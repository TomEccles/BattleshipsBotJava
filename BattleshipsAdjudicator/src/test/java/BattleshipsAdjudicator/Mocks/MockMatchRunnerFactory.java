package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.IBattleshipsPlayerWrapper;
import BattleshipsAdjudicator.IMatchRunner;
import BattleshipsAdjudicator.IMatchRunnerFactory;
import com.google.common.collect.Lists;
import org.joda.time.Duration;

import java.util.List;

public class MockMatchRunnerFactory implements IMatchRunnerFactory
{
  private List<MockMatchRunner> _runners;

  public MockMatchRunnerFactory(Iterable<MockMatchRunner> runners)
  {
    _runners = Lists.newArrayList(runners);
  }

  public final IMatchRunner GetMatchRunner(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, int numberOfRounds, Duration playerTimeout, String directory, String baseOutputFilename)
  {
    return _runners.stream().filter(r -> (r.getWinner() == player1 && r.getLoser() == player2) || (r.getWinner() == player2 && r.getLoser() == player1)).findFirst().orElse(null);
  }
}
