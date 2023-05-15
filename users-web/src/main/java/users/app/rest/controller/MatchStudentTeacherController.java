package users.app.rest.controller;

import org.springframework.web.bind.annotation.*;
import users.dto.MatchStudentTeacherDTO;
import users.dto.StudentDTO;
import users.exceptions.AppException;
import users.service.MatchStudentTeacherService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"},allowCredentials = "true")
@RequestMapping("/api/matched")
public class MatchStudentTeacherController {

    private final MatchStudentTeacherService matchStudentTeacherService;

    public MatchStudentTeacherController(MatchStudentTeacherService matchStudentTeacherService) {
        this.matchStudentTeacherService = matchStudentTeacherService;
    }

    @GetMapping
    public List<StudentDTO> matched() throws AppException {
        return matchStudentTeacherService.matched();
    }
}
