package org.catools.athena.model.pipeline;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class PipelineSummaryDto implements Serializable {

  private Long totalCount;

  private Long uniqueNameCount;

  private Long completedCount;

  private Long inProgressCount;

  private Double averageDuration;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant latestStartTime;
}