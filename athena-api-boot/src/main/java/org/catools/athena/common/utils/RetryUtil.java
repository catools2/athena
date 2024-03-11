package org.catools.athena.common.utils;

import lombok.experimental.UtilityClass;

import java.util.function.Function;

@UtilityClass
public class RetryUtil {
  public static <R> R retry(int retryCount, int interval, Function<Integer, R> m) {
    Throwable ex = null;
    int counter = 0;

    while (retryCount-- > 0) {
      try {
        counter++;
        return m.apply(counter);
      } catch (Throwable e) {
        ex = e;
      }

      try {
        Thread.sleep(interval);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    if (ex instanceof RuntimeException) {
      throw (RuntimeException) ex;
    }
    throw new RuntimeException(ex);

  }
}
