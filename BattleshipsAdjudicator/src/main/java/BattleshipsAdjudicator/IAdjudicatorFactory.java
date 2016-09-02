package BattleshipsAdjudicator;

import org.joda.time.Duration;

import java.io.Writer;

public interface IAdjudicatorFactory
{
  IAdjudicator getAdjudicator(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, Duration playerTimeout, Writer log);
}
