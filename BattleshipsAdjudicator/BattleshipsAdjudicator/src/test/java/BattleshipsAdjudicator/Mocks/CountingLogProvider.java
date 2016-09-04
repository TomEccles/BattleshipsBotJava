package BattleshipsAdjudicator.Mocks;

import BattleshipsAdjudicator.IGameLogProvider;

import java.io.Writer;

public class CountingLogProvider implements IGameLogProvider
{
  private int LogsProvided;
  public final int getLogsProvided()
  {
    return LogsProvided;
  }
  private void setLogsProvided(int value)
  {
    LogsProvided = value;
  }

  public final Writer NextLog()
  {
    setLogsProvided(getLogsProvided() + 1);

    return null;
  }
}
