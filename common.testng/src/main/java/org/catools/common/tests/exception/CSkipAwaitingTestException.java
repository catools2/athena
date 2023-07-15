package org.catools.common.tests.exception;

import org.catools.common.exception.CRuntimeException;

/**
 * Signals that issue class contains a issue awaiting some action and due to the test configuration
 * it should be skipped.
 */
public class CSkipAwaitingTestException extends CRuntimeException {

  public CSkipAwaitingTestException(String description) {
    super(description);
  }
}
