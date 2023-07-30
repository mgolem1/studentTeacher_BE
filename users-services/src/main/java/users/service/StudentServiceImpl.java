package users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import users.dto.StudentDTO;
import users.exceptions.AppError;
import users.exceptions.AppException;
import users.mapper.InterestMapper;
import users.mapper.StudentMapper;
import users.mapper.TeacherMapper;
import users.model.Address;
import users.model.Interest;
import users.model.Student;
import users.model.Teacher;
import users.repository.RoleRepository;
import users.repository.StudentRepository;
import users.repository.UserRepository;
import users.specification.StudentSearchCriteria;
import users.specification.StudentSearchSpecification;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final InterestMapper interestMapper;


    private final TeacherMapper teacherMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, InterestMapper interestMapper, TeacherMapper teacherMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.interestMapper = interestMapper;
        this.teacherMapper = teacherMapper;
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
    public Page<StudentDTO> studentList(StudentSearchCriteria criteria, Pageable pageable) throws AppException{
        return studentRepository.findAll(StudentSearchSpecification.findUsers(criteria), pageable).map(studentMapper::toDTO);
    }

    @Override
    public StudentDTO getStudentById(Long id) throws AppException {
        Optional<Student> studentById = studentRepository.findById(id);

        if (!studentById.isPresent()) {
            throw new AppException(AppError.STUDENT_NOT_FOUND);
        }

        StudentDTO studentDTO = studentMapper.toDTO(studentRepository.save(studentById.get()));
        return studentDTO;
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws AppException {

        Student student=studentRepository.findById(id).get();

        if(student==null){
            throw new AppException(AppError.STUDENT_NOT_FOUND);
        }

        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setDepartment(studentDTO.getDepartment());

        if(studentDTO.getAddress()!=null){
            if(student.getAddress()==null){
                student.setAddress(new Address());
            }

            student.getAddress().setCity(studentDTO.getAddress().getCity());
            student.getAddress().setStreet(studentDTO.getAddress().getStreet());
            student.getAddress().setCountry(studentDTO.getAddress().getCountry());
            student.getAddress().setZipCode(studentDTO.getAddress().getZipCode());
            student.getAddress().setStreetNumber(studentDTO.getAddress().getStreetNumber());

        }

        if(studentDTO.getFirstChoice()!=null){
            Teacher teacher=teacherMapper.fromDTO(studentDTO.getFirstChoice());
            student.setFirstChoice(teacher);
        }
        if(studentDTO.getSecondChoice()!=null){
            Teacher teacher=teacherMapper.fromDTO(studentDTO.getSecondChoice());
            student.setFirstChoice(teacher);
        }
        if(studentDTO.getThirdChoice()!=null){
            Teacher teacher=teacherMapper.fromDTO(studentDTO.getThirdChoice());
            student.setFirstChoice(teacher);
        }
        if(studentDTO.getMentor()!=null){
            Teacher teacher=teacherMapper.fromDTO(studentDTO.getMentor());
            student.setFirstChoice(teacher);
        }

        if(studentDTO.getInterest()!=null){
            Set<Interest> interest=studentDTO.getInterest().stream().map(i->interestMapper.fromDTO(i)).collect(Collectors.toSet());
            student.setInterest(interest);
        }

        studentRepository.save(student);

        StudentDTO saveUserDTO = studentMapper.basicInfoStudent(studentRepository.save(student));

        return saveUserDTO;
    }

    @Override
    public StudentDTO createStudent(StudentDTO student) throws AppException {

        if(!hasRole("STUDENT")){
            throw new AppException(AppError.UNAUTHORIZED);
        }

        Optional<Student> studentOptional = studentRepository.findByUsername(student.getId());

        if (studentOptional.isPresent()) {
            throw new AppException(AppError.ALREADY_EXIST);
        }

        if (student.getFirstName() == null || student.getLastName() == null || student.getUsername()==null || student.getPassword()==null) {
            throw new AppException(AppError.BAD_REQUEST);
        }

        Student newStudent=new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setUsername(student.getUsername());
        newStudent.setPassword(student.getPassword());
        newStudent.setDepartment(student.getDepartment());
        newStudent.setFinalGrade(student.getFinalGrade());

        studentRepository.save(newStudent);

        return studentMapper.toDTO(newStudent);
    }
}
