package auth.service.interfaces;

import auth.entities.User;
import auth.enums.Rights;
import auth.enums.Role;

import java.util.List;
import java.util.Set;

public interface AuthorizationServiceInt {
    Set<Rights> provideAuthority(User user);
}
