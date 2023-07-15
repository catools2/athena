package org.catools.common.faker.exception;

public class CFakerParameterException extends RuntimeException {
  public CFakerParameterException(String description) {
    super(description);
  }

  public CFakerParameterException(String message, Throwable cause) {
    super(message, cause);
  }
}
