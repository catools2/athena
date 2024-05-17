package org.catools.athena.common.utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}