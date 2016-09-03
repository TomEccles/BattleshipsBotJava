package BattleshipsPracticeDummy;

import java.util.*;

import BattleshipsInterface.*;
import com.google.common.collect.Lists;

public class PracticeDummy implements IBattleshipsPlayer
{
  public final String getName()
  {
    return "PracticeDummy";
  }

  public final void ShotResult(boolean hit)
  {
  }

  private class Coordinate implements ICoordinate {

    @Override
    public char getRow() {
      return 'A';
    }

    @Override
    public int getColumn() {
      return 1;
    }
  }

  public final ICoordinate SelectTarget()
  {
    return new Coordinate();
  }

  public final Iterable<IShipPosition> GetShipPositions()
  {
    ShipPosition tempVar = new ShipPosition();
    tempVar.setShip(EShipType.aircraftCarrier);
    tempVar.setStartingRow('A');
    tempVar.setStartingColumn(1);
    tempVar.setEndingRow('A');
    tempVar.setEndingColumn(5);
    ShipPosition tempVar2 = new ShipPosition();
    tempVar2.setShip(EShipType.battleship);
    tempVar2.setStartingRow('C');
    tempVar2.setStartingColumn(1);
    tempVar2.setEndingRow('C');
    tempVar2.setEndingColumn(4);
    ShipPosition tempVar3 = new ShipPosition();
    tempVar3.setShip(EShipType.destroyer);
    tempVar3.setStartingRow('E');
    tempVar3.setStartingColumn(1);
    tempVar3.setEndingRow('E');
    tempVar3.setEndingColumn(3);
    ShipPosition tempVar4 = new ShipPosition();
    tempVar4.setShip(EShipType.submarine);
    tempVar4.setStartingRow('G');
    tempVar4.setStartingColumn(1);
    tempVar4.setEndingRow('G');
    tempVar4.setEndingColumn(3);
    ShipPosition tempVar5 = new ShipPosition();
    tempVar5.setShip(EShipType.patrolBoat);
    tempVar5.setStartingRow('I');
    tempVar5.setStartingColumn(1);
    tempVar5.setEndingRow('I');
    tempVar5.setEndingColumn(2);
    return Lists.newArrayList(tempVar, tempVar2, tempVar3, tempVar4, tempVar5);
  }

  public final void OpponentsShot(char row, int column)
  {
  }
}
