package users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import users.dto.StudentDTO;
import users.exceptions.AppError;
import users.exceptions.AppException;
import users.mapper.InterestMapper;
import users.mapper.StudentMapper;
import users.model.Address;
import users.model.Interest;
import users.model.Student;
import users.model.Teacher;
import users.repository.InterestRepository;
import users.repository.StudentRepository;
import users.repository.TeacherRepository;
import users.specification.StudentSearchCriteria;
import users.specification.StudentSearchSpecification;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final InterestMapper interestMapper;

    private final TeacherRepository teacherRepository;

    private final InterestRepository interestRepository;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, InterestMapper interestMapper, TeacherRepository teacherRepository, InterestRepository interestRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.interestMapper = interestMapper;
        this.teacherRepository = teacherRepository;
        this.interestRepository = interestRepository;
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
    public Page<StudentDTO> studentList(StudentSearchCriteria criteria, Pageable pageable) throws AppException {
        return studentRepository.findAll(StudentSearchSpecification.findUsers(criteria), pageable).map(studentMapper::toDTO);
    }

    @Override
    @Transactional
    public StudentDTO getStudentById(Long id) throws AppException {
        Optional<Student> studentById = studentRepository.findById(id);

        if (!studentById.isPresent()) {
            throw new AppException(AppError.STUDENT_NOT_FOUND);
        }

        return studentMapper.toDTO(studentById.get());
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws AppException {

        Student student = studentRepository.findById(id).get();

        if (student == null) {
            throw new AppException(AppError.STUDENT_NOT_FOUND);
        }

        if (studentDTO.getFirstName() != null) {
            student.setFirstName(studentDTO.getFirstName());
        }
        if (studentDTO.getLastName() != null) {
            student.setLastName(studentDTO.getLastName());
        }
        if (studentDTO.getDepartment() != null) {

            student.setDepartment(studentDTO.getDepartment());
        }

        if (studentDTO.getEmail() != null) {
            student.setEmail(studentDTO.getEmail());
        }

        if (studentDTO.getAddress() != null) {
            if (student.getAddress() == null) {
                student.setAddress(new Address());
            }

            student.getAddress().setCity(studentDTO.getAddress().getCity());
            student.getAddress().setStreet(studentDTO.getAddress().getStreet());
            student.getAddress().setZipCode(studentDTO.getAddress().getZipCode());
            student.getAddress().setStreetNumber(studentDTO.getAddress().getStreetNumber());

        }

        if (studentDTO.getFirstChoice() != null) {
            Teacher teacher = teacherRepository.findById(Long.parseLong(studentDTO.getFirstChoice().getId())).get();
            student.setFirstChoice(teacher);
        }
        if (studentDTO.getSecondChoice() != null) {
            Teacher teacher = teacherRepository.findById(Long.parseLong(studentDTO.getSecondChoice().getId())).get();
            student.setSecondChoice(teacher);
        }
        if (studentDTO.getThirdChoice() != null) {
            Teacher teacher = teacherRepository.findById(Long.parseLong(studentDTO.getThirdChoice().getId())).get();
            student.setThirdChoice(teacher);
        }

        if (studentDTO.getInterest() != null) {
            student.getInterest().clear();
            List<Interest> interest = studentDTO.getInterest().stream().map(i -> interestRepository.findById(Long.parseLong(i.getId())).get()).collect(Collectors.toList());

            student.getInterest().addAll(interest);
        }

        for (Interest i : student.getInterest()
        ) {
            System.out.println(i.getName());
        }


        studentRepository.save(student);
        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO createStudent(StudentDTO student) throws AppException {

        if (!hasRole("STUDENT")) {
            throw new AppException(AppError.UNAUTHORIZED);
        }

        Optional<Student> studentOptional = studentRepository.findByUsername(student.getId());

        if (studentOptional.isPresent()) {
            throw new AppException(AppError.ALREADY_EXIST);
        }

        if (student.getFirstName() == null || student.getLastName() == null || student.getUsername() == null || student.getPassword() == null) {
            throw new AppException(AppError.BAD_REQUEST);
        }

        Student newStudent = new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setUsername(student.getUsername());
        newStudent.setPassword(student.getPassword());
        newStudent.setDepartment(student.getDepartment());
        newStudent.setFinalGrade(student.getFinalGrade());

        studentRepository.save(newStudent);

        return studentMapper.toDTO(newStudent);
    }

    @Override
    public void deleteStudent(Long id) throws AppException {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (!studentOptional.isPresent()) {
            throw new AppException(AppError.ALREADY_EXIST);
        }
        studentRepository.delete(studentOptional.get());

    }
}
