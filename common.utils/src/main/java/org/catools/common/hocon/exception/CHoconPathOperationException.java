package org.catools.common.hocon.exception;

/**
 * Signals that an attempt to read path from json object has failed.
 */
public class CHoconPathOperationException extends RuntimeException {

  public CHoconPathOperationException(String content, String jsonPath, Throwable cause) {
    super(String.format("Failed to read path from json object." +
        "(see https://github.com/json-path/JsonPath for JsonPath expression)\n" +
        "Path: %s\nJsonContent:%s\n", jsonPath, content), cause);
  }
}
