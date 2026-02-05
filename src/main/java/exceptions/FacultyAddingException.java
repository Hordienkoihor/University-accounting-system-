package exceptions;

public class FacultyAddingException extends RuntimeException {
    public FacultyAddingException(String message) {
        super(message);
    }

    public FacultyAddingException() {
        super();
    }

}
