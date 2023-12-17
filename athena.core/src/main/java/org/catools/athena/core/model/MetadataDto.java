package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MetadataDto {
  private Long id;

  @NotBlank
  @Size(max = 100)
  private String name;

  @NotBlank
  @Size(max = 2000)
  private String value;
}
