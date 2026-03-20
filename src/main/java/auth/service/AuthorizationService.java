package auth.service;

import auth.entities.HeadAdminRole;
import auth.entities.ManagerRole;
import auth.entities.User;
import auth.entities.UserRole;
import auth.enums.Rights;
import auth.enums.Role;
import auth.service.interfaces.AuthenticationServiceInt;
import auth.service.interfaces.AuthorizationServiceInt;
import exceptions.AuthorizationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AuthorizationService implements AuthorizationServiceInt {
    private AuthenticationServiceInt authenticationService;

    public AuthorizationService(AuthenticationServiceInt authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Set<Rights> provideAuthority(User user) {
        return user.getRights();
    }
}
