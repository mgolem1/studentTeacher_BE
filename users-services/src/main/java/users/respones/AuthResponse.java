package users.respones;

import users.model.User;

public class AuthResponse {

    private final User user;

    private final String jwt;

    public AuthResponse(String jwt,User user) {
        this.jwt = jwt;
        this.user=user;
    }

    public String getJwt() {
        return jwt;
    }
}
