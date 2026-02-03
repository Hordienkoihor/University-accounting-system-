package exceptions;

public class IllegalNameException extends RuntimeException {
    public IllegalNameException(String message) {
        super(message);
    }

    public IllegalNameException() {
        super();
    }
}
