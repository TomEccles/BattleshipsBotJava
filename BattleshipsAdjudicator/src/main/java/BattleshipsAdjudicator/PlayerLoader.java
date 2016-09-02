package BattleshipsAdjudicator;

import BattleshipsInterface.*;

public class PlayerLoader implements IPlayerLoader
{
  public PlayerLoader(String dllPath)
  {
    String name = (Path.GetFileNameWithoutExtension(dllPath) != null) ? Path.GetFileNameWithoutExtension(dllPath) : "NoName";

    System.AppDomain appDomain = AppDomain.CreateDomain(name);

    setPlayer((IBattleshipsPlayerWrapper)appDomain.CreateInstanceAndUnwrap(Assembly.GetExecutingAssembly().FullName, CrossDomainPlayerWrapper.class.FullName, false, BindingFlags.Default, null, new Object[] {dllPath, name}, null, null));
  }

  private IBattleshipsPlayerWrapper Player;
  public final IBattleshipsPlayerWrapper getPlayer()
  {
    return Player;
  }
  private void setPlayer(IBattleshipsPlayerWrapper value)
  {
    Player = value;
  }
}
