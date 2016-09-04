package BattleshipsAdjudicator;

import BattleshipsAdjudicator.EShotResult;
import BattleshipsAdjudicator.Mocks.ShipPosition;
import BattleshipsAdjudicator.ShipSet;
import BattleshipsAdjudicator.ValidShipPosition;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ShipSetTests {
  @Test
  public final void TestShipHit()
  {
    // Arrange
    ShipSet set = new ShipSet(GetShipPositions());

    // Act
    EShotResult hit = set.ShotFired('A', 1);

    // Asset
    assertThat(hit).isEqualByComparingTo(EShotResult.Hit);
  }

  @Test
  public final void TestShipHitAndSunk()
  {
    // Arrange
    ShipSet set = new ShipSet(GetShipPositions());

    // Act
    set.ShotFired('A', 1);
    EShotResult hit = set.ShotFired('B', 1);

    // Asset
    assertThat(hit).isEqualByComparingTo(EShotResult.HitAndSunk);
  }

  @Test
  public final void TestShipHitSunkAndHitAgain()
  {
    // Arrange
    ShipSet set = new ShipSet(GetShipPositions());

    // Act
    set.ShotFired('B', 1);
    set.ShotFired('A', 1);
    EShotResult hit = set.ShotFired('B', 1);

    // Asset
    assertThat(hit).isEqualByComparingTo(EShotResult.Hit);
  }

  public final void TestShipMissed()
  {
    // Arrange
    ShipSet set = new ShipSet(GetShipPositions());

    // Act
    EShotResult hit = set.ShotFired('J', 10);

    // Asset
    assertThat(hit).isEqualByComparingTo(EShotResult.Miss);
  }

  @Test
  public final void TestMethodAllShipsSunk()
  {
    // Arrange
    ShipSet set = new ShipSet(GetShipPositions());

    // Act
    set.ShotFired('A', 1);
    set.ShotFired('B', 1);
    set.ShotFired('A', 3);
    set.ShotFired('B', 3);

    // Assert
    assertThat(set.getAllSunk()).isTrue();
  }

  private List<ValidShipPosition> GetShipPositions()
  {
    return Lists.newArrayList(
      new ValidShipPosition(new ShipPosition(BattleshipsInterface.EShipType.patrolBoat, "A01B01")),
      new ValidShipPosition(new ShipPosition(BattleshipsInterface.EShipType.patrolBoat, "A03B03"))
    );
  }
}
