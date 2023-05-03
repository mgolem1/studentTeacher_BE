package users.model;

import lombok.*;

import javax.persistence.Entity;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address extends BaseEntity {

    private String street;

    private String streetNumber;

    private String zipCode;

    private String city;

    private String country;

}
