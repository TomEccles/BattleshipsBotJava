package BattleshipsExamplePlayer.Placing;

import BattleshipsInterface.EShipType;
import BattleshipsInterface.IShipPosition;

public class ShipPosition implements IShipPosition {
  private final EShipType Ship;
  private final char StartingRow;
  private final int StartingColumn;
  private final char EndingRow;
  private final int EndingColumn;

  public ShipPosition(EShipType ship, char startingRow, int startingColumn, char endingRow, int endingColumn) {
    Ship = ship;
    StartingRow = startingRow;
    StartingColumn = startingColumn;
    EndingRow = endingRow;
    EndingColumn = endingColumn;
  }

  @Override
  public EShipType getShip() {
    return Ship;
  }

  @Override
  public char getStartingRow() {
    return StartingRow;
  }

  @Override
  public int getStartingColumn() {
    return StartingColumn;
  }

  @Override
  public char getEndingRow() {
    return EndingRow;
  }

  @Override
  public int getEndingColumn() {
    return EndingColumn;
  }

  public static ShipPosition fromFriendlyPosition(FriendlyShipPosition position, EShipType type) {
    return new ShipPosition(type, charFromInt(position.xStart), position.yStart+1, charFromInt(position.xEnd), position.yEnd+1);
  }

  private static char charFromInt(int i) {
    return (char)('A' + (i));
  }
}
