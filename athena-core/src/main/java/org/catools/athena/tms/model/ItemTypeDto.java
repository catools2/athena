package org.catools.athena.tms.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class ItemTypeDto implements Serializable {

  private Long id;

  @NotBlank(message = "The item type code must be provided.")
  @Size(max = 10, message = "The item type code can be at most 10 character.")
  private String code;

  @NotBlank(message = "The item type name must be provided.")
  @Size(max = 50, message = "The item type name can be at most 50 character.")
  private String name;

}
