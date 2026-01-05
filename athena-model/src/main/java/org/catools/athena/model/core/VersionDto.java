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


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VersionDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The version code must be provided.")
  @Size(max = 10, message = "The version code can be at most 10 character.")
  private String code;

  @NotBlank(message = "The version name must be provided.")
  @Size(max = 50, message = "The version name can be at most 50 character.")
  private String name;

  @NotNull(message = "The version project must be provided.")
  private String project;

  public VersionDto(String code, String name, String project) {
    this.code = code;
    this.name = name;
    this.project = project;
  }

}
