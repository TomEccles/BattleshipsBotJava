package BattleshipsInterface;

/**
 Objects returned by SelectTarget must implement this interface. Each object
 represents a space on the board, with the row represented by a character and
 the column represented by and integer.
 */
public interface ICoordinate
{
  char getRow();
  int getColumn();
}