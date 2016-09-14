package BattleshipsExamplePlayer.Firing.Scorers;

import BattleshipsExamplePlayer.Firing.OpponentsBoard;
import BattleshipsExamplePlayer.Firing.Heatmap;
import BattleshipsExamplePlayer.Board.Square;

/**
 * Created by TEE on 11/09/2016.
 */
public class HottestSquareScorer implements IScorer {
    private final Heatmap heatmap;
    private final int hitBias;
    private final int totalBias;

    public HottestSquareScorer(Heatmap heatmap, int hitBias, int totalBias) {
        this.heatmap = heatmap;
        this.hitBias = hitBias;
        this.totalBias = totalBias;
    }

    @Override
    public double score(OpponentsBoard board, Square sq) {
        double prior = heatmap.proportionOfHits(sq, hitBias, totalBias);
        int possible = new OpponentsBoard().numberFitting(sq);
        int left = board.numberFitting(sq);
        //Not quite right yet!
        double probWithHit = prior * ((double)left)/possible;
        double probWithMiss = (1-prior);
        return probWithHit / (probWithHit + probWithMiss);
    }

}
