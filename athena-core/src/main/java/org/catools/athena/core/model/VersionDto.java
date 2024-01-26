package org.catools.athena.core.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class VersionDto implements Serializable {

  private Long id;

  @NotBlank(message = "The version code must be provided.")
  @Size(max = 10, message = "The version code can be at most 10 character.")
  private String code;

  @NotBlank(message = "The version name must be provided.")
  @Size(max = 50, message = "The version name can be at most 50 character.")
  private String name;

  @NotNull(message = "The version project must be provided.")
  private String project;

}
