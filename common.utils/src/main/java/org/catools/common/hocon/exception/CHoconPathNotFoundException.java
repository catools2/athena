package org.catools.common.hocon.exception;

/**
 * Signals that an attempt to read path from json object has failed because the path does not exist.
 */
public class CHoconPathNotFoundException extends RuntimeException {

  public CHoconPathNotFoundException(String content, String jsonPath, Throwable cause) {
    super(String.format("Json path not found." +
        "(see https://github.com/json-path/JsonPath for JsonPath expression)\n" +
        "Path: %s\nJsonContent:%s\n", jsonPath, content), cause);
  }
}
