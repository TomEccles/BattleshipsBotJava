package BattleshipsAdjudicator;

import BattleshipsAdjudicator.Mocks.*;
import BattleshipsInterface.IBattleshipsPlayer;
import com.google.common.collect.Lists;
import static org.assertj.core.api.Assertions.*;
import org.joda.time.Duration;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.fail;

public class LeagueRunnerTests {
  
  private Duration PlayerTimeout = Duration.standardSeconds(1);

  @Test
  public void TestAllPlayersHaveUniqueNames()
  {
    // Arrange
    List<IBattleshipsPlayerWrapper> players = new ArrayList<>();
    players.add(new SafeNamePlayer());
    players.add(new SafeNamePlayer());

    // Act & Assert
    try
    {
      LeagueRunner runner = new LeagueRunner(players, 1, PlayerTimeout, new MockMatchRunnerFactory(Collections.EMPTY_LIST), ".");
      fail("League runner didn't throw DuplicatePlayerNameException when given identically named players");
    }
    catch (DuplicatePlayerNameException e)
    {
      return;
    }
  }

  @Test
  public void TestAllPlayersPlayEachOtherOnce() throws Exception {
    // Arrange
    IBattleshipsPlayerWrapper player1 = new SafeNamePlayer("player1");
    IBattleshipsPlayerWrapper player2 = new SafeNamePlayer("player2");
    IBattleshipsPlayerWrapper player3 = new SafeNamePlayer("player3");
    List<IBattleshipsPlayerWrapper> players = Lists.newArrayList(player1, player2, player3);
    List<MockMatchRunner> matchRunners = Lists.newArrayList(
      new MockMatchRunner(player1, player2),
      new MockMatchRunner(player1, player3),
      new MockMatchRunner(player2, player3));
    IMatchRunnerFactory factory = new MockMatchRunnerFactory(matchRunners);
    LeagueRunner leagueRunner = new LeagueRunner(players, 1, PlayerTimeout, factory, ".");

    // Act
    leagueRunner.RunLeague();

    // Assert
    assertThat(matchRunners.stream().filter(mr -> mr.getNumberOfRuns() == 1).count()).isEqualTo(3);
  }

  @Test
  public void TestPlayersAreResetBetweenMatches() throws Exception {
    // Arrange
    ReportingPlayer player1 = new ReportingPlayer("player1");
    ReportingPlayer player2 = new ReportingPlayer("player2");
    ReportingPlayer player3 = new ReportingPlayer("player3");
    List<IBattleshipsPlayerWrapper> players = Lists.newArrayList(player1, player2, player3);
    List<MockMatchRunner> matchRunners = Lists.newArrayList(
      new MockMatchRunner(player1, player2),
      new MockMatchRunner(player1, player3),
      new MockMatchRunner(player2, player3));
    IMatchRunnerFactory factory = new MockMatchRunnerFactory(matchRunners);
    LeagueRunner leagueRunner = new LeagueRunner(players, 2, PlayerTimeout, factory, ".");

    // Act
    leagueRunner.RunLeague();

    // Assert
    assertThat(player1.getMethodsCalled().stream().filter(s -> s.equals(ReportingPlayer.METHOD_RESET_PLAYER)).count()).isEqualTo(2);
    assertThat(player2.getMethodsCalled().stream().filter(s -> s.equals(ReportingPlayer.METHOD_RESET_PLAYER)).count()).isEqualTo(2);
    assertThat(player3.getMethodsCalled().stream().filter(s -> s.equals(ReportingPlayer.METHOD_RESET_PLAYER)).count()).isEqualTo(2);
  }

  @Test
  public void TestLeagueSimpleScoring() throws Exception {
    // Arrange
    IBattleshipsPlayerWrapper player1 = new SafeNamePlayer("player1");
    IBattleshipsPlayerWrapper player2 = new SafeNamePlayer("player2");
    IBattleshipsPlayerWrapper player3 = new SafeNamePlayer("player3");
    List<IBattleshipsPlayerWrapper> players = Lists.newArrayList(player1, player2, player3);
    List<MockMatchRunner> matchRunners = Lists.newArrayList(
            new MockMatchRunner(player1, player2),
            new MockMatchRunner(player1, player3),
            new MockMatchRunner(player2, player3));
    IMatchRunnerFactory factory = new MockMatchRunnerFactory(matchRunners);
    LeagueRunner leagueRunner = new LeagueRunner(players, 1, PlayerTimeout, factory, ".");

    // Act
    leagueRunner.RunLeague();
    Map<IBattleshipsPlayerWrapper, Integer> result = leagueRunner.getLeagueScores();

    // Assert
    assertThat(result.get(player1)).isEqualTo(6);
    assertThat(result.get(player2)).isEqualTo(3);
    assertThat(result.get(player3)).isEqualTo(0);
  }

  @Test
  public void TestLeagueDrawScoring() throws Exception {
    // Arrange
    IBattleshipsPlayerWrapper player1 = new SafeNamePlayer("player1");
    IBattleshipsPlayerWrapper player2 = new SafeNamePlayer("player2");
    IBattleshipsPlayerWrapper player3 = new SafeNamePlayer("player3");
    List<IBattleshipsPlayerWrapper> players = Lists.newArrayList(player1, player2, player3);
    List<MockMatchRunner> matchRunners = Lists.newArrayList(
            new MockMatchRunner(player1, player2),
            new MockMatchRunner(player1, player3),
            new MockDrawingMatchRunner(player2, player3));
    IMatchRunnerFactory factory = new MockMatchRunnerFactory(matchRunners);
    LeagueRunner leagueRunner = new LeagueRunner(players, 1, PlayerTimeout, factory, ".");

    // Act
    leagueRunner.RunLeague();
    Map<IBattleshipsPlayerWrapper, Integer> result = leagueRunner.getLeagueScores();

    // Assert
    assertThat(result.get(player1)).isEqualTo(6);
    assertThat(result.get(player2)).isEqualTo(1);
    assertThat(result.get(player3)).isEqualTo(1);
  }
}
