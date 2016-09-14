package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Board.Square;
import BattleshipsExamplePlayer.Board.SquareState;

import java.util.Optional;

/**
 * Created by TEE on 10/09/2016.
 */
public class RandomStrategy implements IFiringStrategy {


    @Override
    public Optional<Square> selectTarget(OpponentsBoard board) {
        Square sq;
        do
        {
            sq = Square.randomSquare();
        } while (board.state(sq) != SquareState.unknown);
        return Optional.of(sq);
    }

}
