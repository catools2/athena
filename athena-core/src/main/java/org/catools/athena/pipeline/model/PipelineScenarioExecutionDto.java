package org.catools.athena.pipeline.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Data
@Accessors(chain = true)
public class PipelineScenarioExecutionDto implements Serializable {

  private Long id;

  @NotBlank(message = "The pipeline scenario feature must be provided.")
  @Size(max = 1000, message = "The pipeline scenario feature can be at most 1000 character.")
  private String feature;

  @NotBlank(message = "The pipeline scenario scenario must be provided.")
  @Size(max = 500, message = "The pipeline scenario scenario can be at most 500 character.")
  private String scenario;

  @NotBlank(message = "The pipeline scenario parameters must be provided.")
  @Size(max = 2000, message = "The pipeline scenario parameters can be at most 2000 character.")
  private String parameters;

  @NotNull(message = "The pipeline scenario start time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant startTime;

  @NotNull(message = "The pipeline scenario end time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant endTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant beforeScenarioStartTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant beforeScenarioEndTime;

  @NotNull(message = "The pipeline scenario status must be provided.")
  private String status;

  @NotNull(message = "The pipeline scenario executor must be provided.")
  private String executor;

  @NotNull(message = "The pipeline scenario pipeline must be provided.")
  private Long pipelineId;

  private Set<MetadataDto> metadata = new HashSet<>();

}
