package org.catools.athena.pipeline.model;

import org.catools.athena.core.model.MetadataDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PipelineScenarioExecutionDto implements Serializable {

  private Long id;

  @NotBlank
  private String feature;

  @NotBlank
  private String scenario;

  @NotNull
  private Date startTime;

  @NotNull
  private Date endTime;

  @NotNull
  private Date beforeScenarioStartTime;

  @NotNull
  private Date beforeScenarioEndTime;

  private String status;

  private String executor;

  private Long pipelineId;

  private List<MetadataDto> metadata = new ArrayList<>();
}
