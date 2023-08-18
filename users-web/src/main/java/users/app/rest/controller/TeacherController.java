package users.app.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.app.rest.utils.ResponseMessage;
import users.dto.StudentDTO;
import users.dto.TeacherDTO;
import users.exceptions.AppException;
import users.service.TeacherService;
import users.specification.TeacherSearchCriteria;

@RestController
@CrossOrigin(origins = {"*"},allowCredentials = "true")
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public Page<TeacherDTO> getTeachers(@PageableDefault(size = 20, direction = Sort.Direction.ASC) Pageable pageable,
                                         @RequestParam(required = false) String lastName,
                                         @RequestParam(required = false) String firstName)  throws AppException{
        return teacherService.teacherList(TeacherSearchCriteria.builder().firstName(firstName).lastName(lastName).build(), pageable);
    }

    //find by id
    @GetMapping("/{id}")
    public TeacherDTO showById(@PathVariable("id") Long id) throws AppException {
        return teacherService.getTeacherById(id);
    }

    @PostMapping()
    @ResponseBody
    public TeacherDTO createStudent(@RequestBody TeacherDTO teacherDTO) throws AppException{
        return teacherService.createTeacher(teacherDTO);
    }

    @PutMapping("/{id}")
    @ResponseBody
    private TeacherDTO updateStudent(@PathVariable("id") Long id, @RequestBody TeacherDTO teacherDTO) throws AppException {
        return teacherService.updateTeacher(id, teacherDTO);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ResponseMessage<String>> deleteTeacher(@PathVariable("id") Long id) throws AppException {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok(new ResponseMessage<>(""));
    }
}
