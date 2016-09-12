package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Heatmap;
import BattleshipsExamplePlayer.Square;

/**
 * Created by TEE on 11/09/2016.
 */
public class HottestSquareScorer implements IScorer {
    private final Heatmap heatmap;
    private final int bias;

    public HottestSquareScorer(Heatmap heatmap, int bias) {
        this.heatmap = heatmap;
        this.bias = bias;
    }

    @Override
    public double score(OpponentsBoard board, Square sq) {
        double prior = heatmap.proportionOfHits(sq, bias);
        int possible = new OpponentsBoard().numberFitting(sq);
        int left = board.numberFitting(sq);
        //Not quite right yet!
        double probWithHit = prior * ((double)left)/possible;
        double probWithMiss = (1-prior);
        return probWithHit / (probWithHit + probWithMiss);
    }

}
