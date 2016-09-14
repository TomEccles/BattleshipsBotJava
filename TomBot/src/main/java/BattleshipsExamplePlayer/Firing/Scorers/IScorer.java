package BattleshipsExamplePlayer.Firing.Scorers;

import BattleshipsExamplePlayer.Firing.OpponentsBoard;
import BattleshipsExamplePlayer.Board.Square;

/**
 * Created by TEE on 11/09/2016.
 */
public interface IScorer {
    double score(OpponentsBoard board, Square square);
}
