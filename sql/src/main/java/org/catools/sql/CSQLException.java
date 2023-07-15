package org.catools.sql;

import org.catools.common.exception.CRuntimeException;

public class CSQLException extends CRuntimeException {

  public CSQLException(String message, Throwable cause) {
    super(message, cause);
  }
}
