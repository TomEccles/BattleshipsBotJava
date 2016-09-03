package BattleshipsAdjudicator;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Paths;

public class GameLogProvider implements IGameLogProvider {
  private String _baseLogFilenamePrefix;
  private String _baseLogFilenameSuffix;
  private int _index = 0;

  public GameLogProvider(String baseLogFilePath) {
    String directory = (Paths.get(baseLogFilePath).toFile()).getParent();
    _baseLogFilenamePrefix = Paths.get(FilenameUtils.getBaseName(baseLogFilePath)).toString();
    _baseLogFilenameSuffix = FilenameUtils.getExtension(baseLogFilePath);

    EnsureDirectoryExists(directory);
  }

  private void EnsureDirectoryExists(String directory) {
    if (!(new java.io.File(directory)).isDirectory()) {
      (new java.io.File(directory)).mkdirs();
    }
  }

  public final Writer NextLog() throws IOException {
    _index++;
    String fileName = _baseLogFilenamePrefix + "_Game" + _index + _baseLogFilenameSuffix;
    OutputStream out = new FileOutputStream(Paths.get(fileName).toString());
    return new OutputStreamWriter(out);
  }
}