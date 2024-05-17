package org.catools.athena.common.utils;

import lombok.experimental.UtilityClass;

import java.util.function.IntFunction;

@UtilityClass
public class RetryUtils {
  public static <R> R retry(int retryCount, int interval, IntFunction<R> m) {
    Exception ex = null;
    int counter = 0;

    while (retryCount-- > 0) {
      try {
        counter++;
        return m.apply(counter);
      } catch (Exception e) {
        ex = e;
      }

      sleep(interval);
    }

    throw new RetryFailedException(ex);
  }

  @SuppressWarnings({"java:S2142", "java:S112"})
  private static void sleep(int interval) {
    try {
      Thread.sleep(interval);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
