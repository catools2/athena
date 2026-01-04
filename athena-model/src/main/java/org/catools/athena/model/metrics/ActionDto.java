package org.catools.athena.model.metrics;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

@Data
@Accessors(chain = true)
public class ActionDto {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The action name must be provided.")
  private String name;

  @NotBlank(message = "The action category must be provided.")
  private String category;

  @NotBlank(message = "The action target must be provided.")
  private String target;

  @NotBlank(message = "The action target must be provided.")
  private String type;

  @NotBlank(message = "The action command must be provided.")
  private String command;

  private String parameter;

}
