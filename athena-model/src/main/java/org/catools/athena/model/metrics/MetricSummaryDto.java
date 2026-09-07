package org.catools.athena.model.metrics;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class MetricSummaryDto implements Serializable {

  private Long totalCount;

  private Long uniqueActionCount;

  private Double averageDuration;

  private Long slowestDuration;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant latestActionTime;
}