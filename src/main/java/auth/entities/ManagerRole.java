package auth.entities;

import auth.enums.Right;
import auth.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class ManagerRole extends User {
    private final Role role = Role.MANAGER;
//    private final Set<Right> rights = new HashSet<>(Set.of(Right.LOOK, Right.REPORTS, Right.CRUD));

    public ManagerRole(String name, String password) {
        super(name, password);
        this.rightsMask = Right.createMask(Right.LOOK, Right.REPORTS, Right.CRUD);
    }

    public boolean changeName(String newName, String password) {
        if (this.getPassword().equals(password)) {
            this.setName(newName);
            return true;
        }
        return false;
    }

    public Role getRole() {
        return role;
    }

//    @Override
//    public Set<Right> getRights() {
//        return Set.copyOf(rights);
//    }
}
