package BattleshipsAdjudicator;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Program {
  public static void main(String[] args) throws Exception {
    try {
      CommandLineArguments parsedArgs = new CommandLineArguments(args);

      ArrayList<IBattleshipsPlayerWrapper> players = LoadPlayers(parsedArgs.getPlayerDirectory());

      if (players.size() < 2) {
        System.out.printf("Insufficient players found (%1$s)" + "\r\n", players.size());
      }

      LeagueRunner leagueRunner = new LeagueRunner(players, parsedArgs.getNumberOfRounds(), parsedArgs.getPlayerTimeout(), new MatchRunnerFactory(), parsedArgs.getLogDirectory());

      leagueRunner.RunLeague();

      PrintResults(leagueRunner, System.out);

      try (PrintStream logWriter = new PrintStream(Paths.get(parsedArgs.getLogDirectory(), "Summary.txt").toString())) {
        PrintResults(leagueRunner, logWriter);
      }
    } catch (UsageException e) {
      System.err.println(e.getMessage());
      System.err.println();
      System.err.println(CommandLineArguments.USAGE);
    }
  }

  private static ArrayList<IBattleshipsPlayerWrapper> LoadPlayers(String directory) throws Exception {
    ArrayList<IBattleshipsPlayerWrapper> ret = new ArrayList<>();

    FilenameFilter filterJars = (File dir, String name) -> FilenameUtils.isExtension(name, "jar");

    for (File playerFile : new File(directory).listFiles(filterJars)) {
      try {
        PlayerLoader playerLoader = new PlayerLoader(playerFile);
        ret.add(playerLoader.getPlayer());
      } catch (RuntimeException e) {
        System.out.printf("Error loading player from '%1$s':" + "\r\n", playerFile);
        System.out.println(e.toString());
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
