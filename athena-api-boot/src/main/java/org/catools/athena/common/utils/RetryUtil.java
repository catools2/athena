package org.catools.athena.common.utils;

import lombok.experimental.UtilityClass;

import java.util.function.IntFunction;

@UtilityClass
public class RetryUtil {
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

    if (ex instanceof RuntimeException rex) {
      throw rex;
    }
    throw new RuntimeException(ex);

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
