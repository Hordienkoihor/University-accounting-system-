package auth.entities;

import auth.enums.Rights;
import auth.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class HeadAdminRole extends User {
    private final Role role = Role.ADMIN;
    private final Set<Rights> rights = new HashSet<>(Set.of(Rights.LOOK, Rights.REPORTS, Rights.CRUD, Rights.CRUD_ADMIN));

    public HeadAdminRole(String name, String password) {
        super(name, password);
    }

    @Override
    public Set<Rights> getRights() {
        return Set.copyOf(rights);
    }
}
