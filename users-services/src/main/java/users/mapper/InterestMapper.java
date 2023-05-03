package users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;
import users.dto.InterestDTO;
import users.dto.StudentDTO;
import users.model.Interest;
import users.model.Student;

@Mapper(componentModel = "spring")
@Component
public abstract class InterestMapper {

    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract Interest fromDTO(InterestDTO entity);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(entity.getId()))"),
            @Mapping(target = "name",source = "entity.name")
    })
    public abstract InterestDTO interestToDTO(Interest entity);
}
