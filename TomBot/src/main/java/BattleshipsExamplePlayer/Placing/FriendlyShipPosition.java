package BattleshipsExamplePlayer.Placing;

import BattleshipsExamplePlayer.Square;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by TEE on 10/09/2016.
 */
public class FriendlyShipPosition {
    public int xStart;
    public int xEnd;
    public int yStart;
    public int yEnd;

    public FriendlyShipPosition(int xStart, int xEnd, int yStart, int yEnd) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }

    public boolean outOfBounds() {
        return xStart < 0 || xEnd > 9 || yStart < 0 || yEnd > 9;
    }

    public boolean bordersAny(List<FriendlyShipPosition> otherShips) {
        for(FriendlyShipPosition ship : otherShips) {
            if(borders(ship)) return true;
        }
        return false;
    }

    private boolean borders(FriendlyShipPosition other) {
        return
                other.xEnd >= this.xStart - 1 &&
                this.xEnd >= other.xStart - 1 &&
                other.yEnd >= this.yStart - 1 &&
                this.yEnd >= other.yStart - 1;
    }

    public Collection<? extends Square> squares() {
        List<Square> squares = new ArrayList<>();
        for (int i = xStart ; i <= xEnd; i++) {
            for (int j = yStart ; j <= yEnd; j++) {
                squares.add(new Square(i, j));
            }
        }
        return squares;
    }
}
