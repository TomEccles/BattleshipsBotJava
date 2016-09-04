package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.BoardSquare;
import BattleshipsAdjudicator.Coordinate;
import BattleshipsInterface.ICoordinate;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SafeNamePlayerKiller extends SafeNamePlayer
{
  private List<BoardSquare> _shots = Lists.newArrayList(
    new BoardSquare('A', 1),
    new BoardSquare('A', 2),
    new BoardSquare('A', 3),
    new BoardSquare('A', 4),
    new BoardSquare('A', 5),
    new BoardSquare('C', 1),
    new BoardSquare('C', 2),
    new BoardSquare('C', 3),
    new BoardSquare('C', 4),
    new BoardSquare('E', 1),
    new BoardSquare('E', 2),
    new BoardSquare('E', 3),
    new BoardSquare('G', 1),
    new BoardSquare('G', 2),
    new BoardSquare('G', 3),
    new BoardSquare('I', 1),
    new BoardSquare('I', 2)
  );

  @Override
  public ICoordinate SelectTarget()
  {
    while (_shots.size() > 0)
    {
      BoardSquare shot = _shots.get(0);
      _shots.remove(shot);
      return new Coordinate(shot.getRow(), shot.getColumn());
    }

    return new Coordinate('A', 1);
  }
}
