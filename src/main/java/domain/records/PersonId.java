package domain.records;

public record PersonId(String personId) {
    @Override
    public String toString() {
        return personId;
    }
}
