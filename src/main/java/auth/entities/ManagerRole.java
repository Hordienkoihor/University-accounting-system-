package auth.entities;

import auth.enums.Role;

public class ManagerRole extends User {
    private final Role role = Role.MANAGER;

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
}
