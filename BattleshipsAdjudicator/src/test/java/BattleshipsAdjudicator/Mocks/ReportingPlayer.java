package BattleshipsAdjudicator.Mocks;

import BattleshipsInterface.ICoordinate;
import BattleshipsInterface.IShipPosition;

import java.util.ArrayList;

/**
 Helper implementation of IBattleshipsPlayer that reports which
 methods are called.
 */
public class ReportingPlayer extends SafeNamePlayer
{
  public static final String REPORTING_PLAYER_NAME = "BattleshipsTest-ReportingPlayer";
  public static final String METHOD_GET_NAME = "Name:get";
  public static final String METHOD_SHOT_RESULT = "ShotResult";
  public static final String METHOD_SELECT_TARGET = "SelectTarget";
  public static final String METHOD_GET_SHIP_POSITIONS = "GetShipPositions";
  public static final String METHOD_OPPONENTS_SHOT = "OpponentsShot";
  public static final String METHOD_RESET_PLAYER = "ResetPlayer";

  public ReportingPlayer(String name)
  {
    super(name);
  }

  @Override
  public String getName()
  {
    RecordMethodCall(METHOD_GET_NAME);
    return super.getName();
  }

  @Override
  public void ShotResult(boolean hit)
  {
    RecordMethodCall(METHOD_SHOT_RESULT);
  }

  @Override
  public ICoordinate SelectTarget()
  {
    RecordMethodCall(METHOD_SELECT_TARGET);
    return super.SelectTarget();
  }

  @Override
  public Iterable<IShipPosition> GetShipPositions()
  {
    RecordMethodCall(METHOD_GET_SHIP_POSITIONS);
    return super.GetShipPositions();
  }

  @Override
  public void OpponentsShot(char row, int column)
  {
    RecordMethodCall(METHOD_OPPONENTS_SHOT);
  }

  @Override
  public void ResetPlayer()
  {
    RecordMethodCall(METHOD_RESET_PLAYER);
  }

  public final ArrayList<String> getMethodsCalled()
  {
    return _methodsCalled;
  }

  private void RecordMethodCall(String methodName)
  {
    _methodsCalled.add(methodName);
  }

  private ArrayList<String> _methodsCalled = new ArrayList<String>();
}
