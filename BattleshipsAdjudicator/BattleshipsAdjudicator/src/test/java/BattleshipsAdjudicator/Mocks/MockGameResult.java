package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.GameResultType;
import BattleshipsAdjudicator.IBattleshipsPlayerWrapper;
import BattleshipsAdjudicator.IGameResult;
import BattleshipsInterface.IBattleshipsPlayer;

public class MockGameResult implements IGameResult {
  public MockGameResult(IBattleshipsPlayerWrapper Winner, GameResultType ResultType) {
    this.Winner = Winner;
    this.ResultType = ResultType;
  }

  private IBattleshipsPlayerWrapper Winner;
  public final IBattleshipsPlayerWrapper getWinner() {
    return Winner;
  }
  public final void setWinner(IBattleshipsPlayerWrapper value) {
    Winner = value;
  }

  private GameResultType ResultType;
  public final GameResultType getResultType() {
    return ResultType;
  }
  public final void setResultType(GameResultType value) {
    ResultType = value;
  }
}
