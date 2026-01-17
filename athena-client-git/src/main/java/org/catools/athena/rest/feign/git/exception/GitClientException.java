package org.catools.athena.rest.feign.git.exception;


public class GitClientException extends RuntimeException {

  public GitClientException(final String message, Throwable t) {
    super(message, t);
  }

}
