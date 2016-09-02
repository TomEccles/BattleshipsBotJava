package BattleshipsAdjudicator;

import BattleshipsInterface.*;

import java.io.IOException;

public interface IAdjudicator
{
  IGameResult RunGame() throws IOException;
}

