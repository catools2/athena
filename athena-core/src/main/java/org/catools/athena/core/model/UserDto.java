package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto implements Serializable {
  private Long id;

  @NotBlank
  @Size(max = 300)
  private String name;
}
