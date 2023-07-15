package org.catools.common.hocon.exception;

/**
 * Signals that an attempt to read path from json object has failed.
 */
public class CHoconException extends RuntimeException {

  public CHoconException(String message, Throwable cause) {
    super(message, cause);
  }
}
