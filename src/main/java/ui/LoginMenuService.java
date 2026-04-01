package ui;

import auth.entities.LoginResponse;
import auth.enums.Right;
import auth.service.AuthenticationService;
import auth.service.AuthorizationService;

import java.util.Optional;
import java.util.Set;

public class LoginMenuService {
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    public LoginMenuService(AuthenticationService authenticationService, AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    public Optional<Integer> login(String username, String password) {
        try {
            LoginResponse<?> loginResponse = authenticationService.login(username, password);
            int rightsMask = authorizationService.provideAuthorityMask(loginResponse.user());

            return Optional.of(rightsMask);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean userHasRight(LoginResponse<?> loginResponse, Right right) {
        return authorizationService.hasRight(loginResponse.user(), right);
    }

    public boolean userHasAllRights(LoginResponse<?> loginResponse, Right... rights) {
        return authorizationService.hasAllRights(loginResponse.user(), rights);
    }

    public boolean userHasAnyRight(LoginResponse<?> loginResponse, Right... rights) {
        return authorizationService.hasAnyRights(loginResponse.user(), rights);
    }


}
