package BattleshipsAdjudicator;

import BattleshipsInterface.*;

import java.io.IOException;

public interface IPlayerLoader
{
  IBattleshipsPlayerWrapper getPlayer() throws IOException, InstantiationException, IllegalAccessException;
}
