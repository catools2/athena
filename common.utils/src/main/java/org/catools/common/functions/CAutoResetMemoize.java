package org.catools.common.functions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

/**
 * Often while working with expensive resources like file, it is reasonable to perform action once
 * and then save result of compute for future use. This is what Memoize do. But what if your data
 * invalidate over time and you will need to re generated. This is what CAutoResetMemoize do
 *
 * @param <T> the result of
 */
public class CAutoResetMemoize<T> extends CMemoize<T> {
  private Timer timer = new Timer();
  private long resetIntervalInSecond;

  public CAutoResetMemoize(Supplier<T> delegate, long resetIntervalInSecond) {
    super(delegate);
    this.resetIntervalInSecond = resetIntervalInSecond;
  }

  @Override
  public T get() {
    T t = super.get();
    timer.schedule(getTask(), resetIntervalInSecond * 1000);
    return t;
  }

  private TimerTask getTask() {
    return new TimerTask() {
      @Override
      public void run() {
        reset();
      }
    };
  }
}
