package org.catools.athena.rest.feign.kube.exception;

/**
 * Signals that an attempt to perform operation against the Kubernetes cluster denoted by a specified configuration.
 */
public class KubeOperationException extends RuntimeException {

  public KubeOperationException(final String message, Throwable cause) {
    super(String.format("Failed to perform operation on workbook. message: %s", message), cause);
  }

  public KubeOperationException(final String filename, String message, Throwable cause) {
    super(String.format("Failed to perform operation on workbook from %s. message: %s", filename, message), cause);
  }
}
