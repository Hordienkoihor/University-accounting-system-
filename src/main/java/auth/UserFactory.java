package auth;

import auth.entities.HeadAdminRole;
import auth.entities.ManagerRole;
import auth.entities.User;
import auth.entities.UserRole;

public class UserFactory {
    public static User createUser(String type, String name, String password) {
        return switch (type) {
            case "HeadAdminRole" -> new HeadAdminRole(name, password);
             case "ManagerRole" -> new ManagerRole(name, password);
             case "UserRole" -> new UserRole(name, password);
            default -> null;
        };
    }
}
