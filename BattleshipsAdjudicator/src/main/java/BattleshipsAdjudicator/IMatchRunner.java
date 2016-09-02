package BattleshipsAdjudicator;

import BattleshipsInterface.*;

import java.io.IOException;

public interface IMatchRunner
{
  IMatchResult RunMatch() throws IOException;
}

