package BattleshipsAdjudicator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardSquareTests
{
  @Test
  public final void TestToStringPadding() {
    // Arrange
    BoardSquare square = new BoardSquare('A', 1);

    // Act
    String squareString = square.toString();

    // Assert
    assertThat(squareString).isEqualTo("A01");
  }

  @Test
  public final void TestRowCasing() {
    // Act
    BoardSquare square = new BoardSquare('a', 1);

    // Assert
    assertThat(square.getRow()).isEqualTo('A');
  }

  @Test(expected=InvalidBoardSquareException.class)
  public final void TestColumnTooLarge() {
    // Act
    new BoardSquare('A', 11);
  }

  @Test(expected=InvalidBoardSquareException.class)
  public final void TestColumnTooSmall() {
    // Act
    new BoardSquare('A', 0);
  }

  @Test(expected=InvalidBoardSquareException.class)
  public final void TestStartingRowTooLarge() {
    // Act
    new BoardSquare('K', 1);
  }

  @Test(expected=InvalidBoardSquareException.class)
  public final void TestStartingRowTooSmall() {
    // Act
    new BoardSquare((char)('A' - 1), 1);
  }

  @Test(expected=InvalidBoardSquareException.class)
  public final void TestStartingRowInvalid() {
    // Act
    new BoardSquare('&', 1);
  }

  @Test
  public final void TestTopRow() {
    // Arrange
    BoardSquare square = new BoardSquare('A', 4);

    // Assert
    assertThat(square.getIsOnTopEdge()).isTrue();
    assertThat(square.getIsOnBottomEdge()).isFalse();
  }

  @Test
  public final void TestBottomRow() {
    // Arrange
    BoardSquare square = new BoardSquare('J', 4);

    // Assert
    assertThat(square.getIsOnBottomEdge()).isTrue();
    assertThat(square.getIsOnTopEdge()).isFalse();
  }

  @Test
  public final void TestLeftColumn() {
    // Arrange
    BoardSquare square = new BoardSquare('E', 1);

    // Assert
    assertThat(square.getIsOnLeftEdge()).isTrue();
    assertThat(square.getIsOnRightEdge()).isFalse();
  }

  @Test
  public final void TestRightColumn() {
    // Arrange
    BoardSquare square = new BoardSquare('E', 10);

    // Assert
    assertThat(square.getIsOnRightEdge()).isTrue();
    assertThat(square.getIsOnLeftEdge()).isFalse();
  }
}
