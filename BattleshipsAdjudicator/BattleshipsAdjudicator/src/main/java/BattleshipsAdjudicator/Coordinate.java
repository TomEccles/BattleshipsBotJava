package BattleshipsAdjudicator;

import BattleshipsInterface.ICoordinate;


public class Coordinate implements ICoordinate {
  private final Character row;
  private final Integer column;

  public Coordinate(Character row, Integer column) {
    this.row = row;
    this.column = column;
  }

  @Override
  public char getRow() {
    return row;
  }

  @Override
  public int getColumn() {
    return column;
  }
}
