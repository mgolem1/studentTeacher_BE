package users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import users.dto.TeacherDTO;
import users.exceptions.AppError;
import users.exceptions.AppException;
import users.mapper.InterestMapper;
import users.mapper.TeacherMapper;
import users.model.*;
import users.repository.*;
import users.specification.TeacherSearchCriteria;
import users.specification.TeacherSearchSpecification;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final RoleRepository roleRepository;

    private final TeacherMapper teacherMapper;

    private final InterestMapper interestMapper;

    private final StudentRepository studentRepository;

    private final InterestRepository interestRepository;

    private final CourseRepository courseRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, RoleRepository roleRepository, TeacherMapper teacherMapper, InterestMapper interestMapper, StudentRepository studentRepository, InterestRepository interestRepository, CourseRepository courseRepository) {
        this.roleRepository = roleRepository;
        this.teacherMapper = teacherMapper;
        this.teacherRepository = teacherRepository;
        this.interestMapper = interestMapper;
        this.studentRepository = studentRepository;
        this.interestRepository = interestRepository;
        this.courseRepository = courseRepository;
    }

    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    @Override
    public Page<TeacherDTO> teacherList(TeacherSearchCriteria criteria, Pageable pageable) throws AppException {
        if (hasRole("STUDENT")) {
            throw new AppException(AppError.UNAUTHORIZED);
        }
        return teacherRepository.findAll(TeacherSearchSpecification.findTeacher(criteria), pageable).map(teacherMapper::toDTO);
    }

    @Override
    public TeacherDTO getTeacherById(Long id) throws AppException {

        if (hasRole("STUDENT")) {
            throw new AppException(AppError.UNAUTHORIZED);
        }

        Optional<Teacher> teacherById = teacherRepository.findById(id);

        if (!teacherById.isPresent()) {
            throw new AppException(AppError.TEACHER_NOT_FOUND);
        }

        return teacherMapper.toDTO(teacherById.get());
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) throws AppException {
        if (!hasRole("STUDENT")) {
            throw new AppException(AppError.UNAUTHORIZED);
        }

        Teacher teacher = teacherRepository.findById(Long.parseLong(teacherDTO.getId())).get();

        if (teacher == null) {
            throw new AppException(AppError.ALREADY_EXIST);
        }

        if (teacher.getFirstName() == null || teacher.getLastName() == null || teacher.getUsername() == null || teacher.getPassword() == null) {
            throw new AppException(AppError.BAD_REQUEST);
        }
        Role r = roleRepository.findByName("TEACHER");
        Set<Role> role = new HashSet<>();
        role.add(r);

        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setUsername(teacherDTO.getUsername());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setRoles(role);

        teacherRepository.save(teacher);

        return teacherMapper.toDTO(teacher);
    }

    @Override
    @Transactional
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) throws AppException {
        Teacher teacher = teacherRepository.findById(id).get();

        if (teacher == null) {
            throw new AppException(AppError.TEACHER_NOT_FOUND);
        }

        if (teacherDTO.getFirstName() != null) {
            teacher.setFirstName(teacherDTO.getFirstName());
        }
        if (teacherDTO.getLastName() != null) {
            teacher.setLastName(teacherDTO.getLastName());
        }
        if (teacherDTO.getNumberOfStudent() != null) {
            teacher.setNumberOfStudent(teacherDTO.getNumberOfStudent());
        }

        if (teacherDTO.getEmail() != null) {
            teacher.setEmail(teacherDTO.getEmail());
        }

        if (teacherDTO.getAddress() != null) {
            teacher.getAddress().setCity(teacherDTO.getAddress().getCity());
            teacher.getAddress().setStreet(teacherDTO.getAddress().getStreet());
            teacher.getAddress().setZipCode(teacherDTO.getAddress().getZipCode());
            teacher.getAddress().setStreetNumber(teacherDTO.getAddress().getStreetNumber());

        }

        if (teacherDTO.getInterest() != null) {
            teacher.getInterest().clear();

            List<Interest> interest = teacherDTO.getInterest().stream().map(i -> interestRepository.findById(Long.parseLong(i.getId())).get()).collect(Collectors.toList());
            teacher.getInterest().addAll(interest);

            interestRepository.saveAll(interest);
        }

        if (teacherDTO.getCourses() != null) {
            teacher.getCourses().clear();
            List<Courses> courses = teacherDTO.getCourses().stream().map(c -> courseRepository.findById(Long.parseLong(c.getId())).get()).collect(Collectors.toList());
            teacher.getCourses().addAll(courses);

            courseRepository.saveAll(courses);
        }

        teacherRepository.save(teacher);

        TeacherDTO saveUserDTO = teacherMapper.toDTO(teacher);

        return saveUserDTO;
    }

    @Override
    public void deleteTeacher(Long id) throws AppException {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        if (!teacherOptional.isPresent()) {
            throw new AppException(AppError.ALREADY_EXIST);
        }
        List<Student> student1 = studentRepository.findAllByFirstChoiceId(id);
        if (student1.size() > 0) {
            for (Student s : student1) {
                s.setFirstChoice(null);
                studentRepository.save(s);
            }
        }
        List<Student> student2 = studentRepository.findAllBySecondChoiceId(id);
        if (student2.size() > 0) {
            for (Student s : student2) {
                s.setSecondChoice(null);
                studentRepository.save(s);
            }
        }
        List<Student> student3 = studentRepository.findAllByThirdChoiceId(id);
        if (student3.size() > 0) {
            for (Student s : student3) {
                s.setThirdChoice(null);
                studentRepository.save(s);
            }
        }

        List<Student> mentor = studentRepository.findAllByMentorId(id);
        if (mentor.size() > 0) {
            for (Student s : mentor) {
                s.setMentor(null);
                studentRepository.save(s);
            }
        }
        teacherRepository.delete(teacherOptional.get());
    }
}
