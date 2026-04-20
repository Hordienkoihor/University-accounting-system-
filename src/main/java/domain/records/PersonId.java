package domain.records;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record PersonId(String personId) {
    @JsonValue
    public String personId() {
        return personId;
    }

    @JsonCreator
    public static PersonId fromString(String personId) {
        return new PersonId(personId);
    }

    @Override
    public String toString() {
        return personId;
    }
}
