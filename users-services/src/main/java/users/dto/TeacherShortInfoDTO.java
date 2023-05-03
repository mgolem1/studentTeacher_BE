package users.dto;


import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherShortInfoDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private Set<InterestDTO> interest;

    private Set<CoursesDTO> courses;
}
