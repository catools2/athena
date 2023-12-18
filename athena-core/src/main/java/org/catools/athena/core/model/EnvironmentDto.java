package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class EnvironmentDto {
  private Long id;

  @NotBlank
  @Size(max = 5)
  private String code;

  @NotBlank
  @Size(max = 50)
  private String name;

  @NotBlank
  @Size(min = 1, max = 5)
  private String projectCode;
}
