package org.catools.common.exception;

/**
 * Signals to identify place where we have known application defect. In high performances system we
 * should not use this type. In any case this is something that might help to identify the area
 * where application behaviour is not following automation needs.
 *
 * <p>Hope you do not have to use it :)
 */
public class CRuntimeException extends RuntimeException {
  public CRuntimeException(String description) {
    super(description);
  }

  public CRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
