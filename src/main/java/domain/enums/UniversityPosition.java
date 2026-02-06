package domain.enums;

public enum UniversityPosition {
    ASSISTANT_PROFESSOR("Assistant Professor"),
    ASSOCIATE_PROFESSOR("Associate Professor"),
    FULL_PROFESSOR("Full Professor"),
    DISTINGUISHED_PROFESSOR("Distinguished/Chair Professor"),

    LECTURER("Lecturer"),
    SENIOR_LECTURER("Senior Lecturer"),
    INSTRUCTOR("Instructor"),
    PROFESSOR_OF_PRACTICE("Professor of Practice"),
    VISITING_PROFESSOR("Visiting Professor");

    public final String displayName;

    UniversityPosition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "University position: " + displayName;
    }
}
