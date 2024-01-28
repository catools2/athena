package org.catools.athena.tms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;


@Data
@Accessors(chain = true)
public class TestExecutionDto implements Serializable {

  private Long id;

  @NotNull(message = "The execution created date/time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant createdOn;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant executedOn;

  @NotNull(message = "The execution cycle must be provided.")
  private String cycle;

  @NotNull(message = "The execution item must be provided.")
  private String item;

  @NotNull(message = "The item status must be provided.")
  private String status;

  @NotNull(message = "The execution version must be provided.")
  private String executor;

}
