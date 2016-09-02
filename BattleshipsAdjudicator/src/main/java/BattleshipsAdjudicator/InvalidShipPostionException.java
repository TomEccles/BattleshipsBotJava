package BattleshipsAdjudicator;

public class InvalidShipPostionException extends RuntimeException
{
  public InvalidShipPostionException(String message)
  {
    super(message);
  }
  public InvalidShipPostionException(String formatString, Object... args)
  {
    super(String.format(formatString, args));
  }
}
