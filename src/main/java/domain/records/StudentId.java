package domain.records;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record StudentId(String studentId) {
    @JsonValue
    public String studentId() {
        return studentId;
    }

    @JsonCreator
    public static StudentId fromString(String studentId) {
        return new StudentId(studentId);
    }

    @Override
    public String toString() {
        return studentId;
    }
}
