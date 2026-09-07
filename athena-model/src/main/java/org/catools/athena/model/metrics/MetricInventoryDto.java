package org.catools.athena.model.metrics;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class MetricInventoryDto implements Serializable {

  private Long id;

  private String project;

  private String environment;

  private Long duration;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant actionTime;

  private String actionCategory;

  private String actionName;

  private String actionType;

  private String actionTarget;
}