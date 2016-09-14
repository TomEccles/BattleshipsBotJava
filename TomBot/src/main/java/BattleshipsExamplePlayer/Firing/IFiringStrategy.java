package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Board.Square;

import java.util.Optional;

/**
 * Created by TEE on 10/09/2016.
 */
public interface IFiringStrategy {
    Optional<Square> selectTarget(OpponentsBoard board);
}
