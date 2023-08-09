package org.catools.git.exception;

import org.catools.common.exception.CRuntimeException;

public class CGitClientException extends CRuntimeException {

  public CGitClientException(String message, Throwable t) {
    super(message, t);
  }
}
