package users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import users.dto.StudentDTO;
import users.dto.TeacherDTO;
import users.exceptions.AppException;
import users.specification.StudentSearchCriteria;
import users.specification.TeacherSearchCriteria;

@Service
public interface TeacherService {

    Page<TeacherDTO> teacherList(TeacherSearchCriteria criteria, Pageable pageable) throws AppException;

    TeacherDTO getTeacherById(Long id) throws AppException;

    TeacherDTO createTeacher(TeacherDTO teacherDTO)throws AppException;

    TeacherDTO updateTeacher(Long id,TeacherDTO teacherDTO)throws AppException;

}
