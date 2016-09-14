package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Firing.Scorers.IScorer;
import BattleshipsExamplePlayer.Board.Square;

import java.util.Optional;
import java.util.Random;

/**
 * Created by TEE on 10/09/2016.
 */
public class TwelveSixThreeTwoStripingStrategy implements IFiringStrategy {
    private StripingStrategy oddSixStriper;
    private StripingStrategy evenSixStriper;
    private StripingStrategy threeStriper;
    private StripingStrategy twoStriper;
    private int turns = 0;

    public TwelveSixThreeTwoStripingStrategy(IScorer scorer) {
        int best = new Random(System.currentTimeMillis()).nextInt() % 6;
        oddSixStriper = new StripingStrategy(12, best, scorer);
        evenSixStriper = new StripingStrategy(12, best + 6, scorer);
        threeStriper = new StripingStrategy(3, best, scorer);
        twoStriper = new StripingStrategy(2, best, scorer);
    }

    @Override
    public Optional<Square> selectTarget(OpponentsBoard board) {
        turns++;
        StripingStrategy firstStrat = (turns % 2 == 1) ? oddSixStriper : evenSixStriper;
        StripingStrategy secondStrat = (turns % 2 != 1) ? oddSixStriper : evenSixStriper;
        Optional<Square> attempt = firstStrat.selectTarget(board);
        if(attempt.isPresent()) return attempt;

        attempt = secondStrat.selectTarget(board);
        if(attempt.isPresent()) return attempt;

        if(!board.hasTwoBoat())
        {
            attempt = twoStriper.selectTarget(board);
            return attempt;
        }

        StripingStrategy nearerCompletion =
                twoStriper.remaining(board) < threeStriper.remaining(board) ? twoStriper : threeStriper;

        return nearerCompletion.selectTarget(board);
    }
}
