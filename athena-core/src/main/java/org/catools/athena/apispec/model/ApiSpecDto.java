package org.catools.athena.apispec.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "The api spec first time seen instant must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant firstTimeSeen;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant lastSyncTime;

  @NotEmpty(message = "The api spec path must be provided.")
  private Set<ApiPathDto> paths = new HashSet<>();

  private Set<MetadataDto> metadata = new HashSet<>();
}
