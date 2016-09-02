package BattleshipsAdjudicator;

import java.util.*;

import BattleshipsInterface.*;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;

public class ShipSetValidator {
  
  public ShipSetValidator(ShipSet ships) {
    _ships = ships;
  }

  public final void AssertAreValid() {
    if (_ships.getCount() != 5) {
      throw new InvalidShipPostionException("{0} ships found (not 5)", _ships.getCount());
    }

    AssertShipTypePresent(EShipType.aircraftCarrier);
    AssertShipTypePresent(EShipType.battleship);
    AssertShipTypePresent(EShipType.destroyer);
    AssertShipTypePresent(EShipType.submarine);
    AssertShipTypePresent(EShipType.patrolBoat);

    AssertShipsNotTouching();
  }

  private void AssertShipTypePresent(EShipType typeToCheck) {
    if (!_ships.getStream().anyMatch(s -> s.getShip() == typeToCheck)) {
      throw new InvalidShipPostionException("No {0} found", typeToCheck);
    }
  }

  private void AssertShipsNotTouching() {
    ArrayList<BoardSquare> ShipSquares = new ArrayList<>();

    for (int i = 0; i < _ships.getCount(); i++) {
      Iterable<BoardSquare> adjacentSquares = GetAllAdjacentSquares(_ships.getSquaresAt(i));

      for (int j = 0; j < _ships.getCount(); j++) {
        if (i != j) {
          AssertNoOverlap(_ships.getSquaresAt(j), adjacentSquares);
        }
      }
    }
  }

  private void AssertNoOverlap(Iterable<BoardSquare> lhs, Iterable<BoardSquare> rhs) {
    int totalSquares = Iterables.size(lhs) + Iterables.size(rhs);
    int totalDistinctSquares = Sets.newHashSet(Iterables.concat(lhs, rhs)).size();

    if (totalSquares != totalDistinctSquares)
    {
      throw new InvalidShipPostionException("Touching ships found");
    }
  }

  private Iterable<BoardSquare> GetAllAdjacentSquares(Iterable<BoardSquare> squares)
  {
    ArrayList<BoardSquare> ret = new ArrayList<>();

    for (BoardSquare square : squares)
    {
      ret.addAll(GetAllAdjacentSquares(square));
    }

    return Sets.newHashSet(ret);
  }

  private Collection<BoardSquare> GetAllAdjacentSquares(BoardSquare square)
  {
    ArrayList<BoardSquare> ret = new ArrayList<>();

    if (square.getIsOnBottomEdge()&& square.getIsOnRightEdge())
    {
      ret.add(new BoardSquare((char)(square.getRow() + 1), square.getColumn() + 1));
    }
    if (square.getIsOnBottomEdge())
    {
      ret.add(new BoardSquare((char)(square.getRow() + 1), square.getColumn()));
    }
    if (square.getIsOnBottomEdge()&& square.getIsOnLeftEdge())
    {
      ret.add(new BoardSquare((char)(square.getRow() + 1), square.getColumn() - 1));
    }
    if (square.getIsOnRightEdge())
    {
      ret.add(new BoardSquare(square.getRow(), square.getColumn() + 1));
    }
    if (square.getIsOnLeftEdge())
    {
      ret.add(new BoardSquare(square.getRow(), square.getColumn() - 1));
    }
    if (square.getIsOnTopEdge()&& square.getIsOnRightEdge())
    {
      ret.add(new BoardSquare((char)(square.getRow() - 1), square.getColumn() + 1));
    }
    if (square.getIsOnTopEdge())
    {
      ret.add(new BoardSquare((char)(square.getRow() - 1), square.getColumn()));
    }
    if (square.getIsOnTopEdge()&& square.getIsOnLeftEdge())
    {
      ret.add(new BoardSquare((char)(square.getRow() - 1), square.getColumn() - 1));
    }

    return ret;
  }

  private ShipSet _ships;
}
