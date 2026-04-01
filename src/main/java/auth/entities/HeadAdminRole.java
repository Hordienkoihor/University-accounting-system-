package auth.entities;

import auth.enums.Right;
import auth.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class HeadAdminRole extends User {
    private final Role role = Role.ADMIN;
//    private final Set<Right> rights = new HashSet<>(Set.of(Right.LOOK, Right.REPORTS, Right.CRUD, Right.CRUD_ADMIN));

    public HeadAdminRole(String name, String password) {
        super(name, password);
        this.rightsMask = Right.createMask(Right.LOOK, Right.REPORTS, Right.CRUD, Right.CRUD_ADMIN);
    }

    public Role getRole() {
        return role;
    }

}
