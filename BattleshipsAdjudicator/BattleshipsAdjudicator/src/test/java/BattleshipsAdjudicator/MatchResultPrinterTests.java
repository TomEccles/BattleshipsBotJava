package BattleshipsAdjudicator;

import java.io.*;
import java.util.*;

import BattleshipsAdjudicator.*;
import BattleshipsAdjudicator.Mocks.MockMatchResult;
import BattleshipsAdjudicator.Mocks.SafeNamePlayer;
import com.google.common.collect.Lists;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchResultPrinterTests {
  @Test
  public final void TestMatchResults() throws IOException {
    SafeNamePlayer player1 = new SafeNamePlayer("amy");
    SafeNamePlayer player2 = new SafeNamePlayer("bob");

    // Arrange
    MockMatchResult tempVar = new MockMatchResult();
    tempVar.setPlayer1(player1);
    tempVar.setPlayer2(player2);
    tempVar.setWinner(player1);
    tempVar.setWinnerWins(5);
    tempVar.setLoserWins(3);
    tempVar.setDraws(1);
    MockMatchResult tempVar2 = new MockMatchResult();
    tempVar2.setPlayer1(player1);
    tempVar2.setPlayer2(player2);
    tempVar2.setWinner(player2);
    tempVar2.setWinnerWins(1);
    tempVar2.setLoserWins(0);
    tempVar2.setDraws(0);
    MockMatchResult tempVar3 = new MockMatchResult();
    tempVar3.setPlayer1(player1);
    tempVar3.setPlayer2(player2);
    tempVar3.setWinner(null);
    tempVar3.setWinnerWins(3);
    tempVar3.setLoserWins(3);
    tempVar3.setDraws(2);
    ArrayList<IMatchResult> results = Lists.newArrayList(tempVar, tempVar2, tempVar3);
    MatchResultPrinter resultPrinter = new MatchResultPrinter(results);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream streamWriter = new PrintStream(outputStream);

    // Act
    resultPrinter.Print(streamWriter);

    // Assert
    streamWriter.flush();
    String resultString = new String(outputStream.toByteArray());
    assertThat(resultString).isEqualTo("Match results:" + "\r\n" +
      "amy vs bob - amy wins: 5 wins to 3 wins (1 draws)" + "\r\n" +
      "amy vs bob - bob wins: 1 wins to 0 wins (0 draws)" + "\r\n" +
      "amy vs bob - draw: 3 wins to 3 wins (2 draws)" + "\r\n");
  }
}
