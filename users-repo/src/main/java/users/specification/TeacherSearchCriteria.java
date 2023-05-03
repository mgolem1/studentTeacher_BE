package users.specification;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSearchCriteria {

    private String firstName;

    private String lastName;

    private String interest;
}
