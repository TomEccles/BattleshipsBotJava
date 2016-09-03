package BattleshipsAdjudicator;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.*;

public class MatchResultPrinter {
  private List<IMatchResult> _results;

  public MatchResultPrinter(List<IMatchResult> results) {
    _results = results;
  }

  public final void Print(PrintStream printer) throws IOException {
    printer.println("Match results:");

    for (IMatchResult result : _results) {
      PrintResult(result, printer);
    }
  }

  private void PrintResult(IMatchResult result, PrintStream printer) throws IOException {
    if (result.getWinner() == null) {
      printer.println(String.format("%s vs %s - draw: (%d) wins to (%d) wins ((%d) draws)", result.getPlayer1().getName(), result.getPlayer2().getName(), result.getWinnerWins(), result.getLoserWins(), result.getDraws()));
    } else {
      printer.println(String.format("%s vs %s - %s wins: %d wins to %d wins (%d draws)", result.getPlayer1().getName(), result.getPlayer2().getName(), result.getWinner().getName(), result.getWinnerWins(), result.getLoserWins(), result.getDraws()));
    }
  }
}
