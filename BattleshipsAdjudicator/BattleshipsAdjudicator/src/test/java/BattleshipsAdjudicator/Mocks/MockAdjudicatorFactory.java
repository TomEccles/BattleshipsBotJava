package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.IAdjudicator;
import BattleshipsAdjudicator.IAdjudicatorFactory;
import BattleshipsAdjudicator.IBattleshipsPlayerWrapper;
import org.joda.time.Duration;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class MockAdjudicatorFactory implements IAdjudicatorFactory
{
  private List<MockAdjudicator> _adjudicators;
  private int _nextResultIndex = 0;

  public MockAdjudicatorFactory(List<MockGameResult> gameResults)
  {
    _adjudicators = gameResults.stream().map(MockAdjudicator::new).collect(Collectors.toList());
  }
  public final IAdjudicator getAdjudicator(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, Duration playerTimeout, Writer log)
  {
    return _adjudicators.get(_nextResultIndex++);
  }
}
