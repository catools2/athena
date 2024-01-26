package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnvironmentDto {

  private Long id;

  @NotBlank(message = "The environment code must be provided.")
  @Size(max = 10, message = "The environment code can be at most 10 character.")
  private String code;

  @NotBlank(message = "The environment name must be provided.")
  @Size(max = 50, message = "The environment name can be at most 50 character.")
  private String name;

  @NotNull(message = "The environment project must be provided.")
  private String project;

}
