package org.catools.athena.model.git;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class GitRepositorySummaryDto implements Serializable {

  private Long totalCount;

  private Long hostCount;

  private Long freshCount;

  private Long agingCount;

  private Long staleCount;

  private Long unknownSyncCount;

  private Integer warningWindowDays;

  private Integer freshnessWindowDays;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant latestSyncTime;
}