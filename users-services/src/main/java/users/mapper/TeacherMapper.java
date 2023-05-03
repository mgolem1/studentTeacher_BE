package users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import users.dto.TeacherDTO;
import users.model.Teacher;

@Mapper(componentModel = "spring")
@Component
public abstract class TeacherMapper {

@Autowired
private StudentMapper studentMapper;

    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract Teacher fromDTO(TeacherDTO entity);

    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract TeacherDTO toDTO(Teacher entity);

}
