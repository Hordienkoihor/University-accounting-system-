package exceptions;

public class IllegalHourlyRateNumber extends RuntimeException {
    public IllegalHourlyRateNumber(String message) {
        super(message);
    }

    public IllegalHourlyRateNumber() {
        super();
    }
}
