package BattleshipsExamplePlayer;

import BattleshipsInterface.EShipType;
import BattleshipsInterface.IBattleshipsPlayer;
import BattleshipsInterface.ICoordinate;
import BattleshipsInterface.IShipPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Very simple play that returns gixed ships each game and fires randomly.
 */
public class ExamplePlayer implements IBattleshipsPlayer {
  private boolean[] shotsFired;
  private Random random = new Random();

  @Override
  public String getName() {
    return "HMS Bulletsponge";
  }

  @Override
  public Iterable<IShipPosition> GetShipPositions() {
    this.shotsFired = new boolean[100];

    List<IShipPosition> positions = new ArrayList<>();

    positions.add(new ShipPosition(EShipType.aircraftCarrier, 'B', 1, 'B', 5));
    positions.add(new ShipPosition(EShipType.battleship, 'D', 1, 'D', 4));
    positions.add(new ShipPosition(EShipType.destroyer, 'F', 1, 'F', 3));
    positions.add(new ShipPosition(EShipType.submarine, 'H', 1, 'H', 3));
    positions.add(new ShipPosition(EShipType.patrolBoat, 'J', 1, 'J', 2));

    return positions;
  }

  @Override
  public ICoordinate SelectTarget() {
    int nextShot;

    do
    {
      nextShot = this.random.nextInt(100);
    } while (this.shotsFired[nextShot]);


    this.shotsFired[nextShot] = true;

    return new Coordinate((char)('A' + (nextShot/10)), (nextShot % 10) + 1);
  }

  @Override
  public void ShotResult(boolean b) {
    // Don't care whether we hit anything.
  }

  @Override
  public void OpponentsShot(char c, int i) {
    // Don't care what our opponent does.
  }
}
