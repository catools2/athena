package org.catools.athena.model.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The username must be provided.")
  @Size(max = 150, message = "The username can be at most 150 character.")
  private String username;

  private Set<UserAliasDto> aliases = new HashSet<>();

  public UserDto(String username) {
    this.username = username;
  }

  public UserDto(String username, Set<UserAliasDto> aliases) {
    this.username = username;
    this.aliases = aliases;
  }

}
