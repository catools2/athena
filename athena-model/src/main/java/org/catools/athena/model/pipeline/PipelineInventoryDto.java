package org.catools.athena.model.pipeline;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class PipelineInventoryDto implements Serializable {

  private Long id;

  private String project;

  private String version;

  private String environment;

  private String name;

  private String description;

  private String number;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant startDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant endDate;

  private Long duration;

  private String state;
}