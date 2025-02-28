package users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import users.dto.StudentDTO;
import users.exceptions.AppException;
import users.specification.StudentSearchCriteria;

@Service
public interface StudentService {

    Page<StudentDTO> studentList(StudentSearchCriteria criteria, Pageable pageable) throws AppException;

    StudentDTO getStudentById(Long id) throws AppException;

    StudentDTO updateStudent(Long id, StudentDTO student) throws AppException;

    StudentDTO createStudent(StudentDTO student) throws AppException;

    void deleteStudent(Long id) throws AppException;

}
