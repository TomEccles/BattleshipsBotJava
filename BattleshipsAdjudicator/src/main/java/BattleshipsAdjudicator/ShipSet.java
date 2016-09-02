package BattleshipsAdjudicator;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class ShipSet implements Iterable<ValidShipPosition>
{
  public ShipSet(List<ValidShipPosition> ships) {
    _ships = ships;
  }

  public final boolean getAllSunk() {
    return _ships.stream().allMatch(ValidShipPosition::getIsSunk);
  }

  public final EShotResult ShotFired(char row, int column) {
    for (ValidShipPosition ship : _ships)
    {
      EShotResult result = ship.ShotFired(row, column);
      if (result != EShotResult.Miss)
      {
        return result;
      }
    }

    return EShotResult.Miss;
  }

  @NotNull
  public final Iterator<ValidShipPosition> iterator() {
    return _ships.iterator();
  }

  public Integer getCount() {
    return _ships.size();
  }

  public Stream<ValidShipPosition> getStream() {
    return _ships.stream();
  }

  private List<ValidShipPosition> _ships;

  public List<BoardSquare> getSquaresAt(Integer i) {
    return _ships.get(i).getSquares();
  }
}
