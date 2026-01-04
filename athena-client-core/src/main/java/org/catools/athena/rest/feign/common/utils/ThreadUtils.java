package org.catools.athena.rest.feign.common.utils;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@UtilityClass
public class ThreadUtils {

  public static void sleep(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException ignored) {
    }
  }

  public static void executeInParallel(int threadsCount, long timeoutInMinutes, Supplier<Boolean> command) {
    AtomicReference<Throwable> hasError = new AtomicReference<>();
    ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
    try {
      while (threadsCount-- > 0) {
        executor.execute(() -> {
          try {
            command.get();
          } catch (Throwable t) {
            hasError.set(t);
            throw t;
          }
        });
      }
    } finally {
      executor.shutdown();
    }

    try {
      if (!executor.awaitTermination(timeoutInMinutes, TimeUnit.MINUTES)) {
        throw new RuntimeException("Failed to terminate worker");
      }
    } catch (InterruptedException e) {
      throw new RuntimeException("Failed to terminate worker", e);
    }

    if (hasError.get() != null) {
      throw new RuntimeException("Failed to finish process", hasError.get());
    }
  }

}
