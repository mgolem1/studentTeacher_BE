package users.app.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import users.dto.StudentDTO;
import users.exceptions.AppException;
import users.model.Student;
import users.service.StudentService;
import users.specification.StudentSearchCriteria;


@RestController
@CrossOrigin(origins = {"*"},allowCredentials = "true")
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Page<StudentDTO> getStudents(@PageableDefault(size = 5, direction = Sort.Direction.ASC) Pageable pageable,
    @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String firstName) throws AppException {
        return studentService.userList(StudentSearchCriteria.builder().firstName(firstName).lastName(lastName).build(), pageable);
    }

    @PostMapping()
    @ResponseBody
    public StudentDTO createStudent(@RequestBody StudentDTO student) throws AppException{
        return studentService.createStudent(student);
    }

    //find by id
    @GetMapping("/{id}")
    public StudentDTO showById(@PathVariable("id") Long id) throws AppException {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    private StudentDTO updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO student) throws AppException {
        System.out.println(id+"aa"+student.getFirstName());
        return studentService.updateStudent(id, student);
    }
}
