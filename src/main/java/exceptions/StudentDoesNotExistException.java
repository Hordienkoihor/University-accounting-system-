package exceptions;

public class StudentDoesNotExistException extends RuntimeException {
    public StudentDoesNotExistException(String message) {
        super(message);
    }
    public StudentDoesNotExistException() {

    }

}
