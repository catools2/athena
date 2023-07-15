package org.catools.common.exception;

/**
 * Signals that attempt to convert provided value to json or backward failed.
 */
public class CInvalidJsonFormatException extends RuntimeException {
  public CInvalidJsonFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
