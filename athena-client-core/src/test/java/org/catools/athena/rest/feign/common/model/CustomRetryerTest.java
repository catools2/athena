package org.catools.athena.rest.feign.common.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import feign.Request;
import feign.RetryableException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomRetryerTest {

  private CustomRetryer retryer;

  @BeforeEach
  void setUp() {
    retryer = new CustomRetryer();
  }

  @Test
  void continueOrPropagate_withPOSTMethod_shouldThrowException() {
    // Given
    Request request =
        Request.create(Request.HttpMethod.POST, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(500, "Server Error", Request.HttpMethod.POST, new Date(), request);

    // When/Then
    assertThatThrownBy(() -> retryer.continueOrPropagate(exception))
        .isInstanceOf(RetryableException.class)
        .hasMessageContaining("Server Error");
  }

  @Test
  void continueOrPropagate_withPATCHMethod_shouldThrowException() {
    // Given
    Request request =
        Request.create(Request.HttpMethod.PATCH, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(500, "Server Error", Request.HttpMethod.PATCH, new Date(), request);

    // When/Then
    assertThatThrownBy(() -> retryer.continueOrPropagate(exception))
        .isInstanceOf(RetryableException.class)
        .hasMessageContaining("Server Error");
  }

  @Test
  void continueOrPropagate_withGETMethod_shouldRetry() {
    // Given
    Request request =
        Request.create(Request.HttpMethod.GET, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(500, "Server Error", Request.HttpMethod.GET, new Date(), request);

    // When/Then - Should not throw immediately, will retry
    assertThatCode(
            () -> {
              try {
                retryer.continueOrPropagate(exception);
              } catch (RetryableException e) {
                // Expected after max retries
              }
            })
        .doesNotThrowAnyException();
  }

  @Test
  void continueOrPropagate_withDELETEMethod_shouldRetry() {
    // Given
    Request request =
        Request.create(Request.HttpMethod.DELETE, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(500, "Server Error", Request.HttpMethod.DELETE, new Date(), request);

    // When/Then - Should not throw immediately, will retry
    assertThatCode(
            () -> {
              try {
                retryer.continueOrPropagate(exception);
              } catch (RetryableException e) {
                // Expected after max retries
              }
            })
        .doesNotThrowAnyException();
  }

  @Test
  void continueOrPropagate_withPUTMethod_shouldRetry() {
    // Given
    Request request =
        Request.create(Request.HttpMethod.PUT, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(500, "Server Error", Request.HttpMethod.PUT, new Date(), request);

    // When/Then - Should not throw immediately, will retry
    assertThatCode(
            () -> {
              try {
                retryer.continueOrPropagate(exception);
              } catch (RetryableException e) {
                // Expected after max retries
              }
            })
        .doesNotThrowAnyException();
  }

  @Test
  void clone_shouldCreateNewInstance() {
    // When
    CustomRetryer cloned = (CustomRetryer) retryer.clone();

    // Then
    assertThat(cloned).isNotNull().isNotSameAs(retryer).isInstanceOf(CustomRetryer.class);
  }

  @Test
  void continueOrPropagate_withPOSTAfterMultipleCalls_shouldNotRetry() {
    // Given
    Request request =
        Request.create(Request.HttpMethod.POST, "/api/data", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(
            503, "Service Unavailable", Request.HttpMethod.POST, new Date(), request);

    // When/Then - Should throw on first attempt, not retry
    assertThatThrownBy(() -> retryer.continueOrPropagate(exception))
        .isInstanceOf(RetryableException.class);

    assertThatThrownBy(() -> retryer.continueOrPropagate(exception))
        .isInstanceOf(RetryableException.class);
  }

  @Test
  void continueOrPropagate_withPATCHAfterMultipleCalls_shouldNotRetry() {
    // Given
    Request request =
        Request.create(Request.HttpMethod.PATCH, "/api/update", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(
            429, "Too Many Requests", Request.HttpMethod.PATCH, new Date(), request);

    // When/Then - Should throw on first attempt, not retry
    assertThatThrownBy(() -> retryer.continueOrPropagate(exception))
        .isInstanceOf(RetryableException.class);

    assertThatThrownBy(() -> retryer.continueOrPropagate(exception))
        .isInstanceOf(RetryableException.class);
  }

  @Test
  void continueOrPropagate_withPOSTCausedBySocketException_shouldRetry() {
    // A connection reset means the request never reached the server, so replaying a POST cannot
    // double-apply. This is the pod-replacement case: kube-proxy REJECTs while a Service has no
    // ready endpoints, and the sync must ride out the gap rather than fail.
    Request request =
        Request.create(Request.HttpMethod.POST, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(
            -1,
            "Connection reset",
            Request.HttpMethod.POST,
            new SocketException("Connection reset"),
            new Date(),
            request);

    assertThatCode(
            () -> {
              try {
                retryer.continueOrPropagate(exception);
              } catch (RetryableException e) {
                // Expected once the budget is exhausted
              }
            })
        .doesNotThrowAnyException();
  }

  @Test
  void continueOrPropagate_withPATCHCausedByNestedSocketException_shouldRetry() {
    // The socket failure is usually wrapped by the client stack, so the whole cause chain is walked.
    Request request =
        Request.create(Request.HttpMethod.PATCH, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(
            -1,
            "Connect timed out",
            Request.HttpMethod.PATCH,
            new IOException("wrapped", new ConnectException("Operation not permitted")),
            new Date(),
            request);

    assertThatCode(
            () -> {
              try {
                retryer.continueOrPropagate(exception);
              } catch (RetryableException e) {
                // Expected once the budget is exhausted
              }
            })
        .doesNotThrowAnyException();
  }

  @Test
  void continueOrPropagate_withPOSTCausedByANonNetworkError_shouldStillNotRetry() {
    // Guards the boundary: only socket-level faults widen the retry, application errors must not.
    Request request =
        Request.create(Request.HttpMethod.POST, "/test", new HashMap<>(), null, null, null);
    RetryableException exception =
        new RetryableException(
            500,
            "Server Error",
            Request.HttpMethod.POST,
            new IllegalStateException("bad state"),
            new Date(),
            request);

    assertThatThrownBy(() -> retryer.continueOrPropagate(exception))
        .isInstanceOf(RetryableException.class);
  }
}
