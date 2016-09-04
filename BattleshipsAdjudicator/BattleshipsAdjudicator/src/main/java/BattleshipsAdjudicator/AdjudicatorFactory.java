package BattleshipsAdjudicator;

import org.joda.time.Duration;

import java.io.Writer;

public class AdjudicatorFactory implements IAdjudicatorFactory {
  public final IAdjudicator getAdjudicator(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, Duration playerTimeout, Writer log) {
    return new Adjudicator(player1, player2, playerTimeout, log);
  }
}
