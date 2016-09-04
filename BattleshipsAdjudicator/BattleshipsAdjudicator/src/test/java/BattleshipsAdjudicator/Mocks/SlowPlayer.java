package BattleshipsAdjudicator.Mocks;

import BattleshipsInterface.*;

/**
 Helper implementation of IBattleshipsPlayer that waits the specified
 number of milliseconds on the selected method.
 */
public class SlowPlayer extends SafeNamePlayer
{
  public enum errorType
  {
    opponentsShot,
    shotResult,
    selectTarget,
    getShipPositions;

    public int getValue()
    {
      return this.ordinal();
    }

    public static errorType forValue(int value)
    {
      return values()[value];
    }
  }

  public SlowPlayer(errorType whenToWait, int millisecondsToWait)
  {
    _whenToWait = whenToWait;
    _millisecondsToWait = millisecondsToWait;
  }

  @Override
  public String getName()
  {
    return "SlowPlayer";
  }

  @Override
  public void ShotResult(boolean hit)
  {
    if (_whenToWait == errorType.shotResult)
    {
      try {
        Thread.sleep(_millisecondsToWait);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    super.ShotResult(hit);
  }

  @Override
  public ICoordinate SelectTarget()
  {
    if (_whenToWait == errorType.selectTarget)
    {
      try {
        Thread.sleep(_millisecondsToWait);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    return super.SelectTarget();
  }

  @Override
  public Iterable<IShipPosition> GetShipPositions()
  {
    if (_whenToWait == errorType.getShipPositions)
    {
      try {
        Thread.sleep(_millisecondsToWait);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return super.GetShipPositions();
  }

  @Override
  public void OpponentsShot(char row, int column)
  {
    if (_whenToWait == errorType.opponentsShot)
    {
      try {
        Thread.sleep(_millisecondsToWait);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private errorType _whenToWait = errorType.values()[0];
  private int _millisecondsToWait;
}
