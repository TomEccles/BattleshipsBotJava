package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.IBattleshipsPlayerWrapper;
import BattleshipsInterface.*;

/**
 Basic implementation of ISafeNameBattleshipsPlayer
 */
public class SafeNamePlayer extends PlayerBase implements IBattleshipsPlayerWrapper
{
  private String _name;

  public SafeNamePlayer()
  {
    this("BattleshipsTest-SafeNamePlayer");
  }

  public SafeNamePlayer(String name)
  {
    _name = name;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  public void ResetPlayer()
  {
  }
}
