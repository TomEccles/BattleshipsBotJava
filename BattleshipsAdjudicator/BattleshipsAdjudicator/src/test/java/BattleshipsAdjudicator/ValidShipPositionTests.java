package BattleshipsAdjudicator;

import BattleshipsAdjudicator.Mocks.ShipPosition;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidShipPositionTests {
  @Test
  public void TestValidShip()
  {
    TestPosition(BattleshipsInterface.EShipType.patrolBoat, "C04C03");
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestDiagonalShip()
  {
    TestPosition("A01B02");
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestSinglePointShip()
  {
    TestPosition("A01A01");
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestWrongLengthAircraftCarrier()
  {
    TestPosition(BattleshipsInterface.EShipType.aircraftCarrier, "A01A02");
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestWrongLengthBattleship()
  {
    TestPosition(BattleshipsInterface.EShipType.battleship, "A03A01");
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestWrongLengthDestroyer()
  {
    TestPosition(BattleshipsInterface.EShipType.destroyer, "A01A04");
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestWrongLengthSubmarine()
  {
    TestPosition(BattleshipsInterface.EShipType.submarine, "A01E01");
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestWrongLengthPatrolBoat()
  {
    TestPosition(BattleshipsInterface.EShipType.patrolBoat, "A01A06");
  }

  @Test
  public void TestShipHit()
  {
    // Arrange
    ValidShipPosition postion = new ValidShipPosition(GetPatrolBoat());

    // Act
    EShotResult hit = postion.ShotFired('A', 1);

    // Assert
    assertThat(hit).isEqualByComparingTo(EShotResult.Hit);
    assertThat(postion.getSquares().stream().filter(BoardSquare::getIsHit).count()).isEqualTo(1);
    assertThat(postion.getIsSunk()).isFalse();
  }

  @Test
  public void TestShipMissed()
  {
    // Arrange
    ValidShipPosition postion = new ValidShipPosition(GetPatrolBoat());

    // Act
    EShotResult hit = postion.ShotFired('A', 2);

    // Assert
    assertThat(hit).isEqualByComparingTo(EShotResult.Miss);
    assertThat(postion.getSquares().stream().filter(BoardSquare::getIsHit).count()).isEqualTo(0);
    assertThat(postion.getIsSunk()).isFalse();
  }

  @Test
  public void TestShipHitTwice()
  {
    // Arrange
    ValidShipPosition postion = new ValidShipPosition(GetPatrolBoat());

    // Act
    EShotResult hit1 = postion.ShotFired('A', 1);
    EShotResult hit2 = postion.ShotFired('A', 1);

    // Assert
    assertThat(hit1).isEqualByComparingTo(EShotResult.Hit);
    assertThat(hit2).isEqualByComparingTo(EShotResult.Hit);
    assertThat(postion.getSquares().stream().filter(BoardSquare::getIsHit).count()).isEqualTo(1);
    assertThat(postion.getIsSunk()).isFalse();
  }

  @Test
  public void TestShipSunk()
  {
    // Arrange
    ValidShipPosition postion = new ValidShipPosition(GetPatrolBoat());

    // Act
    EShotResult hit1 = postion.ShotFired('A', 1);
    EShotResult hit2 = postion.ShotFired('B', 1);

    // Assert
    assertThat(hit1).isEqualByComparingTo(EShotResult.Hit);
    assertThat(hit2).isEqualByComparingTo(EShotResult.HitAndSunk);
    assertThat(postion.getSquares().stream().filter(BoardSquare::getIsHit).count()).isEqualTo(2);
    assertThat(postion.getIsSunk()).isTrue();
  }

  private void TestPosition(String position)
  {
    TestPosition(BattleshipsInterface.EShipType.patrolBoat, position);
  }

  private void TestPosition(BattleshipsInterface.EShipType shipType, String position)
  {
    // Arrange
    ShipPosition postion = new ShipPosition(shipType, position);

    // Act & Asset
    new ValidShipPosition(postion);
  }

  private ShipPosition GetPatrolBoat()
  {
    return new ShipPosition(BattleshipsInterface.EShipType.patrolBoat, "A01B01");
  }
}
