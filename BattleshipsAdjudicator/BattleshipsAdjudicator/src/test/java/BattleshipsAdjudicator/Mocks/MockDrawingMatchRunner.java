package BattleshipsAdjudicator.Mocks;


import BattleshipsAdjudicator.IBattleshipsPlayerWrapper;
import BattleshipsAdjudicator.IMatchResult;

public class MockDrawingMatchRunner extends MockMatchRunner
{
  public MockDrawingMatchRunner(IBattleshipsPlayerWrapper player1, IBattleshipsPlayerWrapper player2)
  {
    super(player1, player2);
  }

  @Override
  public IMatchResult RunMatch()
  {
    return new MockMatchDetails(null, getWinner(), getLoser(), 2, 2, 3);
  }
}
