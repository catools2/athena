package org.catools.athena.model.kube;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class PodSummaryDto implements Serializable {

  private Long totalCount;

  private Long namespaceCount;

  private Long nodeCount;

  private Long activeCount;

  private Long deletedCount;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant latestSyncTime;
}