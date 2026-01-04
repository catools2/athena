package org.catools.athena.model.pipeline;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;
import org.catools.athena.model.core.MetadataDto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class PipelineDto {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The pipeline name must be provided.")
  @Size(max = 100, message = "The pipeline name can be at most 100 character.")
  private String name;

  @NotBlank(message = "The pipeline description must be provided.")
  @Size(max = 100, message = "The pipeline description can be at most 300 character.")
  private String description;

  @NotBlank(message = "The pipeline number must be provided.")
  @Size(max = 100, message = "The pipeline number can be at most 100 character.")
  private String number;

  @NotNull(message = "The pipeline startInstant must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant startDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant endDate;

  @NotNull(message = "The pipeline project must be provided.")
  private String project;

  @NotNull(message = "The pipeline environment must be provided.")
  private String environment;

  @NotNull(message = "The pipeline version must be provided.")
  private String version;

  private Set<MetadataDto> metadata = new HashSet<>();

}
