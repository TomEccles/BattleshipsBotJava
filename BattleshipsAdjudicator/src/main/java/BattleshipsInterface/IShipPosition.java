package BattleshipsInterface;

/**
 Objects returned by GetShipPositions must implement this interface. Each
 object should represent the position of a single ship.
 */
public interface IShipPosition
{
  EShipType getShip();
  char getStartingRow();
  int getStartingColumn();
  char getEndingRow();
  int getEndingColumn();
}
