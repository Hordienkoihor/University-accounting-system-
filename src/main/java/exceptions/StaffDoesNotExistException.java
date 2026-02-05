package exceptions;

public class StaffDoesNotExistException extends RuntimeException {
    public StaffDoesNotExistException(String message) {
        super(message);
    }
    public StaffDoesNotExistException() {

    }

}
