package org.catools.atlassian.zapi.exception;

import org.catools.common.exception.CRuntimeException;

public class CZApiException extends CRuntimeException {

  public CZApiException(String message, Throwable t) {
    super(message, t);
  }
}
