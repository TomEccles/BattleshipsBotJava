package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Board.Square;
import BattleshipsExamplePlayer.Board.SquareState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by TEE on 10/09/2016.
 */
public class SinkingStrategy implements IFiringStrategy {
    @Override
    public Optional<Square> selectTarget(OpponentsBoard board) {
        List<Square> squares = findPossibleHits(board);
        if(squares.isEmpty()) return Optional.empty();
        return Optional.of(squares.get(0));
    }

    // This is pretty dumb. Particularly, it's up to you to mark impossible squares as miss before you call this.
    private List<Square> findPossibleHits(OpponentsBoard board) {
        List<Square> possibilities = new ArrayList<>();
        for(int i = 0; i < OpponentsBoard.side; i++)
            for(int j = 0; j < OpponentsBoard.side; j++) {
            {
                Square sq = new Square(i, j);
                if (board.state(sq) != SquareState.hit) continue;
                possibilities.addAll(OpponentsBoard.neighbours(sq)
                        .stream()
                        .filter(square -> board.state(square) == SquareState.unknown)
                        .collect(Collectors.toList()));
            }
        }
        return possibilities;
    }
}
