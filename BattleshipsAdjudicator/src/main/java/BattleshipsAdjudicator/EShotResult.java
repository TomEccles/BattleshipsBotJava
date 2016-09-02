package BattleshipsAdjudicator;

public enum EShotResult
{
  Miss,
  Hit,
  HitAndSunk;

  public int getValue()
  {
    return this.ordinal();
  }

  public static EShotResult forValue(int value)
  {
    return values()[value];
  }
}
