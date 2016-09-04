package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.Coordinate;
import BattleshipsInterface.ICoordinate;
import BattleshipsInterface.IShipPosition;

import java.util.Iterator;

public class ErroringPlayer extends SafeNamePlayer
{
  private errorType _whenToThrow;

  public enum errorType {
    opponentsShot,
    shotRowLowerCaseHigh,
    shotRowLowerCaseLow,
    shotRowUpperCaseHigh,
    shotRowUpperCaseLow,
    shotColumnHigh,
    shotColumnLow,
    shotResult,
    selectTarget,
    getShipPositions;

  }

  public ErroringPlayer(errorType whenToThrow) {
    _whenToThrow = whenToThrow;
  }
  private String Name;

  @Override
  public String getName() {
    return "ErroringPlayer";
  }

  @Override
  public void ShotResult(boolean hit)
  {
    if (_whenToThrow == errorType.shotResult)
    {
      throw new RuntimeException("ShotResult exception");
    }
    super.ShotResult(hit);
  }

  @Override
  public ICoordinate SelectTarget() {
    if (_whenToThrow == errorType.selectTarget) {
      throw new RuntimeException("SelectTarget exception");
    }
    if (_whenToThrow == errorType.shotRowLowerCaseLow) {
      return new Coordinate((char)('a' - 1), 1);
    }
    if (_whenToThrow == errorType.shotRowLowerCaseHigh) {
      return new Coordinate((char)('j' + 1), 1);
    }
    if (_whenToThrow == errorType.shotRowUpperCaseLow) {
      return new Coordinate((char)('A' - 1), 1);
    }
    if (_whenToThrow == errorType.shotRowUpperCaseHigh) {
      return new Coordinate((char)('J' + 1), 1);
    }
    if (_whenToThrow == errorType.shotColumnLow) {
      return new Coordinate('a', 0);
    }
    if (_whenToThrow == errorType.shotColumnHigh) {
    return new Coordinate('a', 11);
    }
    return super.SelectTarget();
  }

  @Override
  public Iterable<IShipPosition> GetShipPositions() {
    if (_whenToThrow == errorType.getShipPositions) {
      throw new RuntimeException("GetShipPositions exception");
    }
    return super.GetShipPositions();
  }
  @Override
  public void OpponentsShot(char row, int column) {
    if (_whenToThrow == errorType.opponentsShot) {
      throw new RuntimeException("OpponentsShot exception");
    }
  }
}
