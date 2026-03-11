package auth.entities;

import auth.enums.Rights;
import auth.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class ManagerRole extends User {
    private final Role role = Role.MANAGER;
    private final Set<Rights> rights = new HashSet<>(Set.of(Rights.LOOK, Rights.REPORTS, Rights.CRUD));

    ManagerRole(String name, String password) {
        super(name, password);
    }

    public boolean changeName(String newName, String password) {
        if (this.getPassword().equals(password)) {
            this.setName(newName);
            return true;
        }
        return false;
    }

    @Override
    public Set<Rights> getRights() {
        return Set.copyOf(rights);
    }
}
