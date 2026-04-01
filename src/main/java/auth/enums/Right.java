package auth.enums;

public enum Right {
    LOOK(1 << 0),
    REPORTS(1 << 1),
    CRUD(1 << 2),
    CRUD_ADMIN(1 << 3);

    private final int mask;

    Right(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

    public static boolean hasRight(int mask, Right right) {
        return (mask & right.getMask()) != 0;
    }

    public static int addRight(int rightsMask, Right right) {
        return rightsMask | right.getMask();
    }

    public static int removeRight(int rightsMask, Right right) {
        return rightsMask & ~right.getMask();
    }

    public static int createMask(Right... rights) {
        int mask = 0;
        for (Right right : rights) {
            mask |= right.getMask();
        }
        return mask;
    }

    public static boolean hasAllRights(int rightsMask, int requiredMask) {
        return (rightsMask & requiredMask) == requiredMask;
    }

    public static boolean hasAnyRights(int rightsMask, int requiredMask) {
        return (rightsMask & requiredMask) != 0;
    }
}
