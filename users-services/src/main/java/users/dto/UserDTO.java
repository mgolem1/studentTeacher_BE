package users.dto;

import lombok.*;
import users.model.Address;
import users.model.Role;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private Address address;

    private Set<Role> roles;

    private String username;

    private String password;
}
