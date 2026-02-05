package exceptions;

public class StaffAddingError extends RuntimeException {
    public StaffAddingError(String message) {
        super(message);
    }

    public StaffAddingError() {

    }
}
