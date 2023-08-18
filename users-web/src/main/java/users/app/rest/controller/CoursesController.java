package users.app.rest.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import users.dto.CoursesDTO;
import users.exceptions.AppException;
import users.service.CoursesServices;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
@RequestMapping("/api/courses")
public class CoursesController {

    private final CoursesServices coursesServices;

    public CoursesController(CoursesServices coursesServices) {
        this.coursesServices = coursesServices;
    }

    @GetMapping
    public List<CoursesDTO> getCourses() throws AppException {
        return coursesServices.getCourses();
    }
}
