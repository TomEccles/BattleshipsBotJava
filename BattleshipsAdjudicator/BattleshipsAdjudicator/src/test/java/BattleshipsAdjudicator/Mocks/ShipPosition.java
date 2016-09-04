package BattleshipsAdjudicator.Mocks;

import BattleshipsInterface.*;

public class ShipPosition implements IShipPosition {
  public ShipPosition(EShipType shipType, String position) {
    this.Ship = shipType;

    this.StartingRow = position.charAt(0);
    this.StartingColumn = Integer.parseInt(position.substring(1, 3));
    this.EndingRow = position.charAt(3);
    this.EndingColumn = Integer.parseInt(position.substring(4, 6));
  }

  private final EShipType Ship;
  public final EShipType getShip() {
    return Ship;
  }

  private final char StartingRow;
  public final char getStartingRow() {
    return StartingRow;
  }

  private final int StartingColumn;
  public final int getStartingColumn() {
    return StartingColumn;
  }

  private final char EndingRow;
  public final char getEndingRow() {
    return EndingRow;
  }

  private final int EndingColumn;
  public final int getEndingColumn() {
    return EndingColumn;
  }
}
