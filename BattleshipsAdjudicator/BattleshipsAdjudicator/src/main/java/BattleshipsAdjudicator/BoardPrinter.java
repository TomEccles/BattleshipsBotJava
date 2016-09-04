package BattleshipsAdjudicator;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoardPrinter
{
  public BoardPrinter(ShipSet ships) {
    _ships = ships;
  }

  public final String getBoard() {
    if (_board == null) {
      _board = GenerateBoard();
    }
    return _board;
  }

  private String GenerateBoard() {
    List<List<Character>> squares = GetSquareChars();

    List<String> rowArray =
            squares.stream()
                    .map(rowList -> StringUtils.join(rowList.stream()
                            .map(c -> c.toString())
                            .collect(Collectors.toList()), "|"))
                    .collect(Collectors.toList());

    StringBuilder ret = new StringBuilder();
    ret.append("   1 2 3 4 5 6 7 8 9 10" + "\r\n");
    ret.append("  _____________________" + "\r\n");

    char currentRow = 'A';
    for (String row : rowArray) {
      ret.append(currentRow);
      ret.append(" |");
      ret.append(row);
      ret.append("|" + "\r\n");
      currentRow++;
    }
    ret.append("  ---------------------");

    return ret.toString();
  }

  private List<List<Character>> GetSquareChars() {
    List<List<Character>> squares = new ArrayList<>();

    for (char row = 'A'; row <= 'J'; row++) {
      List<Character> rowChars = new ArrayList<>();

      for (int column = 1; column <= 10; column++) {
        rowChars.add(column - 1, GetSquareChar(row, column));
      }
      squares.add(row - 'A', rowChars);
    }
    return squares;
  }

  private char GetSquareChar(char row, int column) {
    ValidShipPosition shipInSquare = _ships
            .getStream()
            .filter(ship -> ship.getShipSquare(row, column).isPresent())
            .findFirst()
            .orElse(null);

    if (shipInSquare == null) {
      return '.';
    } else if (shipInSquare.getShipSquare(row, column).get().getIsHit()) {
      return 'X';
    }

    switch (shipInSquare.getShip()) {
      case aircraftCarrier:
        return 'A';
      case battleship:
        return 'B';
      case destroyer:
        return 'D';
      case submarine:
        return 'S';
      case patrolBoat:
        return 'P';
      default:
        throw new IllegalStateException("Unrecognised ship type: " + shipInSquare.getShip());
    }
  }

  private String _board;
  private ShipSet _ships;
}
