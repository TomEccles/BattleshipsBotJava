package BattleshipsAdjudicator;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Map;
import java.util.SortedSet;

public class ResultPrinter {
  private Map<IBattleshipsPlayerWrapper, Integer> _scores;

  public ResultPrinter(Map<IBattleshipsPlayerWrapper, Integer> scores) {
    _scores = scores;
  }

  public final void Print(PrintStream printer) throws IOException {
    printer.print("League scores:");

    SortedSet<Map.Entry<IBattleshipsPlayerWrapper, Integer>> playersInOrder = SortingUtils.entriesSortedByValues(_scores);

    for (Map.Entry<IBattleshipsPlayerWrapper, Integer> playerScore : playersInOrder) {
      printer.println(playerScore.getKey().getName() + " " + playerScore.getValue());
    }
  }
}
