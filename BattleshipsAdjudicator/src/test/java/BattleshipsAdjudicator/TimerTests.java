package BattleshipsAdjudicator.Tests;

import BattleshipsAdjudicator.OutOfTimeException;
import BattleshipsAdjudicator.Timer;
import org.joda.time.Duration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.fail;

public class TimerTests {
  private static final int testTimeSpanMilliseconds = 500;
  private static final int testAccuracyMilliseconds = 30;
  private final Duration testTimeLimit = Duration.millis(testTimeSpanMilliseconds);

  @Test(expected = IllegalStateException.class)
  public final void TestCantStopBeforeStart()
  {
    // Arrange
    Timer timer = new Timer(testTimeLimit);

    // Act & Assert
    timer.Stop();
  }

  @Test(expected = IllegalStateException.class)
  public final void TestCantStartTwice()
  {
    // Arrange
    Timer timer = new Timer(testTimeLimit);

    // Act & Assert
    timer.Start();
    timer.Start();
  }

  @Test(expected = IllegalStateException.class)
  public final void TestCantStopTwice()
  {
    // Arrange
    Timer timer = new Timer(testTimeLimit);

    // Act & Assert
    timer.Start();
    timer.Stop();
    timer.Stop();
  }

  @Test(expected = OutOfTimeException.class)
  public final void TestTimeLimitReached() throws InterruptedException
  {
    // Arrange
    Timer timer = new Timer(testTimeLimit);

    // Act & Assert
    timer.Start();
    Thread.sleep(testTimeSpanMilliseconds + testAccuracyMilliseconds);
    timer.Stop();
  }

  @Test
  public final void TestTimerDoesntStartEarly() throws InterruptedException
  {
    // Arrange
    Timer timer = new Timer(testTimeLimit);

    // Act & Assert
    Thread.sleep(testTimeSpanMilliseconds + testAccuracyMilliseconds);
    timer.Start();
    timer.Stop();
  }

  @Test(expected = OutOfTimeException.class)
  public final void TestMultipleStartsAndStops() throws InterruptedException
  {
    // Arrange
    int timeStep = testTimeSpanMilliseconds / 5;
    Timer timer = new Timer(testTimeLimit);

    // Act & Assert
    try
    {
      timer.Start();
      Thread.sleep(timeStep);
      timer.Stop();

      Thread.sleep(timeStep);

      timer.Start();
      Thread.sleep(timeStep);
      timer.Stop();

      Thread.sleep(timeStep);

      timer.Start();
      Thread.sleep(timeStep);
      timer.Stop();

      Thread.sleep(timeStep);

      timer.Start();
      Thread.sleep(timeStep);
      timer.Stop();

      Thread.sleep(timeStep);
    } catch (RuntimeException e) {
      fail("Timer threw exception early: " + e.toString());
    }

    timer.Start();
    Thread.sleep(timeStep + testAccuracyMilliseconds);
    timer.Stop();
  }
}
