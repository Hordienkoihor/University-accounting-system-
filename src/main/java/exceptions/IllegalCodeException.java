package exceptions;

public class IllegalCodeException extends RuntimeException {
    public IllegalCodeException(String message) {
        super(message);
    }

    public IllegalCodeException() {
        super();
    }

}
