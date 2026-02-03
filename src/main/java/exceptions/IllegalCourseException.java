package exceptions;

public class IllegalCourseException extends RuntimeException {
    public IllegalCourseException(String message) {
        super(message);
    }

    public IllegalCourseException() {
        super();
    }

}
