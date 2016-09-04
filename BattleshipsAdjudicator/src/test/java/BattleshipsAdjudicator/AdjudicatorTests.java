package BattleshipsAdjudicator;

import BattleshipsAdjudicator.Mocks.*;
import BattleshipsInterface.IBattleshipsPlayer;
import com.google.common.io.CountingOutputStream;
import org.assertj.core.api.Assert;
import org.joda.time.Duration;
import org.joda.time.ReadableDuration;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdjudicatorTests {
  private enum ExpectedGameResult
  {
    player1Victory,
    player2Victory,
    draw
  }

  private Duration longPlayerTimeout = Duration.standardDays(1);
  private Duration shortPlayerTimeout = Duration.millis(80);
  private int slowPlayerTimeout = 100;

  @Test
  public void TestOutputWriting() throws IOException {
    // Arrange
    CountingOutputStream outputStream = new CountingOutputStream(new ByteArrayOutputStream());
    OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
    IAdjudicator a = new Adjudicator(new SafeNamePlayer(), new SafeNamePlayerKiller(), longPlayerTimeout, outputWriter);

    // Act
    a.RunGame();

    // Assert
    assertThat(outputStream.getCount()).isGreaterThan(1000);
  }

  @Test
  public void TestAllInterfaceMethodsUsed() throws IOException {
    // Arrange
    ReportingPlayer player1 = new ReportingPlayer("player1");
    ReportingPlayer player2 = new ReportingPlayer("player2");
    IAdjudicator a = new Adjudicator(player1, player2, longPlayerTimeout, null);

    // Act
    IGameResult result = a.RunGame();

    // Assert
    List<String> player1Methods = player1.getMethodsCalled();
    List<String> player2Methods = player2.getMethodsCalled();

    assertThat(player1Methods).contains("Name:get");
    assertThat(player1Methods).contains("ShotResult");
    assertThat(player1Methods).contains("SelectTarget");
    assertThat(player1Methods).contains("GetShipPositions");
    assertThat(player1Methods).contains("OpponentsShot");
    assertThat(player2Methods).contains("Name:get");
    assertThat(player2Methods).contains("ShotResult");
    assertThat(player2Methods).contains("SelectTarget");
    assertThat(player2Methods).contains("GetShipPositions");
    assertThat(player2Methods).contains("OpponentsShot");
  }

  @Test
  public void TestInvalidPlayer1Ships() throws IOException {
    RunGameWithError(
      new InvalidShipPositionPlayer(),
      new SafeNamePlayer(),
      longPlayerTimeout,
      ExpectedGameResult.player2Victory,
      GameResultType.victoryInvalidShips);
  }

  @Test
  public void TestInvalidPlayer2Ships() throws IOException {
    RunGameWithError(
      new SafeNamePlayer(),
      new InvalidShipPositionPlayer(),
      longPlayerTimeout,
      ExpectedGameResult.player1Victory,
      GameResultType.victoryInvalidShips);
  }

  @Test
  public void TestExceptionPlayer1Ships() throws IOException {
    RunGameWithError(
            new ErroringPlayer(ErroringPlayer.errorType.getShipPositions),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShips);
  }

  @Test
  public void TestExceptionPlayer2Ships() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.getShipPositions),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShips);
  }

  @Test
  public void TestExceptionPlayer1SelectTarget() throws IOException {
    RunGameWithError(
            new ErroringPlayer(ErroringPlayer.errorType.selectTarget),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryException);
  }

  @Test
  public void TestExceptionPlayer2SelectTarget() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.selectTarget),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryException);
  }

  @Test
  public void TestExceptionPlayer1ShotResult() throws IOException {
    RunGameWithError(
            new ErroringPlayer(ErroringPlayer.errorType.shotResult),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryException);
  }

  @Test
  public void TestExceptionPlayer2ShotResult() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.shotResult),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryException);
  }

  @Test
  public void TestExceptionPlayer1OpponentsShot() throws IOException {
    RunGameWithError(
            new ErroringPlayer(ErroringPlayer.errorType.opponentsShot),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryException);
  }

  @Test
  public void TestExceptionPlayer2OpponentsShot() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.opponentsShot),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryException);
  }

  @Test
  public void TestPlayer1InvalidShot() throws IOException {
    RunGame(
            new ErroringPlayer(ErroringPlayer.errorType.shotRowLowerCaseHigh),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new ErroringPlayer(ErroringPlayer.errorType.shotRowLowerCaseLow),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new ErroringPlayer(ErroringPlayer.errorType.shotRowUpperCaseHigh),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new ErroringPlayer(ErroringPlayer.errorType.shotRowUpperCaseLow),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new ErroringPlayer(ErroringPlayer.errorType.shotColumnHigh),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new ErroringPlayer(ErroringPlayer.errorType.shotColumnLow),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShot);
  }

  @Test
  public void TestPlayer2InvalidShot() throws IOException {
    RunGame(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.shotRowLowerCaseHigh),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.shotRowLowerCaseLow),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.shotRowUpperCaseHigh),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.shotRowUpperCaseLow),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.shotColumnHigh),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShot);
    RunGame(
            new SafeNamePlayer(),
            new ErroringPlayer(ErroringPlayer.errorType.shotColumnLow),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShot);
  }

  @Test
  public void TestExceptionPlayer1SlowSelectTarget() throws IOException {
    RunGameWithError(
            new SlowPlayer(SlowPlayer.errorType.selectTarget, slowPlayerTimeout),
            new SafeNamePlayer(),
            shortPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryTimeout);
  }

  @Test
  public void TestExceptionPlayer2SlowSelectTarget() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new SlowPlayer(SlowPlayer.errorType.selectTarget, slowPlayerTimeout),
            shortPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryTimeout);
  }

  @Test
  public void TestExceptionPlayer1SlowGetShipPositions() throws IOException {
    RunGameWithError(
            new SlowPlayer(SlowPlayer.errorType.getShipPositions, slowPlayerTimeout),
            new SafeNamePlayer(),
            shortPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryInvalidShips);
  }

  @Test
  public void TestExceptionPlayer2SlowGetShipPositions() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new SlowPlayer(SlowPlayer.errorType.getShipPositions, slowPlayerTimeout),
            shortPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryInvalidShips);
  }

  @Test
  public void TestExceptionPlayer1SlowShotResult() throws IOException {
    RunGameWithError(
            new SlowPlayer(SlowPlayer.errorType.shotResult, slowPlayerTimeout),
            new SafeNamePlayer(),
            shortPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryTimeout);
  }

  @Test
  public void TestExceptionPlayer2SlowShotResult() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new SlowPlayer(SlowPlayer.errorType.shotResult, slowPlayerTimeout),
            shortPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryTimeout);
  }

  @Test
  public void TestExceptionPlayer1SlowOpponentsShot() throws IOException {
    RunGameWithError(
            new SlowPlayer(SlowPlayer.errorType.opponentsShot, slowPlayerTimeout),
            new SafeNamePlayer(),
            shortPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victoryTimeout);
  }

  @Test
  public void TestExceptionPlayer2SlowOpponentsShot() throws IOException {
    RunGameWithError(
            new SafeNamePlayer(),
            new SlowPlayer(SlowPlayer.errorType.opponentsShot, slowPlayerTimeout),
            shortPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victoryTimeout);
  }

  @Test
  public void TestDraw() throws IOException {
    RunGame(
            new SafeNamePlayer(),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.draw,
            GameResultType.draw);
  }

  @Test
  public void TestPlayer1Win() throws IOException {
    RunGame(
            new SafeNamePlayerKiller(),
            new SafeNamePlayer(),
            longPlayerTimeout,
            ExpectedGameResult.player1Victory,
            GameResultType.victory);
  }

  @Test
  public void TestPlayer2Win() throws IOException {
    RunGame(
            new SafeNamePlayer(),
            new SafeNamePlayerKiller(),
            longPlayerTimeout,
            ExpectedGameResult.player2Victory,
            GameResultType.victory);
  }

  private void RunGame(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, Duration playerTimeout, ExpectedGameResult expectedResult, GameResultType expectedResultType) throws IOException {
    // Arrange
    IBattleshipsPlayerWrapper expectedWinner = null;
    if (expectedResult == ExpectedGameResult.player1Victory) {
      expectedWinner = player1;
    }
    if (expectedResult == ExpectedGameResult.player2Victory) {
      expectedWinner = player2;
    }
    Adjudicator a = new Adjudicator(player1, player2, playerTimeout, null);

    // Act
    IGameResult result = a.RunGame();

    // Assert
    assertThat(result.getResultType()).isEqualTo(expectedResultType);
    assertThat(result.getWinner()).isEqualTo(expectedWinner);
    assertThat(a.getException()).isNull();
  }

  private void RunGameWithError(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2, Duration playerTimeout, ExpectedGameResult expectedResult, GameResultType expectedResultType) throws IOException {
    // Arrange
    IBattleshipsPlayerWrapper expectedWinner = null;
    if (expectedResult == ExpectedGameResult.player1Victory) {
      expectedWinner = player1;
    }
    if (expectedResult == ExpectedGameResult.player2Victory) {
      expectedWinner = player2;
    }
    Adjudicator a = new Adjudicator(player1, player2, playerTimeout, null);

    // Act
    IGameResult result = a.RunGame();

    // Assert
    assertThat(result.getResultType()).isEqualTo(expectedResultType);
    assertThat(result.getWinner()).isEqualTo(expectedWinner);
    assertThat(a.getException()).isNotNull();
  }
}

