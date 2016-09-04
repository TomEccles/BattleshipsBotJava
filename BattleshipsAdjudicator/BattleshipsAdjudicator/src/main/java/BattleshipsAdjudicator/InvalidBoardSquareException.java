package BattleshipsAdjudicator;

public class InvalidBoardSquareException extends RuntimeException {
  public InvalidBoardSquareException(String message) {
    super(message);
  }

  public InvalidBoardSquareException(String formatString, Object... args) {
    super(String.format(formatString, args));
  }
}
