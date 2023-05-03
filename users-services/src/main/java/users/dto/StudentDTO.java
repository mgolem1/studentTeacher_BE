package users.dto;

import lombok.*;
import users.model.Address;
import users.model.Role;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO extends UserDTO{

    private Set<InterestDTO> interest;

    private String department;

    private TeacherDTO firstChoice;

    private TeacherDTO secondChoice;

    private TeacherDTO thirdChoice;

    private TeacherDTO mentor;

    private Double finalGrade;

    private Set<Role> roles;

}
