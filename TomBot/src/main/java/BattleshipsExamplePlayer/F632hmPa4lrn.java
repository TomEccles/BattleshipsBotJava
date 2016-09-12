package BattleshipsExamplePlayer;

import BattleshipsExamplePlayer.Firing.*;
import BattleshipsExamplePlayer.Placing.AntiFourPlacer;
import BattleshipsExamplePlayer.Placing.FriendlyShipPosition;
import BattleshipsInterface.IBattleshipsPlayer;
import BattleshipsInterface.ICoordinate;
import BattleshipsInterface.IShipPosition;
import javafx.util.Pair;

import java.util.*;

public class F632hmPa4lrn implements IBattleshipsPlayer {
  private OpponentsBoard board;
  private Square lastShot;
  private List<IFiringStrategy> strategies;
  private List<FriendlyShipPosition> lastPlacing = null;
  private List<Pair<List<FriendlyShipPosition>, Boolean>> won = new ArrayList<>();
  private Heatmap heatmap = new Heatmap();
  private int hits = 0;
  private int gameIndex = 0;
  private Logger logger = new Logger();

  @Override
  public String getName() {
    return "63o2-a4lrn";
  }

  @Override
  public Iterable<IShipPosition> GetShipPositions() {
    gameIndex++;
    logger.close();
    //if (gameIndex % 10 == 0) logger.openNext(getName());
    this.board = new OpponentsBoard();

    if(lastPlacing != null) {
      won.add(new Pair<>(lastPlacing, hits == 17));
    }
    hits = 0;

    strategies = new ArrayList<>();
    strategies.add(new SinkingStrategy());
    strategies.add(new SixThreeTwoStripingStrategy(new HottestSquareScorer(heatmap, 4)));

    PlacementAnalyser analyser = new PlacementAnalyser(won, logger);
    int attempts = 100;

    AntiFourPlacer placer = new AntiFourPlacer(analyser, attempts, logger);
    lastPlacing = placer.getFriendlyPlacing();
    return placer.getPlacing();
  }

  @Override
  public ICoordinate SelectTarget() {
    Square sq = getTarget();

    this.lastShot = sq;

    return coordinate(sq);
  }

  private Coordinate coordinate(Square sq) {
    return new Coordinate((char)('A' + (sq.x)), (sq.y) + 1);
  }

  private Square getTarget() {
    for(IFiringStrategy strategy : strategies) {
      Optional<Square> attempt = strategy.selectTarget(board);
      if (attempt.isPresent()) return attempt.get();
    }
    return null;
  }

  @Override
  public void ShotResult(boolean b) {
    if (b) {
      hits++;
      board.processHit(this.lastShot);
      heatmap.registerHit(this.lastShot);
      List<Square> sinkingShip = board.getContiguousHits(this.lastShot);
      boolean sunk = isSunk(board, sinkingShip);
      int size = sinkingShip.size();
      if (sunk) {
        board.addMissesAllAround(sinkingShip);
        board.sunk(size);
        logger.log("sunk a ship of size " + size);
      }
      else if (size >= 2) {
        if (sinkingShip.get(0).isSameRowAs(sinkingShip.get(1))) {
          board.addMissesAboveAndBelowDiag(sinkingShip);
        } else {
          board.addMissesLeftAndRightDiag(sinkingShip);
        }
      }

      if (hits == 17) {
        // We win!
        for (Square sq : board.getEmpties()) {
          heatmap.registerMiss(sq);
        }
      }
    }
    else {
      Square square = this.lastShot;
      heatmap.registerMiss(square);
      board.addMissIfNotHit(square);
      for (Square neighbour : OpponentsBoard.neighbours(square)) {
        if (board.state(neighbour) == SquareState.hit) {
          List<Square> sinkingShip = board.getContiguousHits(neighbour);
          boolean sunk = isSunk(board, sinkingShip);
          int size = sinkingShip.size();
          if (sunk) {
            board.sunk(size);
            board.addMissesAllAround(sinkingShip);
            logger.log("sunk a ship of size " + size);
          }
        }
      }

    }
  }

  private boolean isSunk(OpponentsBoard board, List<Square> sinkingShip) {
    //logger("Processing hit: " + lastShot.toString());
    if (sinkingShip.size() < 2) return false;
    if (sinkingShip.size() == board.maxRemaining()) return true;

    Square first = sinkingShip.get(0);
    Square last = sinkingShip.get(sinkingShip.size() - 1);

    boolean horizontal = first.y == last.y;
   // logger(horizontal ? "Horizontal" : "Vertical");


    if (horizontal) {
      boolean leftOk = !first.hasLeft() || board.state(first.left()) == SquareState.miss;
      boolean rightOk = !last.hasRight() || board.state(last.right()) == SquareState.miss;
      //logger(leftOk ? "Left is done" : "Could still be left");
      //logger(rightOk ? "Right is done" : "Could still be right");
      return leftOk && rightOk;
    } else {
      boolean downOk = !first.hasDown() || board.state(first.down()) == SquareState.miss;
      boolean upOk = !last.hasUp() || board.state(last.up()) == SquareState.miss;
      //logger(downOk ? "Down is done" : "Could still be down");
      //logger(upOk ? "Up is done" : "Could still be up");
      return downOk && upOk;
    }
  }

  @Override
  public void OpponentsShot(char c, int i) {
    // Don't care what our opponent does.
  }

}
