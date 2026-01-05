package org.catools.athena.model.pipeline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PipelineExecutionStatusDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The pipeline execution status must be provided.")
  @Size(max = 100, message = "The pipeline execution status can be at most 100 character.")
  private String name;

  public PipelineExecutionStatusDto(String name) {
    this.name = name;
  }

}
