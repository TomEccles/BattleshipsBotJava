package BattleshipsAdjudicator;

import org.joda.time.Duration;

import java.io.IOException;
import java.util.*;

public class LeagueRunner
{
  private static final int PointsForAWin = 3;
  private static final int PointsForADraw = 1;

  private Map<IBattleshipsPlayerWrapper, Integer> LeagueScores;
  public final Map<IBattleshipsPlayerWrapper, Integer> getLeagueScores() {
    return LeagueScores;
  }
  private void setLeagueScores(Map<IBattleshipsPlayerWrapper, Integer> value) {
    LeagueScores = value;
  }
  private List<IMatchResult> MatchResults;
  public final List<IMatchResult> getMatchResults()
  {
    return MatchResults;
  }
  private void setMatchResults(List<IMatchResult> value) {
    MatchResults = value;
  }

  private List<IBattleshipsPlayerWrapper> _playerWrappers;
  private IMatchRunnerFactory _factory;
  private int _numberOfRounds;
  private Duration _playerTimeout = Duration.ZERO;
  private String _logDirectory;

  public LeagueRunner(List<IBattleshipsPlayerWrapper> playerWrappers, int numberOfRoundsPerMatch, Duration playerTimeout, IMatchRunnerFactory factory, String logDirectory) {
    _playerWrappers = playerWrappers;
    _factory = factory;
    _numberOfRounds = numberOfRoundsPerMatch;
    _playerTimeout = playerTimeout;
    _logDirectory = logDirectory;
    setMatchResults(new ArrayList<IMatchResult>());
    setLeagueScores(new HashMap<>());
    AssertPlayerNamesAreUnique(_playerWrappers);
  }

  /**
   Runs a league where every player plays every other player once
   */
  public final void RunLeague() throws IOException {
    for (IBattleshipsPlayerWrapper player : _playerWrappers) {
      getLeagueScores().put(player, 0);
    }

    for (int i = 0; i < _playerWrappers.size(); i++) {
      IBattleshipsPlayerWrapper player1 = _playerWrappers.get(i);

      for (int j = i + 1; j < _playerWrappers.size(); j++) {
        IBattleshipsPlayerWrapper player2 = _playerWrappers.get(j);

        player1.ResetPlayer();
        player2.ResetPlayer();

        String logFilename = player1.getName() + " vs. " + player2.getName() + ".txt";
        IMatchRunner runner = _factory.GetMatchRunner(player1, player2, _numberOfRounds, _playerTimeout, logFilename);

        IMatchResult matchResult = runner.RunMatch();

        getMatchResults().add(matchResult);
        AccumulateScores(matchResult);
      }
    }
  }

  private void AccumulateScores(IMatchResult matchResult) {
    if (matchResult.getWinner() != null) {
      AccumulateScore(matchResult.getWinner(), PointsForAWin);
    } else {
      AccumulateScore(matchResult.getPlayer1(), PointsForADraw);
      AccumulateScore(matchResult.getPlayer2(), PointsForADraw);
    }
  }

  private void AccumulateScore(IBattleshipsPlayerWrapper player, int score) {
    if (!getLeagueScores().keySet().stream().anyMatch(p -> p.equals(player))) {
      throw new IllegalStateException("Player is not in the league! " + player.getName());
    }

    getLeagueScores().put(player, getLeagueScores().get(player) + score);
  }

  private void AssertPlayerNamesAreUnique(Iterable<IBattleshipsPlayerWrapper> players) {
    HashMap<String, Boolean> playerNames = new HashMap<String, Boolean>();

    for (IBattleshipsPlayerWrapper player : players) {
      if (playerNames.containsKey(player.getName())) {
        throw new DuplicatePlayerNameException("More than one player is called " + player.getName());
      }

      playerNames.put(player.getName(), true);
    }
  }
}

