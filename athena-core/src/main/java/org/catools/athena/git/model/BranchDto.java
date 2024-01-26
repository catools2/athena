package org.catools.athena.git.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BranchDto implements Serializable {

  private Long id;

  @NotBlank(message = "The git branch hash must be provided.")
  @Size(max = 50, message = "The git branch hash can be at most 50 character.")
  private String hash;

  @NotBlank(message = "The git branch name must be provided.")
  @Size(max = 500, message = "The git branch name can be at most 500 character.")
  private String name;

}
