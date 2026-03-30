package auth.entities;

import auth.enums.Rights;

import java.util.Set;

public abstract class User {
    private static int userIdGenerator = 0;

    private String name;
    private final String password;
    private int userId;

    User(String name, String password) {
        this.name = name;
        this.password = password;
        this.userId = userIdGenerator++;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Rights> getRights() {
        return Set.of();
    }
}
