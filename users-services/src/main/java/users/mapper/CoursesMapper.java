package users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import users.dto.CoursesDTO;
import users.dto.StudentDTO;
import users.model.Courses;
import users.model.Student;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CoursesMapper {

    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract CoursesDTO toDTO(Courses entity);
}
