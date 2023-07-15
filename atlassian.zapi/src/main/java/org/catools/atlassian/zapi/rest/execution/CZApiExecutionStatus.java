package org.catools.atlassian.zapi.rest.execution;

import org.catools.common.collections.CSet;

public enum CZApiExecutionStatus {
  SUCCESS,
  FAILURE,
  WIP,
  SKIP,
  IGNORED,
  DEFERRED,
  BLOCKED,
  AWAITING,
  SUCCESS_PERCENTAGE_FAILURE,
  CREATED;

  public boolean isRunning() {
    return new CSet<>(WIP).contains(this);
  }

  public boolean isFailed() {
    return new CSet<>(FAILURE).contains(this);
  }

  public boolean isSkipped() {
    return new CSet<>(SKIP, IGNORED, DEFERRED, AWAITING).contains(this);
  }

  public boolean isPassed() {
    return new CSet<>(SUCCESS, SUCCESS_PERCENTAGE_FAILURE).contains(this);
  }
}
