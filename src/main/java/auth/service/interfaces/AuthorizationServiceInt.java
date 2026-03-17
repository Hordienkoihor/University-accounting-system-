package auth.service.interfaces;

import auth.entities.User;
import auth.enums.Rights;
import auth.enums.Role;

import java.util.List;

public interface AuthorizationServiceInt {
    List<Rights> provideAuthority(User user);
}
