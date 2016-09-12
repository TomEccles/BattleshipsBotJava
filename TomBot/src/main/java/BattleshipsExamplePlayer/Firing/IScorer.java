package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Square;

/**
 * Created by TEE on 11/09/2016.
 */
public interface IScorer {
    double score(OpponentsBoard board, Square square);
}
