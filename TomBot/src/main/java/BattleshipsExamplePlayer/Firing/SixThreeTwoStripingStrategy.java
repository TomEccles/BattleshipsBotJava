package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Firing.Scorers.IScorer;
import BattleshipsExamplePlayer.Board.Square;

import java.util.Optional;
import java.util.Random;

/**
 * Created by TEE on 10/09/2016.
 */
public class SixThreeTwoStripingStrategy implements IFiringStrategy {
    private StripingStrategy sixStriper;
    private StripingStrategy threeStriper;
    private StripingStrategy twoStriper;

    public SixThreeTwoStripingStrategy(IScorer scorer) {
        int best = new Random(System.currentTimeMillis()).nextInt() % 6;
        sixStriper = new StripingStrategy(6, best, scorer);
        threeStriper = new StripingStrategy(3, best, scorer);
        twoStriper = new StripingStrategy(2, best, scorer);
    }

    @Override
    public Optional<Square> selectTarget(OpponentsBoard board) {
        Optional<Square> attempt = sixStriper.selectTarget(board);
        if(attempt.isPresent()) return attempt;

        if(board.hasTwoBoat())
        {
            attempt = twoStriper.selectTarget(board);
            return attempt;
        }

        StripingStrategy nearerCompletion =
                twoStriper.remaining(board) < threeStriper.remaining(board) ? twoStriper : threeStriper;

        return nearerCompletion.selectTarget(board);
    }
}
