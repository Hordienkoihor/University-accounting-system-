package exceptions;

public class SpecialityDoesNotExistsException extends RuntimeException {
    public SpecialityDoesNotExistsException(String message) {
        super(message);
    }
  public SpecialityDoesNotExistsException() {
    super();
  }

}
