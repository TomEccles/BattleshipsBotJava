package BattleshipsAdjudicator;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GameLogProviderTests {
  private Pattern LOG_FILE_MATCHER = Pattern.compile("BattleshipsTestLogFile.*\\.txt");
  private String LOG_FILE_SIMPLE_BASE_PATH = "BattleshipsTestLogFile.txt";
  private static String TEMP_DIR_PATH;

  @BeforeClass
  public static void SetLogFileBase() throws Exception {
    TEMP_DIR_PATH = "temp";
    new File(TEMP_DIR_PATH).mkdir();
  }

  @Test
  public final void TestFirstFileNaming() throws IOException {
    // Arrange
    AssertNoLogFilesPresent();
    GameLogProvider logProvider = new GameLogProvider(TEMP_DIR_PATH, LOG_FILE_SIMPLE_BASE_PATH);

    // Act
    Writer logger = logProvider.NextLog();
    logger.write("Test game log entry");
    logger.close();

    // Assert
    String logFilename = GetLogFiles().stream().findFirst().orElse(null);
    try {
      String expectedLogFilename = Paths.get(TEMP_DIR_PATH, "BattleshipsTestLogFile_Game1.txt").toString();
      assertThat(logFilename).isEqualTo(expectedLogFilename);
    } finally {
      File file = new File(logFilename);
      if (file.exists()) {
        file.delete();
      }
    }
  }

  @Test
  public final void TestFileNaming() throws IOException {
    // Arrange
    AssertNoLogFilesPresent();
    GameLogProvider logProvider = new GameLogProvider(TEMP_DIR_PATH, LOG_FILE_SIMPLE_BASE_PATH);

    // Act
    Writer logger = logProvider.NextLog();
    logger.write("Test game log entry");
    logger.close();
    logger = logProvider.NextLog();
    logger.write("Test game log entry");
    logger.close();

    // Assert
    List<String> logFilenames = GetLogFiles();
    try
    {
      String expectedLogFilename = Paths.get(TEMP_DIR_PATH, "BattleshipsTestLogFile_Game1.txt").toString();
      String expectedLogFilename2 = Paths.get(TEMP_DIR_PATH, "BattleshipsTestLogFile_Game2.txt").toString();

      assertThat(logFilenames).contains(expectedLogFilename);
      assertThat(logFilenames).contains(expectedLogFilename2);
    } finally {
      for (String logFilename : logFilenames) {
        File file = new File(logFilename);
        if (file.exists()) {
          file.delete();
        }
      }
    }
  }

  private void AssertNoLogFilesPresent() throws IOException {
    List<String> tempFiles = GetLogFiles();
    assertThat(tempFiles).isEmpty();
  }

  private List<String> GetLogFiles() throws IOException {
    return Files.walk(Paths.get(TEMP_DIR_PATH))
            .map(f -> f.toString())
            .filter(file -> LOG_FILE_MATCHER.matcher(file).find())
            .collect(Collectors.toList());
  }
}
