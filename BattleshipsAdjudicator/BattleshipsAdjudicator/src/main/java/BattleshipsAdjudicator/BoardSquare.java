package BattleshipsAdjudicator;

// Represents a square on the board
public class BoardSquare
{
  public BoardSquare(char row, int column)
  {
    setIsHit(false);

    setRow(Character.toUpperCase(row));
    setColumn(column);

    ValidateRow();
    ValidateColumn();
  }

  @Override
  public String toString()
  {
    return String.format("%s%02d", getRow(), getColumn());
  }

  private void ValidateColumn()
  {
    if (getColumn() < 1 || getColumn() > 10)
    {
      throw new InvalidBoardSquareException("Ship position column was " + getColumn());
    }
  }

  private void ValidateRow()
  {
    if (getRow() < 'A' || getRow() > 'J')
    {
      throw new InvalidBoardSquareException("Ship position row was " + getRow());
    }
  }

  public final boolean getIsOnLeftEdge()
  {
    return getColumn() == 1;
  }
  public final boolean getIsOnRightEdge()
  {
    return getColumn() == 10;
  }
  public final boolean getIsOnTopEdge()
  {
    return getRow() == 'A';
  }
  public final boolean getIsOnBottomEdge()
  {
    return getRow() == 'J';
  }

  private char Row;
  public final char getRow()
  {
    return Row;
  }
  private void setRow(char value)
  {
    Row = value;
  }
  private int Column;
  public final int getColumn()
  {
    return Column;
  }
  private void setColumn(int value)
  {
    Column = value;
  }
  private boolean IsHit;
  public final boolean getIsHit()
  {
    return IsHit;
  }
  public final void setIsHit(boolean value)
  {
    IsHit = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BoardSquare that = (BoardSquare) o;

    if (Row != that.Row) return false;
    if (Column != that.Column) return false;
    return IsHit == that.IsHit;

  }

  @Override
  public int hashCode() {
    int result = (int) Row;
    result = 31 * result + Column;
    result = 31 * result + (IsHit ? 1 : 0);
    return result;
  }
}

