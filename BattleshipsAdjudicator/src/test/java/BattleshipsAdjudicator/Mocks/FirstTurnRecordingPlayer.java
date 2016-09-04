package BattleshipsAdjudicator.Mocks;

import java.util.ArrayList;
import java.util.List;

public class FirstTurnRecordingPlayer extends SafeNamePlayer
{
  private static List<PlayerBase> FirstPlayers;
  public static List<PlayerBase> getFirstPlayers()
  {
    return FirstPlayers;
  }
  private static void setFirstPlayers(List<PlayerBase> value)
  {
    FirstPlayers = value;
  }
  private int _gamesPlayed = 0;

  @Override
  public Iterable<BattleshipsInterface.IShipPosition> GetShipPositions()
  {
    _gamesPlayed++;

    if (getFirstPlayers() == null)
    {
      setFirstPlayers(new ArrayList<PlayerBase>());
    }

    if (getFirstPlayers().size() < _gamesPlayed)
    {
      getFirstPlayers().add(this);
    }

    return super.GetShipPositions();
  }
}
