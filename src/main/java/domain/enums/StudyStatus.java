package domain.enums;

import java.util.Arrays;

public enum StudyStatus {
    STUDYING("studying"),
    EXPELLED("expelled"),
    ACADEMIC_LEAVE("academic leave"),
    PENDING("pending"),;

    private final String displayName;

    StudyStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Study status: " + displayName;
    }

    /**
     *
     * */
    public static StudyStatus getStudyStatus(String name) {
        return Arrays.stream(StudyStatus.values())
                .filter(s -> s.getDisplayName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
