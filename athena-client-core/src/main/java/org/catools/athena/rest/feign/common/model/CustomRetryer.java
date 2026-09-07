package org.catools.athena.rest.feign.common.model;

import feign.Request.HttpMethod;
import feign.RetryableException;
import feign.Retryer;
import java.net.SocketException;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CustomRetryer extends Retryer.Default {
  private static final List<HttpMethod> nonIdempotentMethods =
      List.of(HttpMethod.POST, HttpMethod.PATCH);

  @Override
  public void continueOrPropagate(RetryableException e) {
    // A reset or refused connection is an infrastructure fault: the request never reached the
    // server, so replaying it cannot double-apply. Withholding the retry here only converts a
    // recoverable blip into a failed sync, so transient network errors are retried on every method.
    if (isTransientNetworkError(e)) {
      log.debug("Transient network error detected, will retry: {}", e.getMessage());
      super.continueOrPropagate(e);
      return;
    }

    // An application-level error may well have been applied server-side, so a non-idempotent
    // method must not be replayed.
    if (nonIdempotentMethods.contains(e.method())) {
      log.debug(
          "Non-idempotent method {} with application error, will not retry: {}",
          e.method(),
          e.getMessage());
      throw e;
    }

    super.continueOrPropagate(e);
  }

  /**
   * Checks if the exception is caused by a transient network error that should be retried
   * regardless of HTTP method. SocketException covers connection reset, connection refused and the
   * kube-proxy REJECT a Service with no ready endpoints produces.
   */
  private static boolean isTransientNetworkError(RetryableException e) {
    Throwable cause = e;
    while (cause != null) {
      if (cause instanceof SocketException) {
        return true;
      }
      cause = cause.getCause();
    }
    return false;
  }

  public Retryer clone() {
    return new CustomRetryer();
  }
}
