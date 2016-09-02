package BattleshipsAdjudicator;

import BattleshipsInterface.*;
import org.joda.time.Duration;


public interface IMatchRunnerFactory {
  IMatchRunner GetMatchRunner(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, int numberOfRounds, Duration playerTimeout, String baseOutputFilename);
}
