package org.catools.athena.common.utils;

import org.catools.athena.common.exception.AthenaInterruptedException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RetryUtilsTest {

  @Test
  void retryShallTryToTheLastAttemptEvenIfExceptionThrowsInPreviousCalls() {
    AtomicInteger counter = new AtomicInteger();
    boolean returnedValue = RetryUtils.retry(10, 10, idx -> {
      if (counter.incrementAndGet() < 10) throw new RuntimeException("Ops!!!");
      return true;
    });
    assertThat(counter.get(), equalTo(10));
    assertThat(returnedValue, equalTo(true));
  }

  @Test
  void retryShallTryToTheLastAttemptAndFailIfStillExceptionThrows() {
    AtomicInteger counter = new AtomicInteger();
    assertThrows(RuntimeException.class, () -> {
      RetryUtils.retry(9, 10, idx -> {
        if (counter.incrementAndGet() < 10) throw new RuntimeException("Ops!!!");
        return true;
      });
    });
  }


  @Test
  void testRetrySuccessWithoutRetries() {
    IntFunction<String> function = mock(IntFunction.class);
    when(function.apply(1)).thenReturn("Success");

    String result = RetryUtils.retry(3, 100, function);

    assertEquals("Success", result);
    verify(function, times(1)).apply(1);
  }

  @Test
  void testRetrySuccessAfterRetries() {
    IntFunction<String> function = mock(IntFunction.class);
    when(function.apply(1)).thenThrow(new RuntimeException("First attempt failed"));
    when(function.apply(2)).thenReturn("Success");

    String result = RetryUtils.retry(3, 100, function);

    assertEquals("Success", result);
    verify(function, times(1)).apply(1);
    verify(function, times(1)).apply(2);
  }

  @Test
  void testRetryFailureAfterAllRetries() {
    IntFunction<String> function = mock(IntFunction.class);
    when(function.apply(anyInt())).thenThrow(new RuntimeException("Failed"));

    RetryFailedException exception = assertThrows(RetryFailedException.class, () -> {
      RetryUtils.retry(3, 100, function);
    });

    assertNotNull(exception.getCause());
    assertEquals("Failed", exception.getCause().getMessage());
    verify(function, times(3)).apply(anyInt());
  }

  @Test
  void testSleepInterruptedException() {
    // We need to test the private sleep method, which is not directly possible.
    // Instead, we test the retry logic that uses sleep.
    IntFunction<String> function = mock(IntFunction.class);
    when(function.apply(anyInt())).thenThrow(new RuntimeException("Failed"));

    Thread.currentThread().interrupt(); // Simulate interrupt

    assertThrows(AthenaInterruptedException.class, () -> {
      RetryUtils.retry(3, 100, function);
    });
  }
}