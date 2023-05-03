package users.dto;

import lombok.*;
import users.model.Interest;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestDTO {

    private String id;


    private String name;

    public InterestDTO(Interest interest) {
        this.name = interest.getName();
    }
}
