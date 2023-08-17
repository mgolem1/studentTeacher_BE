package users.dto;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String id;

    private String street;

    private String streetNumber;

    private String zipCode;

    private String city;
}
