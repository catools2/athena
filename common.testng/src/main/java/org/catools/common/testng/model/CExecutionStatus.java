package org.catools.common.testng.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.collections.CSet;

@AllArgsConstructor
public enum CExecutionStatus {
  SUCCESS(50),
  FAILURE(40),
  WIP(20),
  SKIP(10),
  IGNORED(4),
  DEFERRED(3),
  BLOCKED(2),
  AWAITING(1),
  SUCCESS_PERCENTAGE_FAILURE(51),
  CREATED(0);

  @Getter
  int id;

  public boolean isRunning() {
    return new CSet<>(WIP).contains(this);
  }

  public boolean isFailed() {
    return new CSet<>(FAILURE).contains(this);
  }

  public boolean isSkipped() {
    return new CSet<>(SKIP, IGNORED, DEFERRED).contains(this);
  }

  public boolean isAwaiting() {
    return new CSet<>(AWAITING).contains(this);
  }

  public boolean isBlocked() {
    return new CSet<>(BLOCKED).contains(this);
  }

  public boolean isPassed() {
    return new CSet<>(SUCCESS, SUCCESS_PERCENTAGE_FAILURE).contains(this);
  }
}
