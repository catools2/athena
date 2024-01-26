package org.catools.athena.apispec.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class ApiParameterDto implements NameTypePair, Serializable {

  private Long id;

  @NotBlank(message = "The metadata name must be provided.")
  @Size(max = 100, message = "The metadata name can be at most 100 character.")
  private String name;

  @NotBlank(message = "The metadata value must be provided.")
  @Size(max = 2000, message = "The metadata value can be at most 2000 character.")
  private String type;

}
