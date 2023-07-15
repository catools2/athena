package org.catools.common.exception;

/**
 * Signals that attempt to find provided resource failed.
 */
public class CResourceNotFoundException extends RuntimeException {
  public CResourceNotFoundException(String message) {
    super(message);
  }

  public CResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
