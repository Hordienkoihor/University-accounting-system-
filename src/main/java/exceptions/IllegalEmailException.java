package exceptions;

public class IllegalEmailException extends RuntimeException {
    public IllegalEmailException(String message) {
        super(message);
    }

    public IllegalEmailException() {
        super();
    }

}
