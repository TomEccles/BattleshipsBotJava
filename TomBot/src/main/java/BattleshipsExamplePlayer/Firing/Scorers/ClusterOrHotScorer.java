package BattleshipsExamplePlayer.Firing.Scorers;

import BattleshipsExamplePlayer.Board.Square;
import BattleshipsExamplePlayer.Board.SquareState;
import BattleshipsExamplePlayer.Firing.Heatmap;
import BattleshipsExamplePlayer.Firing.OpponentsBoard;

/**
 * Created by TEE on 11/09/2016.
 */
public class ClusterOrHotScorer implements IScorer {
    private final Heatmap heatmap;
    private final int hitBias;
    private final int totalBias;

    public ClusterOrHotScorer(Heatmap heatmap, int hitBias, int totalBias) {
        this.heatmap = heatmap;
        this.hitBias = hitBias;
        this.totalBias = totalBias;
    }

    @Override
    public double score(OpponentsBoard board, Square sq) {
        boolean twoAway = hasHitTwoAway(board, sq);
        if (twoAway) return twoAwayScore(board, sq);
        else return heatmapScore(board, sq);
    }

    private double twoAwayScore(OpponentsBoard board, Square sq) {
        double prior = heatmap.proportionOfTwoAwayHits(hitBias, totalBias);
        int left = board.numberFitting(sq);

        OpponentsBoard board2 = new OpponentsBoard();
        if (sq.hasDown(2) && board.state(sq.down(2)) == SquareState.hit) {
            board2.addMissIfNotHit(sq.down());
        }
        if (sq.hasUp(2) && board.state(sq.up(2)) == SquareState.hit) {
            board2.addMissIfNotHit(sq.up());
        }
        if (sq.hasLeft(2) && board.state(sq.left(2)) == SquareState.hit) {
            board2.addMissIfNotHit(sq.left());
        }
        if (sq.hasRight(2) && board.state(sq.right(2)) == SquareState.hit) {
            board2.addMissIfNotHit(sq.right());
        }
        int possible = board2.numberFitting(sq);

        double probWithHit = prior * ((double)left)/possible;
        double probWithMiss = (1-prior);
        return probWithHit / (probWithHit + probWithMiss);
    }

    private double heatmapScore(OpponentsBoard board, Square sq) {
        double prior = heatmap.proportionOfHits(sq, hitBias,totalBias);
        int possible = new OpponentsBoard().numberFitting(sq);
        int left = board.numberFitting(sq);
        double probWithHit = prior * ((double)left)/possible;
        double probWithMiss = (1-prior);
        return probWithHit / (probWithHit + probWithMiss);
    }

    private boolean hasHitTwoAway(OpponentsBoard board, Square sq) {
        return (sq.hasDown(2) && board.state(sq.down(2)) == SquareState.hit) ||
                (sq.hasUp(2) && board.state(sq.up(2)) == SquareState.hit) ||
                (sq.hasRight(2) && board.state(sq.right(2)) == SquareState.hit) ||
                (sq.hasLeft(2) && board.state(sq.left(2)) == SquareState.hit);
    }

}
