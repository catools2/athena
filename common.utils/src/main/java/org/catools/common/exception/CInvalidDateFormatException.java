package org.catools.common.exception;

/**
 * Signals that attempt to convert provided value to Date value failed.
 */
public class CInvalidDateFormatException extends RuntimeException {
  public CInvalidDateFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
