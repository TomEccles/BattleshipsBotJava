package BattleshipsAdjudicator;

import BattleshipsAdjudicator.Mocks.ShipPosition;
import BattleshipsInterface.EShipType;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

/**
 Summary description for BoardPrinterTests
 */
public class BoardPrinterTests
{

  private ShipSet _ships = new ShipSet(new ArrayList<ValidShipPosition>(Arrays.asList(new ValidShipPosition[]
  {
    new ValidShipPosition(new ShipPosition(EShipType.aircraftCarrier, "A01A05")),
    new ValidShipPosition(new ShipPosition(EShipType.battleship, "A10D10")),
    new ValidShipPosition(new ShipPosition(EShipType.destroyer, "J08J10")),
    new ValidShipPosition(new ShipPosition(EShipType.submarine, "H01J01")),
    new ValidShipPosition(new ShipPosition(EShipType.patrolBoat, "E05E06"))
  })));

  private String board =
    "   1 2 3 4 5 6 7 8 9 10" + "\r\n" +
    "  _____________________" + "\r\n" +
    "A |A|A|A|A|A|.|.|.|.|B|" + "\r\n" +
    "B |.|.|.|.|.|.|.|.|.|X|" + "\r\n" +
    "C |.|.|.|.|.|.|.|.|.|B|" + "\r\n" +
    "D |.|.|.|.|.|.|.|.|.|B|" + "\r\n" +
    "E |.|.|.|.|P|P|.|.|.|.|" + "\r\n" +
    "F |.|.|.|.|.|.|.|.|.|.|" + "\r\n" +
    "G |.|.|.|.|.|.|.|.|.|.|" + "\r\n" +
    "H |S|.|.|.|.|.|.|.|.|.|" + "\r\n" +
    "I |S|.|.|.|.|.|.|.|.|.|" + "\r\n" +
    "J |S|.|.|.|.|.|.|D|D|D|" + "\r\n" +
    "  ---------------------";

  @Test
  public final void TestBoard()
  {
    // Arrange
    _ships.ShotFired('B', 10);
    BoardPrinter printer = new BoardPrinter(_ships);

    // Assert
    assertThat(printer.getBoard()).isEqualTo(board);
  }
}
