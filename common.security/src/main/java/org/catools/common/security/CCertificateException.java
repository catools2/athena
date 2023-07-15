package org.catools.common.security;

import org.catools.common.exception.CRuntimeException;

public class CCertificateException extends CRuntimeException {

  public CCertificateException(String message, Throwable t) {
    super(message, t);
  }
}
