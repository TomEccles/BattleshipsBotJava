package BattleshipsAdjudicator;

public class BadNameException extends RuntimeException {
  public BadNameException(String message, RuntimeException innerException) {
    super(message, innerException);
  }
}
