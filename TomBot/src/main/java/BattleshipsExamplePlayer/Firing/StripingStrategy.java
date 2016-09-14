package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Board.SquareState;
import BattleshipsExamplePlayer.Firing.Scorers.IScorer;
import BattleshipsExamplePlayer.Board.Square;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by TEE on 10/09/2016.
 */
public class StripingStrategy implements IFiringStrategy {
    private int best;
    private int modulus;
    private IScorer scorer;

    public StripingStrategy(int modulus) {
        this.modulus = modulus;
        best = new Random(System.currentTimeMillis()).nextInt() % modulus;
    }

    public StripingStrategy(int modulus, int best, IScorer scorer) {
        this.modulus = modulus;
        this.best = best;
        this.scorer = scorer;
    }

    @Override
    public Optional<Square> selectTarget(OpponentsBoard board) {
        List<Square> empties = validSquares(board);

        if (empties.isEmpty()) return Optional.empty();
        if (scorer != null) {
            Collections.sort(empties, (sq1, sq2) -> compare(sq1, sq2, board));
        }
        Collections.reverse(empties);
        return Optional.of(empties.get(0));
    }

    private int compare(Square square1, Square square2, OpponentsBoard board) {
        double score1 = score(square1, board);
        double score2 = score(square2, board);
        return score1 < score2 ? -1 : (score1 > score2 ? 1 : 0);
    }

    private double score(Square square, OpponentsBoard board) {
        return scorer.score(board, square);
    }

    private List<Square> validSquares(OpponentsBoard board) {
        List<Square> empties =
                board.getSquares(SquareState.unknown)
                    .stream()
                    .filter(this::good)
                    .collect(Collectors.toList());
        Collections.shuffle(empties);
        return empties;
    }

    private boolean good (Square sq){
        return ((sq.x + sq.y + best) % modulus) == 0;
    }

    public int remaining(OpponentsBoard board) {
        return validSquares(board).size();
    }
}
