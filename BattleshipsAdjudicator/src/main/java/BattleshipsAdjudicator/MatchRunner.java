package BattleshipsAdjudicator;

import com.google.common.collect.Lists;
import org.joda.time.Duration;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class MatchRunner implements IMatchRunner
{
  private IBattleshipsPlayerWrapper _player1;
  private IBattleshipsPlayerWrapper _player2;
  private IAdjudicatorFactory _adjudicatorFactory;
  private int _numberOfRounds;
  private Duration _playerTimeout = Duration.ZERO;
  private IGameLogProvider _gamelogProvider;

  public MatchRunner(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, int numberOfRounds, Duration playerTimeout, IAdjudicatorFactory adjudicatorFactory, IGameLogProvider gamelogProvider) {
    _player1 = player1;
    _player2 = player2;
    _numberOfRounds = numberOfRounds;
    _playerTimeout = playerTimeout;
    _adjudicatorFactory = adjudicatorFactory;
    _gamelogProvider = gamelogProvider;
  }

  public final IMatchResult RunMatch() throws IOException {
    ArrayList<IGameResult> gameResults = new ArrayList<>();
    IBattleshipsPlayerWrapper gamePlayer1;
    IBattleshipsPlayerWrapper gamePlayer2;

    for (int i = 0; i < _numberOfRounds; i++) {
      gamePlayer1 = i % 2 == 0 ? _player1 : _player2;
      gamePlayer2 = i % 2 == 0 ? _player2 : _player1;
      Writer gameLog = _gamelogProvider == null ? null : _gamelogProvider.NextLog();

      try {
        IAdjudicator adjudicator = _adjudicatorFactory.getAdjudicator(gamePlayer1, gamePlayer2, _playerTimeout, gameLog);
        gameResults.add(adjudicator.RunGame());
      }
      finally
      {
        if (gameLog != null)
        {
          gameLog.close();
        }
      }
    }

    return CreateMatchResult(gameResults);
  }

  private IMatchResult CreateMatchResult(ArrayList<IGameResult> gameResults)
  {
    Integer numberOfDraws = toIntExact(gameResults.stream().filter(gr -> gr.getWinner() != null).count());

    Map<IBattleshipsPlayerWrapper, List<IGameResult>> playerResults = gameResults
            .stream()
            .collect(Collectors.groupingBy(IGameResult::getWinner));

    List<IBattleshipsPlayerWrapper> players = Lists.newArrayList(playerResults.keySet());

    int winnerGameWins = 0;
    int loserGameWins = 0;
    IBattleshipsPlayerWrapper winner = null;
    IBattleshipsPlayerWrapper loser = null;

    if (players.size() > 2)
    {
      throw new IllegalStateException("Match results contain more than 2 players!");
    }

    if (players.size() == 1)
    {
      winner = players.get(0);
      winnerGameWins = playerResults.get(winner).size();
    }
    else if (players.size() == 2)
    {
      IBattleshipsPlayerWrapper player1 = players.get(0);
      IBattleshipsPlayerWrapper player2 = players.get(1);

      int player1Wins = playerResults.get(player1).size();
      int player2Wins = playerResults.get(player2).size();

      // Set the winner (draws are handled later).
      if (player1Wins > player2Wins) {
        winner = player1;
        loser = player2;
      } else {
        winner = player2;
        loser = player1;
      }

      winnerGameWins = playerResults.get(winner).size();
      loserGameWins = playerResults.get(loser).size();

      // Handle a draw.
      if (player1Wins == player2Wins) {
        winner = null;
      }
    }

    MatchResult tempVar = new MatchResult();
    tempVar.setWinner(winner);
    tempVar.setWinnerWins(winnerGameWins);
    tempVar.setLoserWins(loserGameWins);
    tempVar.setDraws(numberOfDraws);
    tempVar.setPlayer1(_player1);
    tempVar.setPlayer2(_player2);
    return tempVar;
  }

  private static class MatchResult implements IMatchResult
  {
    private IBattleshipsPlayerWrapper Winner;
    public final IBattleshipsPlayerWrapper getWinner() {
      return Winner;
    }
    public final void setWinner(IBattleshipsPlayerWrapper value) {
      Winner = value;
    }
    private IBattleshipsPlayerWrapper Player1;
    public final IBattleshipsPlayerWrapper getPlayer1() {
      return Player1;
    }
    public final void setPlayer1(IBattleshipsPlayerWrapper value) {
      Player1 = value;
    }
    private IBattleshipsPlayerWrapper Player2;
    public final IBattleshipsPlayerWrapper getPlayer2() {
      return Player2;
    }
    public final void setPlayer2(IBattleshipsPlayerWrapper value) {
      Player2 = value;
    }
    private int WinnerWins;
    public final int getWinnerWins() {
      return WinnerWins;
    }
    public final void setWinnerWins(int value) {
      WinnerWins = value;
    }
    private int LoserWins;
    public final int getLoserWins() {
      return LoserWins;
    }
    public final void setLoserWins(int value) {
      LoserWins = value;
    }
    private int Draws;
    public final int getDraws() {
      return Draws;
    }
    public final void setDraws(int value) {
      Draws = value;
    }
  }
}
