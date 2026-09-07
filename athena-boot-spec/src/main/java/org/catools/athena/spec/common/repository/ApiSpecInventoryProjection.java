package org.catools.athena.spec.common.repository;

import java.time.Instant;

public interface ApiSpecInventoryProjection {

  Long getId();

  Long getProjectId();

  String getName();

  String getTitle();

  String getVersion();

  Instant getFirstTimeSeen();

  Instant getLastSyncTime();

  Integer getPathCount();
}