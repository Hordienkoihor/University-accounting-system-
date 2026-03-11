package auth.entities;

import auth.enums.Rights;
import auth.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class UserRole extends User {
    private final Role role = Role.USER;
    private final Set<Rights> rights = new HashSet<>(Set.of(Rights.LOOK, Rights.REPORTS));

    UserRole(String name, String password) {
        super(name, password);
    }

    public boolean changeName(String newName, String password) {
        if (this.getPassword().equals(password)) { //todo transfer to service
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
