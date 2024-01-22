package org.catools.athena.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PipelineExecutionDto implements Serializable {

  private Long id;

  private String packageName;

  private String className;

  private String methodName;

  private String parameters;

  private Instant startTime;

  private Instant endTime;

  private Instant testStartTime;

  private Instant testEndTime;

  private Instant beforeClassStartTime;

  private Instant beforeClassEndTime;

  private Instant beforeMethodStartTime;

  private Instant beforeMethodEndTime;

  private String status;

  private String executor;

  private Long pipelineId;

  private Set<MetadataDto> metadata = new HashSet<>();
}
