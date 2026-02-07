package exceptions;

public class GroupDoesNotExistException extends RuntimeException {
    public GroupDoesNotExistException(String message) {
        super(message);
    }

    public GroupDoesNotExistException() {
        super();
    }

}
