package exceptions;

public class FacultyDoesNotExistException extends RuntimeException {
    public FacultyDoesNotExistException(String message) {
        super(message);
    }
    public FacultyDoesNotExistException() {

    }

}
