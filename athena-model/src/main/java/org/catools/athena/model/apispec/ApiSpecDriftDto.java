package org.catools.athena.model.apispec;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class ApiSpecDriftDto implements Serializable {

  private Long id;

  private String project;

  private String name;

  private String title;

  private String version;

  private Long pathCount;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant lastSyncTime;

  private Long syncAgeDays;

  private String driftStatus;
}