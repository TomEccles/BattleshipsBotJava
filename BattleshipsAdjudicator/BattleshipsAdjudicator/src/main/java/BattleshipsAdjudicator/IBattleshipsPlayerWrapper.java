package BattleshipsAdjudicator;

import BattleshipsInterface.*;

/**
 This interface has exactly the same methods as IBattleshipsPlayer plus
 a method to reset the internal player. Classes implementing this
 interface must guarantee that accessing the name property cannot cause
 an exception.
 */
public interface IBattleshipsPlayerWrapper extends IBattleshipsPlayer
{
  void ResetPlayer() throws InstantiationException, IllegalAccessException;
}
