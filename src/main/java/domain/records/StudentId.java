package domain.records;

public record StudentId(String studentId) {
    @Override
    public String toString() {
        return studentId;
    }
}
