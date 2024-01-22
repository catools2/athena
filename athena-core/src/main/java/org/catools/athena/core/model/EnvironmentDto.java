package org.catools.athena.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class EnvironmentDto {
  private Long id;

  private String code;

  private String name;

  private String project;
}
