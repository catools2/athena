package org.catools.athena.model.tms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class TestExecutionInventoryDto implements Serializable {

  private Long id;

  private String cycleCode;

  private String cycleName;

  private String project;

  private String version;

  private String item;

  private String status;

  private String executor;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant createdOn;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant executedOn;
}