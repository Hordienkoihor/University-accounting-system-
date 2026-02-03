package exceptions;

public class IllegalFatherNameException extends RuntimeException {
    public IllegalFatherNameException(String message) {
        super(message);
    }

    public IllegalFatherNameException() {
        super();
    }
}
