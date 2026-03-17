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

public class AuthorizationService implements AuthorizationServiceInt {
    private AuthenticationServiceInt authenticationService;

    public AuthorizationService(AuthenticationServiceInt authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public List<Rights> provideAuthority(User user) {
        if (!authenticationService.isLoggedIn((user.getName() + user.getPassword()).hashCode())) {
            throw new AuthorizationException("Can't give rights to unauthenticated user");
        }

        if (user instanceof HeadAdminRole) {
            return List.of(Rights.LOOK, Rights.REPORTS, Rights.CRUD, Rights.CRUD_ADMIN);
        }

        if (user instanceof ManagerRole) {
            return List.of(Rights.LOOK, Rights.REPORTS, Rights.CRUD);
        }

        if (user instanceof UserRole) {
            return List.of(Rights.LOOK, Rights.REPORTS);
        }

        return List.of();
    }
}
