package auth.entities;

import auth.enums.Role;

public class StudentRole extends User {
    private final Role role = Role.student;

    StudentRole(String name, String password) {
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
