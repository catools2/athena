package org.catools.atlassian.zapi.exception;

import org.catools.common.exception.CRuntimeException;

public class CZApiClientException extends CRuntimeException {

  public CZApiClientException(String message, Throwable t) {
    super(message, t);
  }
}
