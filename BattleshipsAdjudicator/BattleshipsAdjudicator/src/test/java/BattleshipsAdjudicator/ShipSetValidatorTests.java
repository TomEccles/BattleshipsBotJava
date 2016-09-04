package BattleshipsAdjudicator;

import BattleshipsAdjudicator.Mocks.ShipPosition;
import BattleshipsInterface.EShipType;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class ShipSetValidatorTests {
  @Test(expected = InvalidShipPostionException.class)
  public void TestNotEnoughShips() {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.remove(0);
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestTooManyShips()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.add(new ValidShipPosition(new ShipPosition(EShipType.aircraftCarrier, "I05I09")));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoAircraftCarrier()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(0, ChangeShipType(ships.get(0), EShipType.battleship));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoBattleship()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(1, ChangeShipType(ships.get(1), EShipType.aircraftCarrier));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoDestroyer()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(2, ChangeShipType(ships.get(2), EShipType.aircraftCarrier));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoSubmarine()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(3, ChangeShipType(ships.get(3), EShipType.aircraftCarrier));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoPatrolBoat()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(4, ChangeShipType(ships.get(4), EShipType.aircraftCarrier));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoDiagonallyTouchingBoats()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(0, ChangePosition(ships.get(0), "B05B09"));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoHorizontallyTouchingBoats()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(0, ChangePosition(ships.get(0), "C05C09"));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoVerticallyTouchingBoats()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(0, ChangePosition(ships.get(0), "A10E10"));
    ships.set(1, ChangePosition(ships.get(1), "F10I10"));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  @Test(expected = InvalidShipPostionException.class)
  public void TestNoOverlappingBoats()
  {
    // Arrange
    List<ValidShipPosition> ships = GetDefaultShips();
    ships.set(0, new ValidShipPosition(new ShipPosition(ships.get(0).getShip(), "C03C07")));
    ShipSetValidator validator = new ShipSetValidator(new ShipSet(ships));

    // Act & Assert
    validator.AssertAreValid();
  }

  private List<ValidShipPosition> GetDefaultShips()
  {
    return Lists.newArrayList(
      new ValidShipPosition(new ShipPosition(EShipType.aircraftCarrier, "A01A05")),
      new ValidShipPosition(new ShipPosition(EShipType.battleship, "C01C04")),
      new ValidShipPosition(new ShipPosition(EShipType.destroyer, "E01E03")),
      new ValidShipPosition(new ShipPosition(EShipType.submarine, "G01G03")),
      new ValidShipPosition(new ShipPosition(EShipType.patrolBoat, "I01I02"))
    );
  }

  private ValidShipPosition ChangePosition(ValidShipPosition originalPosition, String newPosition)
  {
    return new ValidShipPosition(new ShipPosition(originalPosition.getShip(), "C03C07"));
  }

  private ValidShipPosition ChangeShipType(ValidShipPosition originalPosition, EShipType newShipType)
  {
    List<BoardSquare> squares = originalPosition.getSquares();
    return new ValidShipPosition(
            new ShipPosition(newShipType, squares.get(0).toString() + squares.get(squares.size() - 1).toString()));
  }
}
