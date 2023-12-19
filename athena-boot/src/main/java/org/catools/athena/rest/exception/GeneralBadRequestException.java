package org.catools.athena.rest.exception;

public class GeneralBadRequestException extends RuntimeException {
  public GeneralBadRequestException() {
  }

  public GeneralBadRequestException(String message) {
    super(message);
  }

  public GeneralBadRequestException(Throwable cause) {
    super(cause);
  }

  public GeneralBadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
