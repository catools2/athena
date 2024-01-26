package org.catools.athena.git.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class GitRepositoryDto implements Serializable {

  private Long id;

  @NotBlank(message = "The git repository name must be provided.")
  @Size(max = 200, message = "The git repository name can be at most 200 character.")
  private String name;

  @NotBlank(message = "The git repository url must be provided.")
  @Size(max = 300, message = "The git repository url can be at most 300 character.")
  private String url;

  private Instant lastSync;

}
