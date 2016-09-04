package BattleshipsAdjudicator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandLineArgumentTests {
  @Test(expected = UsageException.class)
  public final void TestNullArguments() {
    // Act & Assert
    new CommandLineArguments(null);
  }

  @Test(expected = UsageException.class)
  public final void TestNoArguments() {
    // Act & Assert
    new CommandLineArguments(new String[0]);
  }

  @Test
  public final void TestPlayerDirectory() {
    // Arrange
    String playerDirectory = "playerDir";
    String[] stringArgs = new String[] {playerDirectory};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getPlayerDirectory()).isEqualTo(playerDirectory);
  }

  @Test
  public final void TestLogDirectoryNonDefault() {
    // Arrange
    String playerDirectory = "playerDir";
    String logDirectory = "logDir";
    String[] stringArgs = new String[] {playerDirectory, "-l:" + logDirectory};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getLogDirectory()).isEqualTo(logDirectory);
  }

  @Test
  public final void TestLogDirectoryDefault() {
    // Arrange
    String playerDirectory = "playerDir";
    String[] stringArgs = new String[] {playerDirectory};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getLogDirectory()).isEqualTo("logs");
  }

  @Test
  public final void TestNumberOfRoundsDefault() {
    // Arrange
    String playerDirectory = "playerDir";
    String[] stringArgs = new String[] {playerDirectory};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getNumberOfRounds()).isEqualTo(1);
  }

  @Test
  public final void TestNumberOfRoundsNonDefault() {
    // Arrange
    String playerDirectory = "playerDir";
    String[] stringArgs = new String[] {playerDirectory, "-r:1000"};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getNumberOfRounds()).isEqualTo(1000);
  }

  @Test
  public final void TestPlayerTimeoutDefault() {
    // Arrange
    String playerDirectory = "playerDir";
    String[] stringArgs = new String[] {playerDirectory};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getPlayerTimeout().getMillis()).isEqualTo(1000);
  }

  @Test
  public final void TestPlayerTimeoutNonDefault() {
    // Arrange
    String playerDirectory = "playerDir";
    String[] stringArgs = new String[] {playerDirectory, "-t:2000"};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getPlayerTimeout().getMillis()).isEqualTo(2000);
  }

  @Test
  public final void TestAllArguments() {
    // Arrange
    String playerDirectory = "playerDir";
    String logDirectory = "logDir";
    int numberOfRounds = 1000;
    String[] stringArgs = new String[] {playerDirectory, "-r:" + numberOfRounds, "-l:" + logDirectory};

    // Act
    CommandLineArguments args = new CommandLineArguments(stringArgs);

    // Assert
    assertThat(args.getPlayerDirectory()).isEqualTo(playerDirectory);
    assertThat(args.getNumberOfRounds()).isEqualTo(numberOfRounds);
    assertThat(args.getLogDirectory()).isEqualTo(logDirectory);
  }
}
