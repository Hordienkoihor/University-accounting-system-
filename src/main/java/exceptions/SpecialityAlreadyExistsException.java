package exceptions;

public class SpecialityAlreadyExistsException extends RuntimeException {
    public SpecialityAlreadyExistsException(String message) {
        super(message);
    }
  public SpecialityAlreadyExistsException() {
    super();
  }

}
