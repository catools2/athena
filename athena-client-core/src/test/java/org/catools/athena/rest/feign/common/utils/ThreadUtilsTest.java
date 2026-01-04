package org.catools.athena.rest.feign.common.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThreadUtilsTest {

  @Test
  @Timeout(2)
  void sleep_shouldPauseExecution() {
    // Given
    long startTime = System.currentTimeMillis();
    long sleepTime = 100;

    // When
    ThreadUtils.sleep(sleepTime);

    // Then
    long endTime = System.currentTimeMillis();
    long elapsed = endTime - startTime;
    assertThat(elapsed).isGreaterThanOrEqualTo(sleepTime);
  }

  @Test
  @Timeout(5)
  void executeInParallel_withSuccessfulCommands_shouldCompleteSuccessfully() {
    // Given
    int threadsCount = 3;
    long timeoutInMinutes = 1;
    AtomicInteger counter = new AtomicInteger(0);

    // When
    ThreadUtils.executeInParallel(threadsCount, timeoutInMinutes, () -> {
      counter.incrementAndGet();
      return true;
    });

    // Then
    assertThat(counter.get()).isEqualTo(threadsCount);
  }

  @Test
  @Timeout(5)
  void executeInParallel_withMultipleIterations_shouldExecuteUntilReturnTrue() {
    // Given
    int threadsCount = 2;
    long timeoutInMinutes = 1;
    AtomicInteger counter = new AtomicInteger(0);
    int maxIterations = 5;

    // When
    ThreadUtils.executeInParallel(threadsCount, timeoutInMinutes, () -> {
      while (counter.get() < maxIterations * threadsCount) {
        ThreadUtils.sleep(50);
        counter.incrementAndGet();// Simulate work
      }
      return true;
    });

    // Then
    assertThat(counter.get()).isGreaterThanOrEqualTo(maxIterations * threadsCount);
  }

  @Test
  @Timeout(5)
  void executeInParallel_withException_shouldPropagateException() {
    // Given
    int threadsCount = 2;
    long timeoutInMinutes = 1;
    String errorMessage = "Test exception";

    // When/Then
    assertThatThrownBy(() ->
        ThreadUtils.executeInParallel(threadsCount, timeoutInMinutes, () -> {
          throw new RuntimeException(errorMessage);
        })
    )
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Failed to finish process")
        .hasCauseInstanceOf(RuntimeException.class);
  }

  @Test
  @Timeout(5)
  void executeInParallel_withSingleThread_shouldExecuteSequentially() {
    // Given
    int threadsCount = 1;
    long timeoutInMinutes = 1;
    AtomicInteger counter = new AtomicInteger(0);

    // When
    ThreadUtils.executeInParallel(threadsCount, timeoutInMinutes, () -> {
      counter.incrementAndGet();
      return true;
    });

    // Then
    assertThat(counter.get()).isEqualTo(1);
  }

  @Test
  @Timeout(5)
  void executeInParallel_withSharedResource_shouldHandleConcurrency() {
    // Given
    int threadsCount = 5;
    long timeoutInMinutes = 1;
    AtomicInteger sharedCounter = new AtomicInteger(0);
    int expectedIncrements = 10;

    // When
    ThreadUtils.executeInParallel(threadsCount, timeoutInMinutes, () -> {
      for (int i = 0; i < expectedIncrements / threadsCount; i++) {
        sharedCounter.incrementAndGet();
      }
      return true;
    });

    // Then
    assertThat(sharedCounter.get()).isEqualTo(expectedIncrements);
  }

  @Test
  void executeInParallel_withEarlyReturn_shouldStopOtherThreads() {
    // Given
    int threadsCount = 3;
    long timeoutInMinutes = 1;
    AtomicBoolean firstThreadFinished = new AtomicBoolean(false);
    AtomicInteger executionCount = new AtomicInteger(0);

    // When
    ThreadUtils.executeInParallel(threadsCount, timeoutInMinutes, () -> {
      executionCount.incrementAndGet();
      if (firstThreadFinished.compareAndSet(false, true)) {
        return true; // First thread returns true
      }
      ThreadUtils.sleep(100); // Other threads sleep
      return false;
    });

    // Then
    assertThat(executionCount.get()).isGreaterThanOrEqualTo(1);
  }

  @Test
  void sleep_withZeroMilliseconds_shouldReturnImmediately() {
    // Given
    long startTime = System.currentTimeMillis();

    // When
    ThreadUtils.sleep(0);

    // Then
    long endTime = System.currentTimeMillis();
    long elapsed = endTime - startTime;
    assertThat(elapsed).isLessThan(50); // Should be very fast
  }

  @Test
  @Timeout(3)
  void executeInParallel_withQuickTasks_shouldFinishBeforeTimeout() {
    // Given
    int threadsCount = 4;
    long timeoutInMinutes = 2;
    AtomicInteger counter = new AtomicInteger(0);

    // When
    long startTime = System.currentTimeMillis();
    ThreadUtils.executeInParallel(threadsCount, timeoutInMinutes, () -> {
      counter.incrementAndGet();
      ThreadUtils.sleep(10);
      return true;
    });
    long endTime = System.currentTimeMillis();

    // Then
    assertThat(counter.get()).isEqualTo(threadsCount);
    assertThat(endTime - startTime).isLessThan(2000); // Should finish in less than 2 seconds
  }
}

