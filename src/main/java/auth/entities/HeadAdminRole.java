package auth.entities;

import auth.enums.Rights;
import auth.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class HeadAdminRole extends User {
    private static HeadAdminRole instance = null;
    private final Role role = Role.ADMIN;
    private final Set<Rights> rights = new HashSet<>(Set.of(Rights.LOOK, Rights.REPORTS, Rights.CRUD, Rights.CRUD_ADMIN));

    private HeadAdminRole(String name, String password) {
        super(name, password);
    }

    public static synchronized HeadAdminRole getInstance(String name, String password) {
        if (instance == null) {
            instance = new HeadAdminRole(name, password);
        } else {
            throw new RuntimeException("Head admin already exists");
        }
        return instance;
    }

    public static synchronized HeadAdminRole getInstance() {
        if (instance == null) {
            throw new RuntimeException("Head admin does not exist");
        } else {
            return instance;
        }
    }

    @Override
    public Set<Rights> getRights() {
        return Set.copyOf(rights);
    }
}
