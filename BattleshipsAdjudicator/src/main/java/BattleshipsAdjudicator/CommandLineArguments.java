package BattleshipsAdjudicator;

import org.joda.time.Duration;

import java.nio.file.Paths;

public class CommandLineArguments {
  private String PlayerDirectory;
  public final String getPlayerDirectory()
  {
    return PlayerDirectory;
  }
  private void setPlayerDirectory(String value)
  {
    PlayerDirectory = value;
  }
  private int NumberOfRounds;
  public final int getNumberOfRounds()
  {
    return NumberOfRounds;
  }
  private void setNumberOfRounds(int value)
  {
    NumberOfRounds = value;
  }
  private String LogDirectory;
  public final String getLogDirectory()
  {
    return LogDirectory;
  }
  private void setLogDirectory(String value)
  {
    LogDirectory = value;
  }
  private Duration PlayerTimeout = Duration.ZERO;
  public final Duration getPlayerTimeout()
  {
    return PlayerTimeout;
  }
  private void setPlayerTimeout(Duration value)
  {
    PlayerTimeout = value;
  }

  public CommandLineArguments(String[] args) {
    if (args == null || args.length < 1) {
      throw new UsageException("No arguments specified");
    }

    setPlayerDirectory(args[0]);

    for (int i = 1; i < args.length; i++) {
      parseArgument(args[i]);
    }

    SetDefaults();
  }

  private void SetDefaults() {
    if (getNumberOfRounds() <= 0) {
      setNumberOfRounds(1);
    }

    if (getLogDirectory() == null) {
      setLogDirectory(Paths.get("logs").toString());
    }

    if (getPlayerTimeout() == Duration.ZERO) {
      setPlayerTimeout(new Duration(1000));
    }
  }

  private void parseArgument(String argument) {
    if (argument.length() < 3) {
      throw new UsageException("Unrecognised argument: " + argument);
    }

    String argumentType = argument.substring(0, 3);
    String argumentValue = argument.substring(3);
    switch (argumentType) {
      case "-r:":
        int numberOfRounds = 0;
        try {
          numberOfRounds = Integer.parseInt(argumentValue);
        } catch(Exception e) {
          throw new UsageException("Invalid number of rounds supplied: " + argumentValue);
        }
        setNumberOfRounds(numberOfRounds);
        break;
      case "-t:":
        int timeout = 0;
        try {
          timeout = Integer.parseInt(argumentValue);
        } catch(Exception e) {
          throw new UsageException("Invalid timeout supplied: " + argumentValue);
        }
        setPlayerTimeout(new Duration(timeout));
        break;
      case "-l:":
        setLogDirectory(argumentValue);
        break;
      default:
        throw new UsageException("Unrecognised argument: " + argument);
    }
  }

  public static String USAGE = "Usage:" + "\r\n" +
          "BattleshipsAdjudicator.exe <playerDirectory>s [-r:XXX] [-l:<log directory>] [-t:<timeout>]" + "\r\n" +
          "\r\n" +
          "<playerDirectory>   The full path to the directory of player dlls" + "\r\n" +
          "XXX                 The number of rounds to play, defaults to 1" + "\r\n" +
          "<log directory>     The directory for game logs, defaults to .\\logs" + "\r\n" +
          "<timeout>           Maxmimum milliseconds a bot can spend thinking in one game, if exceeded the bot loses";
}