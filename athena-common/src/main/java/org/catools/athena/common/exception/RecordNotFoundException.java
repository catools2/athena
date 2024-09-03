package org.catools.athena.common.exception;

public class RecordNotFoundException extends RuntimeException {
  public RecordNotFoundException(String recordName, String searchCriteria, Object input) {
    super("No " + recordName + " with " + searchCriteria + " " + input + " found.");
  }
}
