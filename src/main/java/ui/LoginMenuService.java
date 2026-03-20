package ui;

import auth.entities.LoginResponse;
import auth.entities.User;
import auth.enums.Rights;
import auth.service.AuthenticationService;
import auth.service.AuthorizationService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LoginMenuService {
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    public LoginMenuService(AuthenticationService authenticationService, AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    public Optional<Set<Rights>> login(String username, String password) {
        try {
            LoginResponse loginResponse = authenticationService.login(username, password);

            return Optional.ofNullable(authorizationService.provideAuthority(loginResponse.user()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


}
