package enums;

public enum StudyForm {
    TUITION_FREE("tuition free"),
    TUITION("tuition");

    public final String displayName;

    StudyForm(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Study form: " + displayName;
    }
}
