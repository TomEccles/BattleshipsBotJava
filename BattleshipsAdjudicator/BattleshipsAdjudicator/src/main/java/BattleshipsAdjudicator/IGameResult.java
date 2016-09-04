package BattleshipsAdjudicator;

public interface IGameResult
{
  IBattleshipsPlayerWrapper getWinner();
  GameResultType getResultType();
}
