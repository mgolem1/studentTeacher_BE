package users.specification;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentSearchCriteria {

    private String firstName;

    private String lastName;

    private String odjel;

    private String interest;
}
