package org.catools.common.exception;

/**
 * Signals which throws when attempt to generate json string from object failed.
 */
public class CJsonGenerationException extends RuntimeException {

  public CJsonGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}
