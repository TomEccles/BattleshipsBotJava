package BattleshipsExamplePlayer;

import BattleshipsExamplePlayer.Firing.OpponentsBoard;
import BattleshipsExamplePlayer.Placing.FriendlyShipPosition;
import javafx.util.Pair;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by TEE on 11/09/2016.
 */
public class PlacementAnalyser {
    private int buckets = 5;
    private BucketPredictor clusterPredictor;
    private BucketPredictor edgePredictor;

    public PlacementAnalyser(List<Pair<List<FriendlyShipPosition>, Boolean>> won, Logger log) {
        List<Pair<Integer, Integer>> clusterScores =
                    won
                        .stream()
                        .map(pair -> new Pair<>(clusterScore(pair.getKey()), pair.getValue() ? 1 : 0))
                        .collect(Collectors.toList());
        clusterPredictor = new BucketPredictor(clusterScores, buckets, log);

        List<Pair<Integer, Integer>> edgeScores =
                    won
                        .stream()
                        .map(pair -> new Pair<>(edgeScore(pair.getKey()), pair.getValue() ? 1 : 0))
                        .collect(Collectors.toList());
        edgePredictor = new BucketPredictor(edgeScores, buckets, log);
    }

    private int edgeScore(List<FriendlyShipPosition> fleet) {
        int score = 0;
        for (FriendlyShipPosition ship : fleet) {
            score += edgeScore(ship);
        }
        return score;
    }

    private int edgeScore(FriendlyShipPosition ship) {
        return edginess(ship.xEnd) + edginess(ship.xStart) + edginess(ship.yEnd) + edginess(ship.yStart);
    }

    private int edginess(int coordinate) {
        return (Math.max(coordinate, OpponentsBoard.side - 1 - coordinate));
    }

    private int clusterScore(List<FriendlyShipPosition> fleet) {
        List<Square> squares = new ArrayList<>();
        for (FriendlyShipPosition ship : fleet) {
            squares.addAll(ship.squares());
        }

        int score = 0;
        for (Square sq1 : squares) {
            for (Square sq2 : squares) {
                score -= sq1.distanceFrom(sq2);
            }
        }
        return score;
    }

    public double scorePosition(List<FriendlyShipPosition> fleet){
        return edgePredictor.getScore(edgeScore(fleet)) + clusterPredictor.getScore(clusterScore(fleet));
    }
}
