package BattleshipsAdjudicator.Mocks;

import java.util.*;

import BattleshipsAdjudicator.*;
import BattleshipsInterface.*;
import com.google.common.collect.Lists;

/**
 Simple implementation of IBattleshipsPlayer
 */
public abstract class PlayerBase implements IBattleshipsPlayer
{
  public abstract String getName();

  public void ShotResult(boolean hit)
  {
  }

  public ICoordinate SelectTarget()
  {
    return new Coordinate('A', 1);
  }

  public Iterable<IShipPosition> GetShipPositions()
  {
    return Lists.newArrayList(
      new ShipPosition(EShipType.aircraftCarrier, "A01A05"),
      new ShipPosition(EShipType.battleship, "C01C04"),
      new ShipPosition(EShipType.destroyer, "E01E03"),
      new ShipPosition(EShipType.submarine, "G01G03"),
      new ShipPosition(EShipType.patrolBoat, "I01I02")
    );
  }

  public void OpponentsShot(char row, int column)
  {
  }
}
