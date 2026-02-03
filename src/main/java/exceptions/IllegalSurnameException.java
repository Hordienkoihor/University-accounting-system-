package exceptions;

public class IllegalSurnameException extends RuntimeException {
    public IllegalSurnameException(String message) {
        super(message);
    }

    public IllegalSurnameException() {
        super();
    }
}
