package BattleshipsExamplePlayer.Placing;

import BattleshipsExamplePlayer.Board.Square;
import BattleshipsInterface.EShipType;
import BattleshipsInterface.IShipPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TEE on 10/09/2016.
 */
public class RandomPlacer {
    private static Random r = new Random();

    public List<IShipPosition> getPlacing() {
        List<FriendlyShipPosition> friendlyPositions = new ArrayList<>();
        List<IShipPosition> positions = new ArrayList<>();

        FriendlyShipPosition carrier = getNewShip(friendlyPositions, 5);
        friendlyPositions.add(carrier);
        positions.add(ShipPosition.fromFriendlyPosition(carrier, EShipType.aircraftCarrier));

        FriendlyShipPosition battleship = getNewShip(friendlyPositions, 4);
        friendlyPositions.add(battleship);
        positions.add(ShipPosition.fromFriendlyPosition(battleship, EShipType.battleship));

        FriendlyShipPosition destroyer = getNewShip(friendlyPositions, 3);
        friendlyPositions.add(destroyer);
        positions.add(ShipPosition.fromFriendlyPosition(destroyer, EShipType.destroyer));
        FriendlyShipPosition sub = getNewShip(friendlyPositions, 3);
        friendlyPositions.add(sub);
        positions.add(ShipPosition.fromFriendlyPosition(sub, EShipType.submarine));

        FriendlyShipPosition pb = getNewShip(friendlyPositions, 2);
        friendlyPositions.add(pb);
        positions.add(ShipPosition.fromFriendlyPosition(pb, EShipType.patrolBoat));

        return positions;
    }

    private FriendlyShipPosition getNewShip(List<FriendlyShipPosition> shipsSoFar, int length) {
        while(true) {
            Square startSquare = Square.randomSquare();
            boolean horizontal = r.nextBoolean();
            FriendlyShipPosition shipAttempt;
            if (horizontal) {
                 shipAttempt =
                        new FriendlyShipPosition(
                                startSquare.x,
                                startSquare.x + length - 1,
                                startSquare.y,
                                startSquare.y);
            }
            else {
                shipAttempt =
                        new FriendlyShipPosition(
                                startSquare.x,
                                startSquare.x,
                                startSquare.y,
                                startSquare.y + length - 1);
            }
            if (isLegal(shipAttempt, shipsSoFar)) {
                return shipAttempt;
            }
        }
    }

    private boolean isLegal(FriendlyShipPosition shipAttempt, List<FriendlyShipPosition> shipsSoFar) {
        if (shipAttempt.outOfBounds()) return false;
        if (shipAttempt.bordersAny(shipsSoFar)) return false;
        return true;
    }
}
