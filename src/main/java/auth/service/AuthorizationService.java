package auth.service;

import auth.entities.User;
import auth.enums.Right;
import auth.service.interfaces.AuthenticationServiceInt;
import auth.service.interfaces.AuthorizationServiceInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class AuthorizationService implements AuthorizationServiceInt {
    private AuthenticationServiceInt authenticationService;

    private final Logger log = LoggerFactory.getLogger(AuthorizationService.class);

    public AuthorizationService(AuthenticationServiceInt authenticationService) {
        log.info("Initializing AuthorizationService");
        this.authenticationService = authenticationService;
        log.info("Initialized AuthorizationService");
    }

    @Override
    public int provideAuthorityMask(User user) {
        log.info("Provided rights mask for {}", user.getName());
        return user.getRightsMask();
    }

    @Override
    public boolean hasRight(User user, Right right) {
        return user.hasRight(right);
    }

    @Override
    public boolean hasAllRights(User user, Right... rights) {
        return user.hasAllRights(rights);
    }

    @Override
    public boolean hasAnyRights(User user, Right... rights) {
        return user.hasAnyRights(rights);
    }

    @Override
    public void setRight(User user, Right right) {
        user.setRightsMask(Right.addRight(user.getRightsMask(), right));
        log.info("Right {} set to {}", right.name(), user.getName());
    }

    @Override
    public void revokeRight(User user, Right right) {
        user.setRightsMask(Right.removeRight(user.getRightsMask(), right));
        log.info("Right {} revoked from {}", right.name(), user.getName());

    }

//    @Override
//    public Set<Right> provideAuthority(User user) {
//        return user.getRights();

//    }
}
