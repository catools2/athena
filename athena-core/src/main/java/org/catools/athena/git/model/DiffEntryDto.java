package org.catools.athena.git.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class DiffEntryDto implements Serializable {

  private Long id;

  @NotBlank(message = "The diff entry old path must be provided.")
  @Size(max = 1000, message = "The diff entry old path can be at most 1000 character.")
  private String oldPath;

  @NotBlank(message = "The diff entry new path must be provided.")
  @Size(max = 1000, message = "The diff entry new path can be at most 1000 character.")
  private String newPath;

  @NotNull(message = "The diff entry inserted must be provided.")
  private Integer inserted;

  @NotNull(message = "The diff entry deleted must be provided.")
  private Integer deleted;

  @NotBlank(message = "The diff entry change type must be provided.")
  @Size(max = 30, message = "The diff entry change type can be at most 30 character.")
  private String changeType;

}
