package users.respones;

import lombok.Getter;
import users.model.User;

@Getter
public class AuthResponse {


    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
