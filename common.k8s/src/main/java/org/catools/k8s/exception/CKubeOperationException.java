package org.catools.k8s.exception;

/**
 * Signals that an attempt to perform operation against the Kubernetes cluster denoted by a specified configuration.
 */
public class CKubeOperationException extends RuntimeException {

  public CKubeOperationException(String message, Throwable cause) {
    super(String.format("Failed to perform operation on workbook. message: %s", message), cause);
  }

  public CKubeOperationException(String filename, String message, Throwable cause) {
    super(String.format("Failed to perform operation on workbook from %s. message: %s", filename, message), cause);
  }
}
