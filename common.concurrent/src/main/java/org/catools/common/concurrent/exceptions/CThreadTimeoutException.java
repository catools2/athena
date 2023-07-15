package org.catools.common.concurrent.exceptions;

/**
 * Signals that performing job took more than expected time and execution terminated.
 */
public class CThreadTimeoutException extends RuntimeException {

  public CThreadTimeoutException(String description) {
    super(description);
  }
}
