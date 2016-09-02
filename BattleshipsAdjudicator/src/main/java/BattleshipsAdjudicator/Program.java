package BattleshipsAdjudicator;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Program {
  static int main(String[] args) throws IOException {
    try {
      CommandLineArguments parsedArgs = new CommandLineArguments(args);

      ArrayList<IBattleshipsPlayerWrapper> players = LoadPlayers(parsedArgs.getPlayerDirectory());

      if (players.size() < 2) {
        System.out.printf("Insufficient players found (%1$s)" + "\r\n", players.size());
        return 2;
      }

      LeagueRunner leagueRunner = new LeagueRunner(players, parsedArgs.getNumberOfRounds(), parsedArgs.getPlayerTimeout(), new MatchRunnerFactory(), parsedArgs.getLogDirectory());

      leagueRunner.RunLeague();

      PrintResults(leagueRunner, System.out);

      try (PrintStream logWriter = new PrintStream(Paths.get(parsedArgs.getLogDirectory(), "Summary.txt").toString()))
      {
        PrintResults(leagueRunner, logWriter);
      }
    }
    catch (UsageException e)
    {
      System.err.println(e.getMessage());
      System.err.println();
      System.err.println(CommandLineArguments.USAGE);
      return 1;
    }

    return 0;
  }

  private static ArrayList<IBattleshipsPlayerWrapper> LoadPlayers(String directory)
  {
    ArrayList<IBattleshipsPlayerWrapper> ret = new ArrayList<IBattleshipsPlayerWrapper>();

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
    for (var playerFile : Directory.EnumerateFiles(directory, "*.dll"))
    {
      try
      {
        PlayerLoader playerLoader = new PlayerLoader(playerFile);
        ret.add(playerLoader.getPlayer());
      }
      catch (RuntimeException e)
      {
        System.out.printf("Error loading player from '%1$s':" + "\r\n", playerFile);
        System.out.println(e);
      }
    }

    return ret;
  }

  private static void PrintResults(LeagueRunner leagueRunner, PrintStream printer) throws IOException {
    MatchResultPrinter matchResultPrinter = new MatchResultPrinter(leagueRunner.getMatchResults());
    matchResultPrinter.Print(printer);

    printer.println();

    ResultPrinter resultPrinter = new ResultPrinter(leagueRunner.getLeagueScores());
    resultPrinter.Print(printer);
  }
}
