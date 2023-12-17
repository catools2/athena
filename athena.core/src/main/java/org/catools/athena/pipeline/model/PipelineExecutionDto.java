package org.catools.athena.pipeline.model;

import org.catools.athena.core.model.MetadataDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PipelineExecutionDto implements Serializable {

  private Long id;

  @NotBlank
  @Size(max = 300)
  private String packageName;

  @NotBlank
  @Size(max = 100)
  private String className;

  @NotBlank
  @Size(max = 100)
  private String methodName;

  @NotNull
  private Date startTime;

  @NotNull
  private Date endTime;

  @NotNull
  private Date testStartTime;

  @NotNull
  private Date testEndTime;

  @NotNull
  private Date beforeClassStartTime;

  @NotNull
  private Date beforeClassEndTime;

  @NotNull
  private Date beforeMethodStartTime;

  @NotNull
  private Date beforeMethodEndTime;

  private String status;

  private String executor;

  private Long pipelineId;

  private List<MetadataDto> metadata = new ArrayList<>();
}
