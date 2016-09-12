package BattleshipsExamplePlayer;

/**
 * Created by TEE on 12/09/2016.
 */
public class Heatmap {
    private int[][] hits = new int[10][10];
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

    public double proportionOfHits(Square sq, int equalityBias) {
        return (double)(hits(sq)+equalityBias) / (double)(misses(sq)+hits(sq)+equalityBias);
    }
}
