package BattleshipsAdjudicator;

import BattleshipsInterface.*;

public class CrossDomainPlayerWrapper extends MarshalByRefObject implements IBattleshipsPlayerWrapper
{
  private IBattleshipsPlayer _player;
  private java.lang.Class _playerType;

  public CrossDomainPlayerWrapper(String dllPath, String name)
  {
    System.Reflection.Assembly assembly = Assembly.LoadFrom(dllPath);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
    var publicTypes = assembly.GetExportedTypes();

    _playerType = FindPlayerType(publicTypes);

    if (_playerType == null)
    {
      throw new RuntimeException("Couldn't find a class that implements IBattleshipsPlayer in " + dllPath);
    }

    ResetPlayer();

    try
    {
      setName(name + " (" + _player.Name + ")");
    }
    catch (RuntimeException e)
    {
      throw new BadNameException("Player threw exception whilst getting Name", e);
    }
  }

  private java.lang.Class FindPlayerType(java.lang.Class[] publicTypes)
  {
    java.lang.Class playerType = null;
    for (java.lang.Class type : publicTypes)
    {
      if (type.IsAbstract)
      {
        continue;
      }
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
      var interfaces = type.FindInterfaces(CompareInterface, IBattleshipsPlayer.class);
      if (interfaces.getLength() > 0)
      {
        playerType = type;
        break;
      }
    }
    return playerType;
  }

  private boolean CompareInterface(java.lang.Class typeToCompare, Object typeToFind)
  {
    return typeToCompare == (java.lang.Class)typeToFind;
  }

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#region Implementation of IBattleshipsPlayer

  private String Name;
  public final String getName()
  {
    return Name;
  }
  private void setName(String value)
  {
    Name = value;
  }

  public final Iterable<IShipPosition> GetShipPositions()
  {
    return _player.GetShipPositions().Select(position -> new SerializableShipPosition(position)).ToArray();
  }

  public final void SelectTarget(tangible.RefObject<Character> row, tangible.RefObject<Integer> column)
  {
    _player.SelectTarget(row, column);
  }

  public final void ShotResult(boolean wasHit)
  {
    _player.ShotResult(wasHit);
  }

  public final void OpponentsShot(char row, int column)
  {
    _player.OpponentsShot(row, column);
  }

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#endregion

  public final void ResetPlayer()
  {
    _player = (IBattleshipsPlayer)Activator.CreateInstance(_playerType);
  }

  @Override
  public Object InitializeLifetimeService()
  {
    ILease lease = (ILease)super.InitializeLifetimeService();
    if (lease.CurrentState == LeaseState.Initial)
    {
      lease.InitialLeaseTime = Duration.FromDays(1);
    }
    return lease;
  }
}

