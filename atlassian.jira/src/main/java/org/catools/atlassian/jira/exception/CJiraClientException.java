package org.catools.atlassian.jira.exception;

import org.catools.common.exception.CRuntimeException;

public class CJiraClientException extends CRuntimeException {

  public CJiraClientException(String message, Throwable t) {
    super(message, t);
  }
}
