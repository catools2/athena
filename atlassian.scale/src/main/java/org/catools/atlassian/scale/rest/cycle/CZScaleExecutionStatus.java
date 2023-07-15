package org.catools.atlassian.scale.rest.cycle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.collections.CSet;

public enum CZScaleExecutionStatus {
  UNSET(null),
  IN_PROGRESS("In Progress"),
  FAIL("Fail"),
  BLOCKED("Blocked"),
  PASS("Pass"),
  NOT_EXECUTED("Not Executed");

  private final String scaleName;

  CZScaleExecutionStatus(String scaleName) {
    this.scaleName = scaleName;
  }

  @JsonCreator
  public static CZScaleExecutionStatus formScaleName(String value) {
    return CSet.of(values()).getFirstOrNull(s -> StringUtils.equalsIgnoreCase(s.getScaleName(), value));
  }

  @JsonValue
  public String getScaleName() {
    return scaleName;
  }
}