package BattleshipsAdjudicator;

import com.google.common.base.Stopwatch;
import org.joda.time.Duration;

import java.util.concurrent.TimeUnit;

/**
 Timer that can be started and stopped. Throws an exception if the total
 time it has run for when stopped exceeds the time limit provided.
 */
public class Timer
{
  private Duration _timeLimit =  Duration.ZERO;
  private final Stopwatch _timer = Stopwatch.createUnstarted();

  public Timer(Duration timeLimit)
  {
    _timeLimit = timeLimit;
  }

  public final void Start()
  {
    if (_timer.isRunning())
    {
      throw new IllegalStateException("Can't start a timer twice in a row");
    }

    _timer.start();
  }

  public final void Stop()
  {
    if (!_timer.isRunning())
    {
      throw new IllegalStateException("Can't stop a timer before it's started");
    }

    _timer.stop();

    if (getElapsedMilliseconds() > _timeLimit.getMillis())
    {
      throw new OutOfTimeException();
    }
  }

  public final long getElapsedMilliseconds()
  {
    return _timer.elapsed(TimeUnit.MILLISECONDS);
  }
}

