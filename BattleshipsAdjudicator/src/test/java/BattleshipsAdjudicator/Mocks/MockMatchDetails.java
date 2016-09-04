package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.IBattleshipsPlayerWrapper;
import BattleshipsAdjudicator.IMatchResult;

public class MockMatchDetails implements IMatchResult {
  public MockMatchDetails(IBattleshipsPlayerWrapper Winner, IBattleshipsPlayerWrapper Player1, IBattleshipsPlayerWrapper Player2, int WinnerWins, int LoserWins, int Draws) {
    this.Winner = Winner;
    this.Player1 = Player1;
    this.Player2 = Player2;
    this.WinnerWins = WinnerWins;
    this.LoserWins = LoserWins;
    this.Draws = Draws;
  }

  private IBattleshipsPlayerWrapper Winner;
  public final IBattleshipsPlayerWrapper getWinner()
  {
    return Winner;
  }
  public final void setWinner(IBattleshipsPlayerWrapper value)
  {
    Winner = value;
  }
  private IBattleshipsPlayerWrapper Player1;
  public final IBattleshipsPlayerWrapper getPlayer1()
  {
    return Player1;
  }
  public final void setPlayer1(IBattleshipsPlayerWrapper value)
  {
    Player1 = value;
  }
  private IBattleshipsPlayerWrapper Player2;
  public final IBattleshipsPlayerWrapper getPlayer2()
  {
    return Player2;
  }
  public final void setPlayer2(IBattleshipsPlayerWrapper value)
  {
    Player2 = value;
  }
  private int WinnerWins;
  public final int getWinnerWins()
  {
    return WinnerWins;
  }
  public final void setWinnerWins(int value)
  {
    WinnerWins = value;
  }
  private int LoserWins;
  public final int getLoserWins()
  {
    return LoserWins;
  }
  public final void setLoserWins(int value)
  {
    LoserWins = value;
  }
  private int Draws;
  public final int getDraws()
  {
    return Draws;
  }
  public final void setDraws(int value)
  {
    Draws = value;
  }
}
