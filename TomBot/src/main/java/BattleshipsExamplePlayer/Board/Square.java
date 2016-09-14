package BattleshipsExamplePlayer.Board;

import BattleshipsExamplePlayer.Firing.OpponentsBoard;

import java.util.Random;

/**
 * Created by TEE on 10/09/2016.
 */
public class Square {
    public int x;
    public int y;
    private static Random random = new Random();

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Square randomSquare() {
        int r = random.nextInt(100);
        return new Square(r/10, r%10);
    }

    public boolean hasLeft() {
        return x != 0;
    }

    public boolean hasLeft(int i) {
        return x >= i;
    }
    public Square left() {
        return new Square(x -1, y);
    }
    public Square left(int i) {
        return new Square(x - i, y);
    }

    public boolean hasRight() {
        return hasRight(1);
    }
    public boolean hasRight(int i) {
        return x < OpponentsBoard.side - i;
    }
    public Square right() {
        return new Square(x+1, y);
    }
    public Square right(int i) {
        return new Square(x + i, y);
    }

    public boolean hasDown() {
        return y != 0;
    }
    public boolean hasDown(int i) {
        return y >= i;
    }
    public Square down() {
        return new Square(x, y - 1);
    }
    public Square down(int i) {
        return new Square(x, y - i);
    }

    public boolean hasUp() {
        return y != OpponentsBoard.side - 1;
    }
    public boolean hasUp(int i) {
        return y < OpponentsBoard.side - i;
    }
    public Square up() {
        return new Square(x, y + 1);
    }
    public Square up(int i) {
        return new Square(x, y + i);
    }

    public boolean isSameRowAs(Square square) {
        return y == square.y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public int distanceFrom(Square sq2) {
        return Math.abs(x - sq2.x) + Math.abs(y - sq2.y);
    }
}
