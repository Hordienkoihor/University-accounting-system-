package domain.enums;

import java.util.Arrays;

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

    public static StudyForm getStudyForm(String name) {
        return Arrays.stream(StudyForm.values())
                .filter(studyForm -> studyForm.displayName.equals(name))
                .findFirst()
                .orElse(null);
    }
}
