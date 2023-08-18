package users.service;

import org.springframework.stereotype.Service;
import users.dto.CoursesDTO;
import users.exceptions.AppException;

import java.util.List;

@Service
public interface CoursesServices {

    List<CoursesDTO> getCourses() throws AppException;

}
