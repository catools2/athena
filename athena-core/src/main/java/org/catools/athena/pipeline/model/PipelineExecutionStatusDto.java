package org.catools.athena.pipeline.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PipelineExecutionStatusDto implements Serializable {

  private Long id;

  @NotBlank(message = "The pipeline execution status must be provided.")
  @Size(max = 100, message = "The pipeline execution status can be at most 100 character.")
  private String name;

}
