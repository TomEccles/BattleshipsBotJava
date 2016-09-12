package BattleshipsExamplePlayer.Firing;

import BattleshipsExamplePlayer.Square;
import BattleshipsExamplePlayer.SquareState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Process hits, and calculates which squares could still have ships in them
 */
public class OpponentsBoard {
    public static int side = 10;
    public SquareState[][] board;
    private List<Integer> remainingShips;

    public OpponentsBoard() {
        board = new SquareState[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                board[i][j] = SquareState.unknown;
            }
        }
        remainingShips = new ArrayList<>();
        remainingShips.add(5);
        remainingShips.add(4);
        remainingShips.add(3);
        remainingShips.add(3);
        remainingShips.add(2);
    }

    public SquareState state(Square sq) {
        return board[sq.x][sq.y];
    }

    public void addMissIfNotHit(Square s) {
        if(state(s) != SquareState.hit) board[s.x][s.y] = SquareState.miss;
    }

    public void processHit(Square s) {
        board[s.x][s.y] = SquareState.hit;
    }

    public static List<Square> neighbours(Square square) {
        return neighbours(square.x, square.y);
    }

    private static List<Square> neighbours(int i, int j) {
        List<Square> neighbours = new ArrayList<>();
        if (i != 0) neighbours.add(new Square(i - 1, j));
        if (i != side - 1) neighbours.add(new Square(i + 1, j));
        if (j != 0) neighbours.add(new Square(i, j - 1));
        if (j != side - 1) neighbours.add(new Square(i, j + 1));
        return neighbours;
    }

    public List<Square> getContiguousHits(Square sq) {
        boolean horizontal = false;
        if (sq.hasLeft() && (state(sq.left()) == SquareState.hit)) horizontal = true;
        if (sq.hasRight() && (state(sq.right()) == SquareState.hit)) horizontal = true;

        List<Square> answer = new ArrayList<>();
        if (horizontal) {
            Square current = sq;
            while (current.hasLeft() && state(current.left()) == SquareState.hit) {
                current = current.left();
            }

            answer.add(current);
            while (current.hasRight() && state(current.right()) == SquareState.hit) {
                current = current.right();
                answer.add(current);
            }
        } else {
            Square current = sq;
            while (current.hasDown() && state(current.down()) == SquareState.hit) {
                current = current.down();
            }

            answer.add(current);
            while (current.hasUp() && state(current.up()) == SquareState.hit) {
                current = current.up();
                answer.add(current);
            }
        }
        return answer;
    }

    public void addMissesAboveAndBelowDiag(List<Square> squares) {
        squares.forEach(this::addMissesAboveAndBelow);
        squares.forEach(this::addMissesDiagonally);
    }

    public void addMissesLeftAndRightDiag(List<Square> squares) {
        squares.forEach(this::addMissesLeftAndRight);
        squares.forEach(this::addMissesDiagonally);
    }

    public void addMissesAllAround(List<Square> squares) {
        squares.forEach(this::addMissesAboveAndBelow);
        squares.forEach(this::addMissesLeftAndRight);
        squares.forEach(this::addMissesDiagonally);
    }

    private void addMissesDiagonally(Square sq) {
        if (sq.hasUp()) {
            Square up = sq.up();
            if (up.hasLeft()) addMissIfNotHit(up.left());
            if (up.hasRight()) addMissIfNotHit(up.right());
        }
        if (sq.hasDown()) {
            Square down = sq.down();
            if (down.hasLeft()) addMissIfNotHit(down.left());
            if (down.hasRight()) addMissIfNotHit(down.right());
        }
    }

    private void addMissesAboveAndBelow(Square sq) {
        if (sq.hasUp()) {
            addMissIfNotHit(sq.up());
        }
        if (sq.hasDown()) {
            addMissIfNotHit(sq.down());
        }
    }

    private void addMissesLeftAndRight(Square sq) {
        if (sq.hasLeft()) {
            addMissIfNotHit(sq.left());
        }
        if (sq.hasRight()) {
            addMissIfNotHit(sq.right());
        }
    }

    public List<Square> getEmpties() {
        List<Square> empties = new ArrayList<>();
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                Square attempt = new Square(i,j);
                if(state(attempt) == SquareState.unknown) empties.add(attempt);
            }
        }
        return empties;
    }

    public void sunk(int size) {
        remainingShips.remove((Integer)size);
    }

    public int maxRemaining() {
        return Collections.max(remainingShips);
    }

    public boolean hasTwoBoat() {
        return remainingShips.contains(2);
    }

    public int numberFitting(Square square) {
        int fits = 0;
        for (int length : remainingShips) {
            for (int displacement = 0; displacement < length; displacement++) {
                if (fitsHorizontally(square, length, displacement)) fits += 1;
                if (fitsVertically(square, length, displacement)) fits += 1;
            }
        }
        return fits;
    }

    private boolean fitsVertically(Square square, int length, int displacement) {
        int squaresBelow = displacement;
        int squaresAbove = length - displacement - 1;
        if(!square.hasDown(squaresBelow)) return false;
        if(!square.hasUp(squaresAbove)) return false;
        for (int i = 0; i <= squaresBelow; i++) {
            if (state(square.down(i)) != SquareState.unknown) return false;
        }
        for (int i = 1; i <= squaresAbove; i++) {
            if (state(square.up(i)) != SquareState.unknown) return false;
        }
        return true;
    }

    private boolean fitsHorizontally(Square square, int length, int displacement) {
        int squaresToLeft = displacement;
        int squaresToRight = length - displacement - 1;
        if(!square.hasLeft(squaresToLeft)) return false;
        if(!square.hasRight(squaresToRight)) return false;
        for (int i = 0; i <= squaresToLeft; i++) {
            if (state(square.left(i)) != SquareState.unknown) return false;
        }
        for (int i = 1; i <= squaresToRight; i++) {
            if (state(square.right(i)) != SquareState.unknown) return false;
        }
        return true;
    }
}
