package users.dto;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoursesDTO {

    private String id;


    private String name;
}
