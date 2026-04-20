package domain.records;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record StaffId(String staffId) {
    @JsonValue
    @Override
    public String staffId() {
        return staffId;
    }

    @JsonCreator
    public static StaffId fromString(String staffId) {
        return new StaffId(staffId);
    }

    @Override
    public String toString() {
        return staffId;
    }
}
