package BattleshipsAdjudicator;

public enum GameResultType
{
  victoryInvalidName,
  victoryInvalidShips,
  victoryInvalidShot,
  victoryException,
  victoryTimeout,
  victory,
  draw;

  public int getValue()
  {
    return this.ordinal();
  }

  public static GameResultType forValue(int value)
  {
    return values()[value];
  }
}
