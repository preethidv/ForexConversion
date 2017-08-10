package forex.conversion.appln.validation;

/**exception thrown when the expected input is not in
 * the format expected.
 */
public class InputFormatException extends Exception {
  private static final long serialVersionUID = 1L;
  private String message = null;

  public InputFormatException() {
    super();
  }

  public InputFormatException(String message) {
    super(message);
    this.message = message;
  }

  public InputFormatException(Throwable cause) {
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

