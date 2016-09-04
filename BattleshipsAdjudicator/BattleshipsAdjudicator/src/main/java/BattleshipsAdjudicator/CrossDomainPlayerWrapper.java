package BattleshipsAdjudicator;

import BattleshipsInterface.IBattleshipsPlayer;
import BattleshipsInterface.ICoordinate;
import BattleshipsInterface.IShipPosition;
import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class CrossDomainPlayerWrapper implements IBattleshipsPlayerWrapper {
  private IBattleshipsPlayer _player;
  private java.lang.Class _playerType;

  private List<? extends Class<?>> getClassesFrom(File pathToJar) throws IOException {
    JarFile jarFile = new JarFile(pathToJar);
    List<JarEntry> entries = Collections.list(jarFile.entries());

    URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
    URLClassLoader cl = URLClassLoader.newInstance(urls);

    return entries.stream().filter(jarEntry ->
      !jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")
    ).map(jarEntry -> {
      String className = FilenameUtils.getPath(jarEntry.getName()) + FilenameUtils.getBaseName(jarEntry.getName());
      className = className.replace('/', '.');
      try {
        return cl.loadClass(className);
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toList());
  }

  public CrossDomainPlayerWrapper(File pathToJar, String name) throws IOException, InstantiationException, IllegalAccessException {
    List<? extends Class<?>> classes = getClassesFrom(pathToJar);

    _playerType = FindPlayerType(classes);

    if (_playerType == null) {
      throw new RuntimeException("Couldn't find a class that implements IBattleshipsPlayer in " + pathToJar.getName());
    }

    ResetPlayer();

    try {
      setName(name + " (" + _player.getName() + ")");
    } catch (RuntimeException e) {
      throw new BadNameException("Player threw exception whilst getting Name", e);
    }
  }

  private java.lang.Class FindPlayerType(List<? extends Class<?>> classes) {
    Class playerType = null;
    for (Class type : classes) {
      if (Modifier.isAbstract(type.getModifiers())) {
        continue;
      }
      if (IBattleshipsPlayer.class.isAssignableFrom(type)) {
        playerType = type;
        break;
      }
    }
    return playerType;
  }

  private String Name;
  public final String getName() {
    return Name;
  }

  private void setName(String value) {
    Name = value;
  }

  public final Iterable<IShipPosition> GetShipPositions() {
    return Lists.newArrayList(_player.GetShipPositions()).stream().map(SerializableShipPosition::new).collect(Collectors.toList());
  }

  public final ICoordinate SelectTarget() {
    return _player.SelectTarget();
  }

  public final void ShotResult(boolean wasHit) {
    _player.ShotResult(wasHit);
  }

  public final void OpponentsShot(char row, int column) {
    _player.OpponentsShot(row, column);
  }

  public final void ResetPlayer() throws InstantiationException, IllegalAccessException {
    _player = (IBattleshipsPlayer)_playerType.newInstance();
  }
}
