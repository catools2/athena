package org.catools.athena.common.feign;

import feign.Request;
import feign.RetryableException;
import feign.Retryer;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class IdempotentMethodRetryer implements Retryer {

  private static final Set<String> IDEMPOTENT_METHODS = Set.of("GET", "HEAD", "OPTIONS", "PUT", "DELETE");

  private final long initialIntervalMillis;
  private final long maxIntervalMillis;
  private final int maxAttempts;
  private int attempt;
  private long nextInterval;

  public IdempotentMethodRetryer() {
    this(100, TimeUnit.SECONDS.toMillis(1), 5);
  }

  public IdempotentMethodRetryer(long initialIntervalMillis, long maxIntervalMillis, int maxAttempts) {
    this.initialIntervalMillis = initialIntervalMillis;
    this.maxIntervalMillis = maxIntervalMillis;
    this.maxAttempts = maxAttempts;
    this.attempt = 1;
    this.nextInterval = initialIntervalMillis;
  }

  @Override
  public void continueOrPropagate(RetryableException e) {
    if (!isIdempotentMethod(e.request())) {
      throw e;
    }

    if (attempt++ >= maxAttempts) {
      throw e;
    }

    try {
      Thread.sleep(nextInterval);
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
      throw e;
    }

    nextInterval = Math.min(nextInterval * 2, maxIntervalMillis);
  }

  @Override
  public Retryer clone() {
    return new IdempotentMethodRetryer(initialIntervalMillis, maxIntervalMillis, maxAttempts);
  }

  private boolean isIdempotentMethod(Request request) {
    return request != null && IDEMPOTENT_METHODS.contains(request.httpMethod());
  }
}

