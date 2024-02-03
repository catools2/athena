package org.catools.athena.apispec.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ApiPathDto implements Serializable {

  private Long id;

  @NotNull(message = "The path api spec id must be provided.")
  private Long specId;

  @NotBlank(message = "The path method must be provided.")
  @Size(max = 10, message = "The path method can be at most 10 character.")
  private String method;

  @Size(max = 1000, message = "The path title can be at most 1000 character.")
  private String title;

  @Size(max = 5000, message = "The path description can be at most 1000 character.")
  private String description;

  @NotNull(message = "The api path first time seen instant must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant firstTimeSeen;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant lastSyncTime;

  @NotBlank(message = "The path url must be provided.")
  @Size(max = 500, message = "The path url can be at most 500 character.")
  private String url;

  private Map<String, String> parameters = new HashMap<>();

  private Set<MetadataDto> metadata = new HashSet<>();
}
