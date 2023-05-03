package users.service;

import org.springframework.stereotype.Service;
import users.dto.MatchStudentTeacherDTO;
import users.dto.StudentDTO;
import users.exceptions.AppException;

import java.util.List;

@Service
public interface MatchStudentTeacherService {

    List<StudentDTO> matched()throws AppException;
}
