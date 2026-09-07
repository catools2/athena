package org.catools.athena.metric.common.repository;

import java.time.Instant;

public interface MetricInventoryProjection {

  Long getId();

  Long getProjectId();

  Long getEnvironmentId();

  Long getDuration();

  Instant getActionTime();

  String getActionCategory();

  String getActionName();

  String getActionType();

  String getActionTarget();
}