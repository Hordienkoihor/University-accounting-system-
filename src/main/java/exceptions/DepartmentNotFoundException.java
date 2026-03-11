package exceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }

    public DepartmentNotFoundException() {
        super();
    }

}
