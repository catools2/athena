package org.catools.common.concurrent.exceptions;

public class CInterruptedException extends RuntimeException {

  public CInterruptedException(String message, Throwable cause) {
    super(message, cause);
  }
}
