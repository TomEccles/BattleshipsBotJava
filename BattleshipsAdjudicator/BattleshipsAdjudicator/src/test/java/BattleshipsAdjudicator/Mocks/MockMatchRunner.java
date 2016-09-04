package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.IBattleshipsPlayerWrapper;
import BattleshipsAdjudicator.IMatchResult;
import BattleshipsAdjudicator.IMatchRunner;

public class MockMatchRunner implements IMatchRunner
{
  private IBattleshipsPlayerWrapper Winner;
  public final IBattleshipsPlayerWrapper getWinner()
  {
    return Winner;
  }
  private void setWinner(IBattleshipsPlayerWrapper value)
  {
    Winner = value;
  }
  private IBattleshipsPlayerWrapper Loser;
  public final IBattleshipsPlayerWrapper getLoser()
  {
    return Loser;
  }
  private void setLoser(IBattleshipsPlayerWrapper value)
  {
    Loser = value;
  }
  private int NumberOfRuns;
  public final int getNumberOfRuns()
  {
    return NumberOfRuns;
  }
  private void setNumberOfRuns(int value)
  {
    NumberOfRuns = value;
  }

  public MockMatchRunner(IBattleshipsPlayerWrapper winner, IBattleshipsPlayerWrapper loser)
  {
    setWinner(winner);
    setLoser(loser);
    setNumberOfRuns(0);
  }

  public IMatchResult RunMatch()
  {
    setNumberOfRuns(getNumberOfRuns() + 1);

    return new MockMatchDetails(getWinner(), null, null, 5, 1, 1);
  }
}

