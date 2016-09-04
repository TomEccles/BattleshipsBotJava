package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.*;
import org.joda.time.Duration;

import java.io.Writer;

public class GameCountingAdjudicatorFactory implements IAdjudicatorFactory
{
  private int AdjudicatorsCreated;
  public final int getAdjudicatorsCreated()
  {
    return AdjudicatorsCreated;
  }
  private void setAdjudicatorsCreated(int value)
  {
    AdjudicatorsCreated = value;
  }

  public GameCountingAdjudicatorFactory()
  {
    setAdjudicatorsCreated(0);
  }

  public final IAdjudicator getAdjudicator(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, Duration playerTimeout, Writer log)
  {
    setAdjudicatorsCreated(getAdjudicatorsCreated() + 1);

    MockGameResult mockResult = new MockGameResult(null, GameResultType.draw);

    return new MockAdjudicator(mockResult);
  }
}
