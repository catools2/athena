package org.catools.athena.apispec.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class OpenAPIDto {

  @NotNull(message = "The open api name must be provided.")
  private String name;

  @NotNull(message = "The open api project code must be provided.")
  private String projectCode;

  @NotNull(message = "The open api spec must be provided.")
  private ApiSpecDto apiSpec;

  @NotNull(message = "The path api spec id must be provided.")
  private Set<ApiPathDto> savedApiPaths = new HashSet<>();

}
