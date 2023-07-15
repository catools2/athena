package org.catools.media.exception;

import org.catools.common.exception.CRuntimeException;

public class CIOException extends CRuntimeException {
  public CIOException(String message, Throwable cause) {
    super(message, cause);
  }
}
