package users.dto;

import lombok.*;
import users.model.Role;
import users.model.Student;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO extends UserDTO{

    private Set<InterestDTO> interest;

    private Set<CoursesDTO> courses;

    private Integer numberOfStudent;

    private Set<Student> studentsForMentoring;

    private Set<Role> roles;

}
