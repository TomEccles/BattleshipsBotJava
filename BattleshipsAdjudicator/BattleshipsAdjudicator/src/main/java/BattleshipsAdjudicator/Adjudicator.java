package BattleshipsAdjudicator;

import BattleshipsInterface.IBattleshipsPlayer;
import BattleshipsInterface.ICoordinate;
import BattleshipsInterface.IShipPosition;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import org.joda.time.Duration;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Adjudicator implements IAdjudicator {
  /// <summary>
  ///   Flag to control whether we run bots operations in separate threads and abort them if they're too slow
  ///   *** you probably want to disable this if you're stepping through your bot!
  ///   TODO: Command line flag to disable this
  /// </summary>
  private final Boolean UseThreadKiller = true;

  private static ExecutorService executor = Executors.newCachedThreadPool();
  private static TimeLimiter timeLimiter = new SimpleTimeLimiter(executor);

  private Exception Error;
  public Exception getException(){
    return Error;
  }
  private IBattleshipsPlayerWrapper _player1;
  private IBattleshipsPlayerWrapper _player2;
  private Writer _log;
  private String _player1ShortName;
  private String _player2ShortName;
  private ShipSet _player1Ships;
  private ShipSet _player2Ships;
  private Duration _playerTimeout;
  private Duration _threadAbortTimeout;
  private Timer _player1Timer;
  private Timer _player2Timer;
  private int _logTurn;

  private class GameResult implements IGameResult {
    public IBattleshipsPlayerWrapper Winner;
    public GameResultType ResultType;

    GameResult(IBattleshipsPlayerWrapper Winner, GameResultType ResultType) {
      this.Winner = Winner;
      this.ResultType = ResultType;
    }

    public IBattleshipsPlayerWrapper getWinner() {
      return Winner;
    }
    public GameResultType getResultType() {
      return ResultType;
    }
  }

  Adjudicator(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, Duration playerTimeout, Writer log) {
    _player1 = player1;
    _player2 = player2;
    _player1ShortName = TrimBrackets(_player1.getName());
    _player2ShortName = TrimBrackets(_player2.getName());
    _playerTimeout = playerTimeout;
    _threadAbortTimeout = playerTimeout.multipliedBy(2);
    _log = log;
    Error = null;
  }

  public IGameResult RunGame() throws IOException {
    // Trigger garbage collection before the start of the game, to avoid a large
    // GC accidentally timing out one of the players
    System.gc();

    Log("Player 1: %s", _player1.getName());
    Log("\r\n");
    Log("Player 2: %s", _player2.getName());
    Log("\r\n");
    Log("Timeout : %s ms", _playerTimeout.getMillis());
    Log("\r\n");

    _player1Timer = new Timer(_playerTimeout);
    _player2Timer = new Timer(_playerTimeout);

    _player1Ships = GetShips(_player1, _player1Timer);

    if (_player1Ships == null) {
      return getResult(_player2, GameResultType.victoryInvalidShips);
    }

    _player2Ships = GetShips(_player2, _player2Timer);

    if (_player2Ships == null) {
      return getResult(_player1, GameResultType.victoryInvalidShips);
    }

    Log(GetBoards());
    Log("\r\n");

    int turnCounter = 1;
    ICoordinate lastShot;

    while (true) {
      try {
        lastShot = TakeTurn(turnCounter, _player1, _player2Ships, _player1Timer);

        if (lastShot == null) {
          return getResult(_player2, GameResultType.victoryInvalidShot);
        }
      } catch (Exception e) {
        return getResultForException(_player2, e);
      }

      try {
        NotifyOpponentsShot(_player2, _player2Timer, lastShot);
      } catch (Exception e) {
        return getResultForException(_player1, e);
      }

      if (_player2Ships.getAllSunk()) {
        return getResult(_player1, GameResultType.victory);
      }

      try {
        lastShot = TakeTurn(turnCounter, _player2, _player1Ships, _player2Timer);

        if (lastShot == null) {
          return getResult(_player1, GameResultType.victoryInvalidShot);
        }
      } catch (Exception e) {
        return getResultForException(_player1, e);
      }

      try {
        NotifyOpponentsShot(_player1, _player1Timer, lastShot);
      } catch (Exception e) {
        return getResultForException(_player2, e);
      }

      if (_player1Ships.getAllSunk()) {
        return getResult(_player2, GameResultType.victory);
      }

      turnCounter++;

      if (turnCounter > 100) {
        return getDraw();
      }
    }
  }

  private IGameResult getDraw() throws IOException {
    Log("Game drawn");
    Log("\r\n");
    return new GameResult(null, GameResultType.draw);
  }

  private IGameResult getResultForException(IBattleshipsPlayerWrapper winner, Exception e) throws IOException {
    Error = e;
    GameResultType result = ((e instanceof OutOfTimeException) || (e instanceof TimeoutException))
    ? GameResultType.victoryTimeout
    : GameResultType.victoryException;
    return getResult(winner, result);
  }

  private IGameResult getResult(IBattleshipsPlayerWrapper winner, GameResultType gameResultType) throws IOException {
    Log("%s won the game by %s", winner.getName(), gameResultType);
    Log("\r\n");
    Boolean haveTimers = false;
    if (_player1Timer != null) {
      haveTimers = true;
      Log("%s clock: %s ms", _player1ShortName, _player1Timer.getElapsedMilliseconds());
      Log("\r\n");
    }
    if (_player2Timer != null) {
      haveTimers = true;
      Log("%s clock: %s ms", _player2ShortName, _player2Timer.getElapsedMilliseconds());
      Log("\r\n");
    }
    if (Error != null) {
      if (haveTimers) {
        Log("\r\n");
      }
      Log("Error thrown: %s", Error);
      Log("\r\n");
    }
    return new GameResult(winner, gameResultType);
  }

  private <T> T callAndMaybeMonitorForTimeout(Callable<T> callback, Duration timeout) throws Exception {
    if (UseThreadKiller) {
      Callable<T> proxy = timeLimiter.newProxy(
              callback,
              Callable.class,
              timeout.getMillis(),
              TimeUnit.MILLISECONDS
      );

      return proxy.call();
    } else {
      return callback.call();
    }
  }

  private ShipSet GetShips(IBattleshipsPlayer player, Timer playerTimer) {
    try {
      Callable<Iterable<IShipPosition>> internalGetShips = () ->  {
        playerTimer.Start();
        Iterable<IShipPosition> playerShips = player.GetShipPositions();
        playerTimer.Stop();
        return playerShips;
      };

      Iterable<IShipPosition> ships = callAndMaybeMonitorForTimeout(internalGetShips, _threadAbortTimeout);

      // This will throw is ships are not valid.
      ShipSet validShips = new ShipSet(Lists.newArrayList(ships.iterator()).stream().map(ValidShipPosition::new).collect(Collectors.toList()));
      ShipSetValidator validator = new ShipSetValidator(validShips);
      validator.AssertAreValid();
      return validShips;
    } catch (Exception e) {
      Error = e;
      return null;
    }
  }

  private void NotifyOpponentsShot(IBattleshipsPlayer player, Timer playerTimer, ICoordinate lastShot) throws Exception {
    Callable<Boolean> internalOpponentsShot = () -> {
      playerTimer.Start();
      player.OpponentsShot(lastShot.getRow(), lastShot.getColumn());
      playerTimer.Stop();
      return true;
    };

    callAndMaybeMonitorForTimeout(internalOpponentsShot, _threadAbortTimeout);
  }

  private ICoordinate TakeTurn(int turnCounter, IBattleshipsPlayer playerA, ShipSet playerBShips, Timer playerATimer) throws Exception {
    Callable<ICoordinate> internalSelectTarget = () -> {
      playerATimer.Start();
      ICoordinate coordinate = playerA.SelectTarget();
      playerATimer.Stop();
      return coordinate;
    };

    ICoordinate target = callAndMaybeMonitorForTimeout(internalSelectTarget, _threadAbortTimeout);

    ICoordinate shot = GetValidShot(target.getRow(), target.getColumn());

    if (shot != null) {
      EShotResult shotResult = playerBShips.ShotFired(target.getRow(), target.getColumn());
      LogTurn(turnCounter, target.getRow(), target.getColumn(), shotResult);
      Boolean hit = (shotResult != EShotResult.Miss);
      Callable<Boolean> internalShotResult = () -> {
        playerATimer.Start();
        playerA.ShotResult(hit);
        playerATimer.Stop();
        return true;
      };

      callAndMaybeMonitorForTimeout(internalShotResult, _threadAbortTimeout);
    }

    return shot;
  }

  private Coordinate GetValidShot(char row, int column) {
    if ((row < 'a' || row > 'j') && (row < 'A' || row > 'J')) {
      return null;
    }
    if (column < 1 || column > 10) {
      return null;
    }

    return new Coordinate(row, column);
  }

  private void appendBlanks(StringBuilder builder, int number) {
    if (number > 0) {
      char[] charArray = new char[number];
      Arrays.fill(charArray, ' ');
      String blankSpace = new String(charArray);
      builder.append(blankSpace);
    }
  }

  private String GetBoard(String name, ShipSet ships) {
    StringBuilder board = new StringBuilder();
    int indent = (26 - name.length()) / 2;
    if (indent > 0) {
      appendBlanks(board, indent);
    }
    board.append(name);
    board.append("\r\n");
    board.append("\r\n");
    board.append(new BoardPrinter(ships).getBoard());
    return board.toString();
  }

  private List<String> formatBoard(String board) {
    return Lists.newArrayList(board.split("\r\n"));
  }

  private String GetBoards() {
    List<String> board1Lines = formatBoard(GetBoard(_player1ShortName, _player1Ships));
    List<String> board2Lines = formatBoard(GetBoard(_player2ShortName, _player2Ships));

    int columnWidth = board1Lines.stream().map(String::length).max(Integer::max).get() + 10;
    long lines = (board1Lines.size() > board2Lines.size()) ? board1Lines.size() : board2Lines.size();

    StringBuilder ret = new StringBuilder();
    for(int i = 0; i < lines; ++i) {
      appendBlanks(ret, 3);
      String col1 = (i < board1Lines.size()) ? board1Lines.get(i) : "";
      ret.append(col1);
      String col2 = (i < board2Lines.size()) ? board2Lines.get(i) : "";
      if (!col2.equals("")) {
        appendBlanks(ret, columnWidth - col1.length());
        ret.append(col2);
        ret.append("\r\n");
      } else {
        ret.append("\r\n");
      }
    }

    return ret.toString();
  }

  private void LogTurn(int turn, char row, int col, EShotResult hit) throws IOException {
    if (_log != null) {
      if (_logTurn != turn) {
        _log.write("\r\n");
        _log.write(String.format("%5s.", turn));
        _logTurn = turn;
      }
      String result;
      switch(hit) {
        case Hit:
          result = "+";
          break;
        case HitAndSunk:
          result = "+!";
          break;
        default:
          result = "";
          break;
      }
      _log.write(String.format("%3s%02d %-3s", row, col, result));
    }
  }

  private void Log(String message, Object... args) throws IOException {
    if (_log != null) {
      if (_logTurn > 0) {
        _log.write("\r\n");
        _log.write("\r\n");
        _logTurn = 0;
      }
      _log.write(String.format(message, args));
    }
  }

  private static String TrimBrackets(String name) {
    if (name != null) {
      int bracket = name.indexOf('(');
      if (bracket > 0) {
        name = name.substring(0, bracket);
      }
      return name.trim();
    }
    return null;
  }
}
