package org.catools.athena.spec.common.repository;

import java.time.Instant;

public interface ApiSpecSummaryProjection {

  Long getSpecCount();

  Long getProjectCount();

  Long getPathCount();

  Instant getLatestSyncTime();
}