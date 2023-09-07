package org.catools.etl.git.exception;

import org.catools.common.exception.CRuntimeException;

public class CGitApiException extends CRuntimeException {

  public CGitApiException(String message, Throwable t) {
    super(message, t);
  }
}
