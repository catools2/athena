package org.catools.athena.apispec.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ApiPathDto implements Serializable {

  private Long id;

  @NotNull(message = "The path api spec id must be provided.")
  private Long apiSpecId;

  @NotBlank(message = "The path method must be provided.")
  @Size(max = 10, message = "The path method can be at most 10 character.")
  private String method;

  @NotBlank(message = "The path title must be provided.")
  @Size(max = 1000, message = "The path title can be at most 1000 character.")
  private String title;

  @Size(max = 5000, message = "The path description can be at most 1000 character.")
  private String description;

  @NotBlank(message = "The path url must be provided.")
  @Size(max = 500, message = "The path url can be at most 500 character.")
  private String url;

  private Set<ApiParameterDto> parameters = new HashSet<>();
  private Set<MetadataDto> metadata = new HashSet<>();
}
