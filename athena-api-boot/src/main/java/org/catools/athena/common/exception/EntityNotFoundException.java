package org.catools.athena.common.exception;

public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException(String entityName, String entityCode) {
    this("No " + entityName + " with code " + entityCode + " found.");
  }

  public EntityNotFoundException(String message) {
    super(message);
  }
}
