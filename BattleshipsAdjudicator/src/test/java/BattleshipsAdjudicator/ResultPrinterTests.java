package BattleshipsAdjudicator.Tests;

import java.io.*;
import java.util.*;

import BattleshipsAdjudicator.*;
import BattleshipsAdjudicator.Mocks.SafeNamePlayer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultPrinterTests {
  @Test
  public final void TestLeagueResult() throws IOException {
    // Arrange
    HashMap<IBattleshipsPlayerWrapper, Integer> results = new HashMap<IBattleshipsPlayerWrapper, Integer>();
    results.put(new SafeNamePlayer("Player1"), 6);
    results.put(new SafeNamePlayer("Player2"), 8);
    results.put(new SafeNamePlayer("Player3"), 5);
    ResultPrinter resultPrinter = new ResultPrinter(results);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream streamWriter = new PrintStream(outputStream);

    // Act
    resultPrinter.Print(streamWriter);

    // Assert
    streamWriter.flush();

    String resultString = new String(outputStream.toByteArray());
    assertThat(resultString).isEqualTo(
      "League scores:" + "\r\n" +
      "Player2 8" + "\r\n" +
      "Player1 6" + "\r\n" +
      "Player3 5" + "\r\n");
  }
}
