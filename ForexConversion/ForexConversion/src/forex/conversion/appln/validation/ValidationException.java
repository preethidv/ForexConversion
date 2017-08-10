package forex.conversion.appln.validation;

@SuppressWarnings("serial")
public class ValidationException extends Exception {
  private String message = null;

  public ValidationException() {
    super();
  }

  public ValidationException(String message) {
    super(message);
    this.message = message;
  }

  public ValidationException(Throwable cause) {
    super(cause);
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
