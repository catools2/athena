package org.catools.athena.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PipelineExecutionStatusDto implements Serializable {
  private Long id;

  private String name;
}
