package org.catools.athena.apispec.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ApiSpecDto implements Serializable {

  private Long id;

  @NotBlank(message = "The api spec version must be provided.")
  @Size(max = 10, message = "The api spec version can be at most 10 character.")
  private String version;

  @NotBlank(message = "The api spec name must be provided.")
  @Size(max = 100, message = "The api spec name can be at most 100 character.")
  private String name;

  @NotBlank(message = "The api spec title must be provided.")
  @Size(max = 100, message = "The api spec title can be at most 100 character.")
  private String title;

  @NotBlank(message = "The api spec project must be provided.")
  private String project;

  @NotBlank(message = "The api spec first time seen instant must be provided.")
  private Instant firstTimeSeen;

  private Instant lastTimeSeen;

  private Set<MetadataDto> metadata = new HashSet<>();

}
