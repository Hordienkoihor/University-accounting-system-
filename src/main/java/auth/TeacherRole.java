package auth;

import auth.enums.Role;

public class TeacherRole extends User {
    private final Role role = Role.student;

    TeacherRole(String name, String password) {
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
