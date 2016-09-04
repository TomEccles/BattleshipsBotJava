package BattleshipsAdjudicator;

import BattleshipsAdjudicator.Mocks.*;
import com.google.common.collect.Lists;
import org.joda.time.Duration;
import org.junit.Test;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchRunnerTests {
  private SafeNamePlayer _player1 = new SafeNamePlayer();
  private SafeNamePlayer _player2 = new SafeNamePlayer();
  private int NumberOfRounds = 1000;
  private Duration PlayerTimeout = Duration.standardSeconds(1);

  @Test
  public void TestRoundsPlayed() throws IOException {
    // Arrange
    GameCountingAdjudicatorFactory adjudicatorFactory = new GameCountingAdjudicatorFactory();
    IMatchRunner runner = new MatchRunner(_player1, _player2, NumberOfRounds, PlayerTimeout, adjudicatorFactory, null);

    // Act
    runner.RunMatch();

    // Assert
    assertThat(adjudicatorFactory.getAdjudicatorsCreated()).isEqualTo(1000);
  }

  @Test
  public void TestNewLogUsedPerGame() throws IOException {
    // Arrange
    GameCountingAdjudicatorFactory adjudicatorFactory = new GameCountingAdjudicatorFactory();
    CountingLogProvider logProvider = new CountingLogProvider();
    IMatchRunner runner = new MatchRunner(_player1, _player2, NumberOfRounds, PlayerTimeout, adjudicatorFactory, logProvider);

    // Act
    runner.RunMatch();

    // Assert
    assertThat(logProvider.getLogsProvided()).isEqualTo(1000);
  }

  @Test
  public void TestFirstPlayerAlternates() throws IOException {
    // Arrange
    FirstTurnRecordingPlayer player1 = new FirstTurnRecordingPlayer();
    FirstTurnRecordingPlayer player2 = new FirstTurnRecordingPlayer();
    IAdjudicatorFactory adjudicatorFactory = new AdjudicatorFactory();
    IMatchRunner runner = new MatchRunner(player1, player2, 6, PlayerTimeout, adjudicatorFactory, null);

    // Act
    runner.RunMatch();

    // Assert
    assertThat(FirstTurnRecordingPlayer.getFirstPlayers().get(0)).isEqualTo(player1);
    assertThat(FirstTurnRecordingPlayer.getFirstPlayers().get(1)).isEqualTo(player2);
    assertThat(FirstTurnRecordingPlayer.getFirstPlayers().get(2)).isEqualTo(player1);
    assertThat(FirstTurnRecordingPlayer.getFirstPlayers().get(3)).isEqualTo(player2);
    assertThat(FirstTurnRecordingPlayer.getFirstPlayers().get(4)).isEqualTo(player1);
    assertThat(FirstTurnRecordingPlayer.getFirstPlayers().get(5)).isEqualTo(player2);
  }

  @Test
  public void TestAllDraws() throws IOException {
    TestMatchResult(Lists.newArrayList(
      new MockGameResult(null, GameResultType.draw ),
      new MockGameResult(null, GameResultType.draw ),
      new MockGameResult(null, GameResultType.draw )
    ),
    new MockMatchDetails(
      null,
      _player1,
      _player2,
      0,
      0,
      3
    ));
  }

  @Test
  public void TestScoreDraw() throws IOException {
    TestMatchResult(Lists.newArrayList(
      new MockGameResult(_player1, GameResultType.victory),
      new MockGameResult(null, GameResultType.draw),
      new MockGameResult(_player2, GameResultType.victory)
    ),
    new MockMatchDetails(
      null,
      _player1,
      _player2,
      1,
      1,
      1
    ));
  }

  @Test
  public void TestCloseVictory() throws IOException {
    TestMatchResult(Lists.newArrayList(
      new MockGameResult(_player1, GameResultType.victory),
      new MockGameResult(_player1, GameResultType.victoryInvalidShips),
      new MockGameResult(_player2, GameResultType.victory)
    ),
    new MockMatchDetails(
      _player1,
      _player1,
      _player2,
      2,
      1,
      0
    ));
  }

  @Test
  public void TestAllVictories() throws IOException {
    MockMatchDetails expectedResult = new MockMatchDetails(_player1, _player1, _player2, 1, 0, 0);

    TestMatchResult(Lists.newArrayList(new MockGameResult(_player1, GameResultType.victory)), expectedResult);
    TestMatchResult(Lists.newArrayList(new MockGameResult(_player1, GameResultType.victoryException)), expectedResult);
    TestMatchResult(Lists.newArrayList(new MockGameResult(_player1, GameResultType.victoryInvalidName)), expectedResult);
    TestMatchResult(Lists.newArrayList(new MockGameResult(_player1, GameResultType.victoryInvalidShips)), expectedResult);
  }

  private void TestMatchResult(List<MockGameResult> gameResults, MockMatchDetails expectedMatchDetails) throws IOException
  {
    // Arrange
    MockAdjudicatorFactory adjudicatorFactory = new MockAdjudicatorFactory(gameResults);
    IMatchRunner runner = new MatchRunner(_player1, _player2, gameResults.size(), PlayerTimeout, adjudicatorFactory, null);

    // Act
    IMatchResult result = runner.RunMatch();

    // Assert
    assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedMatchDetails);
  }
}
