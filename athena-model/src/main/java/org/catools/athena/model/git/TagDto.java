package org.catools.athena.model.git;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TagDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The git tag hash must be provided.")
  @Size(max = 50, message = "The git tag hash can be at most 50 character.")
  private String hash;

  @NotBlank(message = "The git tag name must be provided.")
  @Size(max = 200, message = "The git tag name can be at most 500 character.")
  private String name;

  public TagDto(String hash, String name) {
    this.hash = hash;
    this.name = name;
  }

}
