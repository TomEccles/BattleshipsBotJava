package BattleshipsAdjudicator;

import BattleshipsInterface.*;

import java.io.Serializable;

public class SerializableShipPosition implements IShipPosition, Serializable {
  public SerializableShipPosition(IShipPosition shipPosition) {
    setShip(shipPosition.getShip());
    setStartingRow(shipPosition.getStartingRow());
    setStartingColumn(shipPosition.getStartingColumn());
    setEndingRow(shipPosition.getEndingRow());
    setEndingColumn(shipPosition.getEndingColumn());
  }

  private EShipType Ship;
  public final EShipType getShip() {
    return Ship;
  }
  private void setShip(EShipType value) {
    Ship = value;
  }

  private char StartingRow;
  public final char getStartingRow() {
    return StartingRow;
  }
  private void setStartingRow(char value) {
    StartingRow = value;
  }

  private int StartingColumn;
  public final int getStartingColumn() {
    return StartingColumn;
  }
  private void setStartingColumn(int value) {
    StartingColumn = value;
  }

  private char EndingRow;
  public final char getEndingRow() {
    return EndingRow;
  }
  private void setEndingRow(char value) {
    EndingRow = value;
  }

  private int EndingColumn;
  public final int getEndingColumn() {
    return EndingColumn;
  }
  private void setEndingColumn(int value) {
    EndingColumn = value;
  }

}
