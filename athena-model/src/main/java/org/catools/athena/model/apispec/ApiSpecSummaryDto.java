package org.catools.athena.model.apispec;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class ApiSpecSummaryDto implements Serializable {

  private Long specCount;

  private Long projectCount;

  private Long pathCount;

  private Long staleSpecCount;

  private Integer freshnessWindowDays;

  private Instant latestSyncTime;
}