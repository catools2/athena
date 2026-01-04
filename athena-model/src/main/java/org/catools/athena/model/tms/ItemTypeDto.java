package org.catools.athena.model.tms;

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
public class ItemTypeDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The item type code must be provided.")
  @Size(max = 10, message = "The item type code can be at most 10 character.")
  private String code;

  @NotBlank(message = "The item type name must be provided.")
  @Size(max = 50, message = "The item type name can be at most 50 character.")
  private String name;

  public ItemTypeDto(String code, String name) {
    this.code = code;
    this.name = name;
  }

}
