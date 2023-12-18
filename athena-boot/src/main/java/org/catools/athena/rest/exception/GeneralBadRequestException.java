package org.catools.athena.rest.exception;

public class GeneralBadRequestException extends RuntimeException {
  public GeneralBadRequestException(String message) {
    super(message);
  }

  public GeneralBadRequestException(Throwable cause) {
    super(cause);
  }
}
