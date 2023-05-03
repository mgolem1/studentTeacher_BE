package users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import users.dto.StudentDTO;
import users.model.Interest;
import users.model.Student;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StudentMapper {


    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract Student fromDTO(StudentDTO entity);


    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract StudentDTO toDTO(Student entity);

    @Mappings({
            @Mapping(target="firstName",source="firstName"),
            @Mapping(target = "firstChoice",ignore = true),
            @Mapping(target = "secondChoice",ignore = true),
            @Mapping(target = "thirdChoice",ignore = true),
    })
    public abstract StudentDTO basicInfoStudent(Student entity);
}
