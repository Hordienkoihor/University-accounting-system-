package exceptions;

public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }

    public DepartmentAlreadyExistsException() {
        super();
    }

}
