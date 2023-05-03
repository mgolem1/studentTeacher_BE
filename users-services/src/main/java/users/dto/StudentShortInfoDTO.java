package users.dto;

import lombok.*;
import users.model.Address;

import java.io.Serializable;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentShortInfoDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private Set<InterestDTO> interest;

    private String department;

    private Double finalGrade;
}
