package auth.entities;

import auth.enums.Role;

public class HeadAdminRole extends User {
    private static HeadAdminRole instance = null;
    private final Role role = Role.ADMIN;

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
}
