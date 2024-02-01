package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProjectDto {

  private Long id;

  @NotBlank(message = "The project code must be provided.")
  @Size(max = 10, message = "The project code can be at most 10 character.")
  private String code;

  @NotBlank(message = "The project name must be provided.")
  @Size(max = 50, message = "The project name can be at most 50 character.")
  private String name;

}
