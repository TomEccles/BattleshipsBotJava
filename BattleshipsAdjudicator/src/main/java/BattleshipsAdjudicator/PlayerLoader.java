package BattleshipsAdjudicator;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class PlayerLoader implements IPlayerLoader
{
  public PlayerLoader(File jarFile) throws IOException, InstantiationException, IllegalAccessException {
    String name = Optional.ofNullable(FilenameUtils.getBaseName(jarFile.getName())).orElse("NoName");

    Player = new CrossDomainPlayerWrapper(jarFile, name);
  }

  private IBattleshipsPlayerWrapper Player;
  public final IBattleshipsPlayerWrapper getPlayer()
  {
    return Player;
  }
}
