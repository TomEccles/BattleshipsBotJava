package BattleshipsInterface;

public enum EShipType
{
  aircraftCarrier,
  battleship,
  destroyer,
  submarine,
  patrolBoat;

  public int getValue()
  {
    return this.ordinal();
  }

  public static EShipType forValue(int value)
  {
    return values()[value];
  }
}
