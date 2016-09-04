package BattleshipsAdjudicator;

import BattleshipsInterface.*;
import org.joda.time.Duration;

public class MatchRunnerFactory implements IMatchRunnerFactory {
  public final IMatchRunner GetMatchRunner(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, int numberOfRounds, Duration playerTimeout, String logDir, String baseOutputFilename) {
    return new MatchRunner(player1, player2, numberOfRounds, playerTimeout, new AdjudicatorFactory(), new GameLogProvider(logDir, baseOutputFilename));
  }
}
