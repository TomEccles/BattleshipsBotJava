package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Square;
import BattleshipsExamplePlayer.SquareState;

import java.util.Optional;
import java.util.Random;

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
