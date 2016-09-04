package BattleshipsAdjudicator;

import java.io.IOException;
import java.io.Writer;

public interface IGameLogProvider
{
  Writer NextLog() throws IOException;
}
