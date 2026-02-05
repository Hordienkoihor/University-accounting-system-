package exceptions;

public class IllegalHoursNumber extends RuntimeException {
    public IllegalHoursNumber(String message) {
        super(message);
    }

    public IllegalHoursNumber() {
        super();
    }
}
