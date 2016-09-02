package BattleshipsAdjudicator;

import java.util.*;
import java.util.stream.Collectors;

import BattleshipsInterface.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class ValidShipPosition {
  public ValidShipPosition(IShipPosition position) {
    setShip(position.getShip());
    ImmutablePair<BoardSquare, BoardSquare> boardSquares = CreateStartAndEndSquares(position);
    BoardSquare start = boardSquares.getLeft();
    BoardSquare end = boardSquares.getRight();

    if (start.getColumn() != end.getColumn() && start.getRow() != end.getRow()) {
      throw new InvalidShipPostionException("Diagonal ship {0}-{1}", start, end);
    }

    List<BoardSquare> shipSquares = CreateMiddleSquares(start, end);

    if (!ShipLengthIsCorrect(shipSquares)) {
      throw new InvalidShipPostionException("Incorrect ship length {0} {1}-{2}", getShip(), start, end);
    }

    setSquares(shipSquares);
  }

  public final EShotResult ShotFired(char row, int column) {
    EShotResult result = EShotResult.Miss;
    for (BoardSquare square : getSquares()) {
      if (square.getRow() == row && square.getColumn() == column) {
        boolean newHit = !(square.getIsHit());
        square.setIsHit(true);
        result = (newHit && getIsSunk()) ? EShotResult.HitAndSunk : EShotResult.Hit;
        break;
      }
    }
    return result;
  }

  public final boolean getIsSunk() {
    return getSquares().stream().allMatch(BoardSquare::getIsHit);
  }

  private ImmutablePair<BoardSquare, BoardSquare> CreateStartAndEndSquares(IShipPosition position)
  {
    BoardSquare start = null;
    BoardSquare end = null;

    if (ShouldReverseStartAndEnd(position)) {
      start = new BoardSquare(position.getEndingRow(), position.getEndingColumn());
      end = new BoardSquare(position.getStartingRow(), position.getStartingColumn());
    } else {
      start = new BoardSquare(position.getStartingRow(), position.getStartingColumn());
      end = new BoardSquare(position.getEndingRow(), position.getEndingColumn());
    }

    if (start.getRow() == end.getRow() && start.getColumn() == end.getColumn()) {
      end = start;
    }
    return new ImmutablePair<>(start, end);
  }

  private boolean ShouldReverseStartAndEnd(IShipPosition position) {
    return position.getStartingRow() > position.getEndingRow() || position.getStartingColumn() > position.getEndingColumn();
  }

  private List<BoardSquare> CreateMiddleSquares(BoardSquare start, BoardSquare end) {
    boolean isHorizontal = start.getRow() == end.getRow();
    ArrayList<BoardSquare> squares = new ArrayList<>(Arrays.asList(new BoardSquare[] {start}));
    if (isHorizontal) {
      for (int i = 1; i < (end.getColumn() - start.getColumn()); i++) {
        squares.add(new BoardSquare(start.getRow(), start.getColumn() + i));
      }
    } else {
      for (int i = 1; i < (end.getRow() - start.getRow()); i++) {
        squares.add(new BoardSquare((char)(start.getRow() + i), start.getColumn()));
      }
    }
    if (start != end) {
      squares.add(end);
    }
    return squares;
  }

  private boolean ShipLengthIsCorrect(List<BoardSquare> shipSquares) {
    return GetCorrectLength(getShip()) == shipSquares.size();
  }

  private int GetCorrectLength(EShipType shipType) {
    switch (shipType) {
      case aircraftCarrier:
        return 5;
      case battleship:
        return 4;
      case destroyer:
        return 3;
      case submarine:
        return 3;
      case patrolBoat:
        return 2;
      default:
        throw new RuntimeException("Invalid ship type: " + shipType);
    }
  }

  private EShipType Ship;
  public final EShipType getShip()
  {
    return Ship;
  }
  private void setShip(EShipType value)
  {
    Ship = value;
  }
  private List<BoardSquare> squares;

  public final List<BoardSquare> getSquares() {
    return squares;
  }

  public final Optional<BoardSquare> getShipSquare(Character row, Integer column) {
    return getSquares()
            .stream()
            .filter(square -> square.getColumn() == column && square.getRow() == row)
            .findFirst();
  }

  private void setSquares(List<BoardSquare> value) {
    squares = value;
  }
}
