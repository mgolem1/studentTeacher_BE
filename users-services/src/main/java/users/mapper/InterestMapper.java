package users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import users.dto.InterestDTO;
import users.model.Interest;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class InterestMapper {

    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract Interest fromDTO(InterestDTO entity);

    @Mapping(target = "id", source = "id", resultType = Long.class)
    public abstract InterestDTO interestToDTO(Interest entity);
}
