package org.catools.athena.model.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MetadataDto implements NameValuePair {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The metadata name must be provided.")
  private String name;

  @NotBlank(message = "The metadata value must be provided.")
  private String value;

  public MetadataDto(String name, String value) {
    this.name = name;
    this.value = value;
  }

}
