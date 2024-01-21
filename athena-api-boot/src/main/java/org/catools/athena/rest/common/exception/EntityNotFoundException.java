package org.catools.athena.rest.common.exception;

public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException(String entityName, String entityCode) {
    super("No " + entityName + " with code " + entityCode + " found.");
  }
}
