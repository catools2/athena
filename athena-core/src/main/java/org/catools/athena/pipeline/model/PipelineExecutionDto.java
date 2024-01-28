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
public class PipelineExecutionDto implements Serializable {

  private Long id;

  @NotBlank(message = "The pipeline execution package name must be provided.")
  @Size(max = 300, message = "The pipeline execution package name can be at most 300 character.")
  private String packageName;

  @NotBlank(message = "The pipeline execution class name must be provided.")
  @Size(max = 300, message = "The pipeline execution class name can be at most 300 character.")
  private String className;

  @NotBlank(message = "The pipeline execution method name must be provided.")
  @Size(max = 300, message = "The pipeline execution method name can be at most 300 character.")
  private String methodName;

  @Size(max = 300, message = "The pipeline execution parameters can be at most 2000 character.")
  private String parameters;

  @NotNull(message = "The pipeline execution start time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant startTime;

  @NotNull(message = "The pipeline execution end time must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant endTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant testStartTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant testEndTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant beforeClassStartTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant beforeClassEndTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant beforeMethodStartTime;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant beforeMethodEndTime;

  @NotNull(message = "The pipeline execution status must be provided.")
  private String status;

  @NotNull(message = "The pipeline execution executor must be provided.")
  private String executor;

  @NotNull(message = "The pipeline execution pipeline must be provided.")
  private Long pipelineId;

  private Set<MetadataDto> metadata = new HashSet<>();

}
