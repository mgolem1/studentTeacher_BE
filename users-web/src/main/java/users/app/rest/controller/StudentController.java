package users.app.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.app.rest.utils.ResponseMessage;
import users.dto.StudentDTO;
import users.exceptions.AppException;
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
    public ResponseEntity<ResponseMessage<Page<StudentDTO>>> getStudents(@PageableDefault(size = 20, direction = Sort.Direction.ASC) Pageable pageable,
                                                                         @RequestParam(required = false) String lastName,
                                                                         @RequestParam(required = false) String firstName) throws AppException {
        return ResponseEntity.ok(new ResponseMessage<>(studentService.studentList(StudentSearchCriteria.builder().firstName(firstName).lastName(lastName).build(), pageable)));
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<ResponseMessage<StudentDTO>>  createStudent(@RequestBody StudentDTO student) throws AppException{
        return ResponseEntity.ok(new ResponseMessage<>(studentService.createStudent(student)));
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<StudentDTO>>  showById(@PathVariable("id") String id) throws AppException {
        return ResponseEntity.ok(new ResponseMessage<>(studentService.getStudentById(Long.parseLong(id))));
    }

    @PutMapping("/{id}")
    @ResponseBody
    private ResponseEntity<ResponseMessage<StudentDTO>> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO student) throws AppException {
        return ResponseEntity.ok(new ResponseMessage<>(studentService.updateStudent(id, student)));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseMessage<String>> deleteStudent(@PathVariable("id") Long id) throws AppException {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ResponseMessage<>(""));
    }
}
