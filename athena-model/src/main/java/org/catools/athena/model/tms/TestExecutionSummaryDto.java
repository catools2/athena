package org.catools.athena.model.tms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class TestExecutionSummaryDto implements Serializable {

  private Long totalCount;

  private Long cycleCount;

  private Long itemCount;

  private Long executedCount;

  private Long pendingCount;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant latestActivityTime;

  private List<TestExecutionStatusCountDto> statusBreakdown = new ArrayList<>();
}