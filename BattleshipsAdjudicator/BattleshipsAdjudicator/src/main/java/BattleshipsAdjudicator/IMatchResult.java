package BattleshipsAdjudicator;

public interface IMatchResult {
  IBattleshipsPlayerWrapper getWinner();
  IBattleshipsPlayerWrapper getPlayer1();
  IBattleshipsPlayerWrapper getPlayer2();
  int getWinnerWins();
  int getLoserWins();
  int getDraws();
}
