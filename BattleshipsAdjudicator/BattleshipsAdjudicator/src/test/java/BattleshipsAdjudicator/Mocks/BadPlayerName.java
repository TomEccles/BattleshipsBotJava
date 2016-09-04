package BattleshipsAdjudicator.Mocks;

/**
 Helper implementation of IBattleshipsPlayer that throws exceptions
 when getting the name
 */
public class BadPlayerName extends PlayerBase
{
  @Override
  public String getName()
  {
    throw new RuntimeException("Name exception");
  }
}
