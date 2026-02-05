package exceptions;

public class StudentAddingError extends RuntimeException {
    public StudentAddingError(String message) {
        super(message);
    }

    public StudentAddingError() {

    }
}
