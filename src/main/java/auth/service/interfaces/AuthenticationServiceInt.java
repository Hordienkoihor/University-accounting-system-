package auth.service.interfaces;

import auth.entities.LoginResponse;
import auth.entities.User;

import java.util.Optional;

public interface AuthenticationServiceInt<T extends User> {
    LoginResponse<T> login(String email, String password);
    T register(T user);
    void logout(T user);
    boolean validateToken(int token);

    boolean isLoggedIn(int token);
}
