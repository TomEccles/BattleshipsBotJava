package BattleshipsExamplePlayer.Firing.Scorers;

import BattleshipsExamplePlayer.Firing.OpponentsBoard;
import BattleshipsExamplePlayer.Board.Square;
import BattleshipsExamplePlayer.Board.SquareState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TEE on 11/09/2016.
 */
public class LonelyScorer implements IScorer {
    @Override
    public double score(OpponentsBoard board, Square square) {
        List<Square> neighbours = OpponentsBoard.neighbours(square);
        List<Square> twoNeighbours = new ArrayList<>();
        for (Square sq : neighbours) {
            twoNeighbours.addAll(OpponentsBoard.neighbours(sq));
        }
        int score = 0;
        for (Square sq: neighbours) {
            if (board.state(sq) == SquareState.unknown) score += 10;
        }
        for (Square sq: twoNeighbours) {
            if (board.state(sq) == SquareState.unknown) score += 1;
        }
        return score;
    }

}
