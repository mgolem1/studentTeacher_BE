package users.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import users.dto.StudentDTO;
import users.exceptions.AppError;
import users.exceptions.AppException;
import users.mapper.StudentMapper;
import users.mapper.TeacherMapper;
import users.model.Interest;
import users.model.Student;
import users.model.Teacher;
import users.repository.StudentRepository;
import users.repository.TeacherRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchStudentTeacherServiceImpl implements MatchStudentTeacherService {


    private final StudentMapper studentMapper;

    private final TeacherMapper teacherMapper;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    public MatchStudentTeacherServiceImpl(StudentMapper studentMapper, TeacherMapper teacherMapper, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
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
    public List<StudentDTO> matched() throws AppException {
        if (hasRole("STUDENT")) {
            throw new AppException(AppError.UNAUTHORIZED);
        }

        List<Student> students = studentRepository.findAll();
        List<Teacher> teachers = teacherRepository.findAll();

        for (Teacher teacher : teachers) {
            Map<Student, Double> studentList = new HashMap<>();
            if (teacher.getNumberOfStudent() == 0 || teacher.getStudentsForMentoring().size() == teacher.getNumberOfStudent()) {
                continue;
            }
            for (Student student : students) {
                if (student.getMentor() != null || student.getInterest().isEmpty()) {
                    continue;
                }
                Double score = student.getFinalGrade();
                for (Interest interest : student.getInterest()) {
                    if (teacher.getInterest().contains(interest)) {
                        score += 5;
                    }
                }
                studentList.put(student, score);
            }
            List<Map.Entry<Student, Double>> list = new ArrayList<>(studentList.entrySet());

            Collections.sort(list, new Comparator<Map.Entry<Student, Double>>() {
                @Override
                public int compare(Map.Entry<Student, Double> o1, Map.Entry<Student, Double> o2) {
                    return Double.compare(o2.getValue(), o1.getValue());
                }
            });

            Map<Student, Double> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<Student, Double> entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            List<Student> sortedStudents = new ArrayList<>();
            for (Map.Entry<Student, Double> entry : list) {
                sortedStudents.add(entry.getKey());
            }

            for (int i = 0; i < teacher.getNumberOfStudent(); i++) {
                if (i >= sortedStudents.size()) {
                    break;
                }
                teacher.getStudentsForMentoring().add(sortedStudents.get(i));
                sortedStudents.get(i).setMentor(teacher);
                studentRepository.save(sortedStudents.get(i));
            }
            teacherRepository.save(teacher);

        }
        return studentRepository.findAll().stream().map(studentMapper::basicInfoStudent).collect(Collectors.toList());
    }

}
