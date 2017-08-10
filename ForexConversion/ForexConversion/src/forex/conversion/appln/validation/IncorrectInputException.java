package forex.conversion.appln.validation;


public class IncorrectInputException extends Exception {
  private static final long serialVersionUID = 1L;
  private String message = null;

  public IncorrectInputException() {
    super();
  }

  public IncorrectInputException(String message) {
    super(message);
    this.message = message;
  }

  public IncorrectInputException(Throwable cause) {
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
