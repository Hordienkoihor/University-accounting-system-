package exceptions;

public class IllegalPhoneNumberException extends RuntimeException {
    public IllegalPhoneNumberException(String message) {
        super(message);
    }

    public IllegalPhoneNumberException() {
        super();
    }

}
