package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Board.Square;
import BattleshipsExamplePlayer.Board.SquareState;

/**
 * Created by TEE on 12/09/2016.
 */
public class Heatmap {
    private int[][] hits = new int[10][10];
    private int twoAwayHits = 0;
    private int twoAwayMisses = 0;
    private int[][] misses = new int[10][10];

    public void registerHit(Square sq) {
        hits[sq.x][sq.y]++;
    }

    public void registerMiss(Square sq) {
        misses[sq.x][sq.y]++;
    }

    private int hits(Square sq) {
        return hits[sq.x][sq.y];
    }

    private int misses(Square sq) {
        return misses[sq.x][sq.y];
    }

    public double proportionOfHits(Square sq, int hitBias, int totalBias) {
        return (double)(hits(sq)+hitBias) / (double)(misses(sq)+hits(sq)+totalBias);
    }

    public double proportionOfTwoAwayHits(int hitBias, int totalBias) {
        return (double)(twoAwayHits + hitBias) / (double)(twoAwayHits + twoAwayMisses + totalBias);
    }

    public void registerGame(OpponentsBoard board) {
        board.getSquares(SquareState.hit).forEach(this::registerHit);
        board.getSquares(SquareState.miss).forEach(this::registerMiss);
        for (Square sq : board.getSquares(SquareState.hit)) {
            if (sq.hasDown(2) && board.state(sq.down(1)) == SquareState.miss) {
                if (board.state(sq.down(2))== SquareState.hit) twoAwayHits++;
                if (board.state(sq.down(2))== SquareState.miss) twoAwayMisses++;
            }
            if (sq.hasUp(2) && board.state(sq.up(1)) == SquareState.miss) {
                if (board.state(sq.up(2))== SquareState.hit) twoAwayHits++;
                if (board.state(sq.up(2))== SquareState.miss) twoAwayMisses++;
            }
            if (sq.hasLeft(2) && board.state(sq.left(1)) == SquareState.miss) {
                if (board.state(sq.left(2))== SquareState.hit) twoAwayHits++;
                if (board.state(sq.left(2))== SquareState.miss) twoAwayMisses++;
            }
            if (sq.hasRight(2) && board.state(sq.right(1)) == SquareState.miss) {
                if (board.state(sq.right(2))== SquareState.hit) twoAwayHits++;
                if (board.state(sq.right(2))== SquareState.miss) twoAwayMisses++;
            }
        }
    }
}
