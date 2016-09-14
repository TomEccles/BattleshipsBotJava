package BattleshipsExamplePlayer.Firing.Scorers;

import BattleshipsExamplePlayer.Firing.OpponentsBoard;
import BattleshipsExamplePlayer.Board.Square;

/**
 * Created by TEE on 11/09/2016.
 */
public class MostShipScorer implements IScorer {
    @Override
    public double score(OpponentsBoard board, Square square) {

        return board.numberFitting(square);
    }

}
