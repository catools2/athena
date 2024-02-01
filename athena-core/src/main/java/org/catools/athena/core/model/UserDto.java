package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class UserDto implements Serializable {

  private Long id;

  @NotBlank(message = "The username must be provided.")
  @Size(max = 150, message = "The username can be at most 150 character.")
  private String username;

  private Set<UserAliasDto> aliases = new HashSet<>();

}
