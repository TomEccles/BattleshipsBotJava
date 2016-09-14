package BattleshipsExamplePlayer.Placing;

import BattleshipsExamplePlayer.Logger;
import BattleshipsExamplePlayer.Board.Square;
import BattleshipsInterface.EShipType;
import BattleshipsInterface.IShipPosition;

import java.util.*;

/**
 * Created by TEE on 10/09/2016.
 */
public class AntiFourPlacer {
    private static Random r = new Random();
    private final int attempts;
    private boolean calculated = false;
    private final Logger logger;

    List<FriendlyShipPosition> friendlyPositions = new ArrayList<>();
    List<IShipPosition> positions = new ArrayList<>();
    private final PlacementAnalyser analyser;

    public AntiFourPlacer(PlacementAnalyser analyser, int attempts, Logger logger) {
        this.analyser = analyser;
        this.attempts = attempts;
        this.logger = logger;
    }

    private void calculatePlacing(){
        List<FriendlyShipPosition> best = null;
        Double bestScore = null;
        for (int i = 0; i< attempts; i++) {
            List<FriendlyShipPosition> candidate = calculateSinglePlacing();
            Double score = analyser.scorePosition(candidate);
            if (bestScore == null || score > bestScore) {
                bestScore = score;
                best = candidate;
            }
        }

        logger.log("Score: " + bestScore);

        friendlyPositions = best;
        positions.add(ShipPosition.fromFriendlyPosition(best.get(0), EShipType.patrolBoat));
        positions.add(ShipPosition.fromFriendlyPosition(best.get(1), EShipType.destroyer));
        positions.add(ShipPosition.fromFriendlyPosition(best.get(2), EShipType.submarine));
        positions.add(ShipPosition.fromFriendlyPosition(best.get(3), EShipType.battleship));
        positions.add(ShipPosition.fromFriendlyPosition(best.get(4), EShipType.aircraftCarrier));

        calculated = true;
    }

    private List<FriendlyShipPosition> calculateSinglePlacing() {
        List<FriendlyShipPosition> pos = new ArrayList<>();

        FriendlyShipPosition pb = getRandomShip(pos, 2);
        pos.add(pb);

        FriendlyShipPosition destroyer = getRandomShip(pos, 3);
        pos.add(destroyer);

        FriendlyShipPosition sub = getRandomShip(pos, 3);
        pos.add(sub);

        FriendlyShipPosition battleship = getRandomShip(pos, 4);
        pos.add(battleship);

        FriendlyShipPosition carrier = getRandomShip(pos, 5);
        pos.add(carrier);

        return pos;
    }

    public List<IShipPosition> getPlacing(){
        if (!calculated) calculatePlacing();
        return positions;
    }

    public List<FriendlyShipPosition> getFriendlyPlacing(){
        if (!calculated) calculatePlacing();
        return friendlyPositions;
    }

    private List<Integer> getMissedDownDiagonals(List<FriendlyShipPosition> ships) {
        List<Integer> missed = new ArrayList<>();
        for (FriendlyShipPosition ship : ships) {
            missed.addAll(getMissedDownDiagonals(ship));
        }
        return missed;
    }

    private List<Integer> getMissedDownDiagonals(FriendlyShipPosition ship) {
        List<Integer> missed = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        for (int i = ship.xStart; i <= ship.xEnd; i++) {
            for (int j = ship.yStart; j <= ship.yEnd; j++) {
                missed.remove((Integer) ((40 + i - j) % 4));
            }
        }
        return missed;
    }

    private List<Integer> getMissedUpDiagonals(List<FriendlyShipPosition> ships) {
        List<Integer> missed = new ArrayList<>();
        for (FriendlyShipPosition ship : ships) {
            missed.addAll(getMissedUpDiagonals(ship));
        }
        return missed;
    }

    private List<Integer> getMissedUpDiagonals(FriendlyShipPosition ship) {
        List<Integer> missed = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        for (int i = ship.xStart; i <= ship.xEnd; i++) {
            for (int j = ship.yStart; j <= ship.yEnd; j++) {
                missed.remove((Integer) ((i + j) % 4));
            }
        }
        return missed;
    }

    private FriendlyShipPosition getRandomShip(List<FriendlyShipPosition> shipsSoFar, int length) {
        List<Integer> unmissableUpDiags = getMissedUpDiagonals(shipsSoFar);
        List<Integer> unmissableDownDiags = getMissedDownDiagonals(shipsSoFar);

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

            List<Integer> badUpDiags = getMissedUpDiagonals(shipAttempt);
            badUpDiags.retainAll(unmissableUpDiags);
            List<Integer> badDownDiags = getMissedDownDiagonals(shipAttempt);
            badDownDiags.retainAll(unmissableDownDiags);

            if (badDownDiags.isEmpty() && badUpDiags.isEmpty() && isLegal(shipAttempt, shipsSoFar)) {
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
