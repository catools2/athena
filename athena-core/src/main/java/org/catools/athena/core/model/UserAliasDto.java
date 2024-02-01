package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserAliasDto implements Serializable {

  private Long id;

  @NotBlank(message = "The path metadata name must be provided.")
  @Size(max = 200, message = "The path metadata name can be at most 100 character.")
  private String alias;

  @NotNull(message = "The alias user must be provided.")
  private String user;

}
