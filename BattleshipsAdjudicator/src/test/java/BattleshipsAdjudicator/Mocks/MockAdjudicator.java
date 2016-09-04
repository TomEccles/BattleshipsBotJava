package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.IAdjudicator;
import BattleshipsAdjudicator.IGameResult;

public class MockAdjudicator implements IAdjudicator
{
  private MockGameResult _result;

  public MockAdjudicator(MockGameResult result)
  {
    _result = result;
  }

  public final IGameResult RunGame() {
    return _result;
  }
}
