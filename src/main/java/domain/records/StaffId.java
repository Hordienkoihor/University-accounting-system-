package domain.records;

public record StaffId(String staffId) {
    @Override
    public String toString() {
        return staffId;
    }
}
