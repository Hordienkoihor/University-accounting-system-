package auth.entities;

import auth.enums.Right;

import java.util.Set;

public abstract class User {
    private static int userIdGenerator = 0;
    private final String password;
    protected int rightsMask = 0;
    private String name;
    private int userId;

    User(String name, String password) {
        this.name = name;
        this.password = password;
        this.userId = userIdGenerator++;
    }

    public int getRightsMask() {
        return rightsMask;
    }

    public void setRightsMask(int rightsMask) {
        this.rightsMask = rightsMask;
    }

    public boolean hasRight(Right right) {
        return Right.hasRight(this.rightsMask, right);
    }

    public boolean hasAllRights(Right... rights) {
        int requiredMask = Right.createMask(rights);
        return Right.hasAllRights(this.rightsMask, requiredMask);
    }

    public boolean hasAnyRights(Right... rights) {
        int requiredMask = Right.createMask(rights);
        return Right.hasAnyRights(this.rightsMask, requiredMask);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Set<Right> getRights() {
        return Set.of();
    }
}
