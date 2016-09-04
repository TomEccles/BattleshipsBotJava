package BattleshipsAdjudicator.Mocks;

import BattleshipsInterface.EShipType;
import com.google.common.collect.Lists;

public class InvalidShipPositionPlayer extends SafeNamePlayer
{
  @Override
  public Iterable<BattleshipsInterface.IShipPosition> GetShipPositions()
  {
    return Lists.newArrayList(new ShipPosition(EShipType.aircraftCarrier, "A11A07"));
  }
}
