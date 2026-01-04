package org.catools.athena.rest.feign.common.exception;

/**
 * Signals which throws when attempt to generate json string from object failed.
 */
public class JsonException extends RuntimeException {

  public JsonException(String message, Throwable cause) {
    super(message, cause);
  }
}
