package auth.service.interfaces;

import auth.entities.User;
import auth.enums.Right;

public interface AuthorizationServiceInt {
//    Set<Right> provideAuthority(User user);

    int provideAuthorityMask(User user);

    boolean hasRight(User user, Right right);

    boolean hasAllRights(User user, Right... rights);

    boolean hasAnyRights(User user, Right... rights);

    void setRight(User user, Right right);

    void revokeRight(User user, Right right);



}
