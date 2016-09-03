package BattleshipsPracticeDummy;

import BattleshipsInterface.*;

public class ShipPosition implements IShipPosition
{
  private EShipType Ship;
  public final EShipType getShip() {
    return Ship;
  }
  public final void setShip(EShipType value) {
    Ship = value;
  }

  private char StartingRow;
  public final char getStartingRow() {
    return StartingRow;
  }
  public final void setStartingRow(char value) {
    StartingRow = value;
  }

  private int StartingColumn;
  public final int getStartingColumn() {
    return StartingColumn;
  }
  public final void setStartingColumn(int value) {
    StartingColumn = value;
  }

  private char EndingRow;
  public final char getEndingRow() {
    return EndingRow;
  }
  public final void setEndingRow(char value) {
    EndingRow = value;
  }

  private int EndingColumn;
  public final int getEndingColumn() {
    return EndingColumn;
  }
  public final void setEndingColumn(int value) {
    EndingColumn = value;
  }
}
