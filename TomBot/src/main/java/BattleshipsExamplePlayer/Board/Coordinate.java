package BattleshipsExamplePlayer.Board;

import BattleshipsInterface.ICoordinate;

public class Coordinate implements ICoordinate {
  private final char row;
  private final int column;

  public Coordinate(char row, int column) {
    this.row = row;
    this.column = column;
  }

  @Override
  public char getRow() {
    return this.row;
  }

  @Override
  public int getColumn() {
    return this.column;
  }
}
