package org.catools.common.security;

import org.catools.common.exception.CRuntimeException;

/**
 * Signals that attempt to perform operation against provided keystore failed.
 */
public class CKeyStoreException extends CRuntimeException {

  public CKeyStoreException(String message, Throwable cause) {
    super(message, cause);
  }
}
