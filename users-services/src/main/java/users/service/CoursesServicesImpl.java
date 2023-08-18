package users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import users.dto.CoursesDTO;
import users.exceptions.AppException;
import users.mapper.CoursesMapper;
import users.repository.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CoursesServicesImpl implements CoursesServices {

    private final CourseRepository courseRepository;

    private final CoursesMapper coursesMapper;

    public CoursesServicesImpl(CourseRepository courseRepository, CoursesMapper coursesMapper) {
        this.courseRepository = courseRepository;
        this.coursesMapper = coursesMapper;
    }

    @Override
    public List<CoursesDTO> getCourses() throws AppException {
        return courseRepository.findAll().stream().map(coursesMapper::toDTO).collect(Collectors.toList());
    }
}
