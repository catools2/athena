package org.catools.common.security;

import org.catools.common.exception.CRuntimeException;

public class CCipherException extends CRuntimeException {

  public CCipherException(String operation, String transformation, String input, Throwable t) {
    super(String.format("Failed to %s string using %s transformation '%s' ", operation, transformation, input), t);
  }
}
