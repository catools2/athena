package org.catools.athena.model.git;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GitRepositoryDto implements Serializable {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The git repository name must be provided.")
  @Size(max = 200, message = "The git repository name can be at most 200 character.")
  private String name;

  @NotBlank(message = "The git repository url must be provided.")
  @Size(max = 300, message = "The git repository url can be at most 300 character.")
  private String url;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant lastSync;

  public GitRepositoryDto(String name, String url, Instant lastSync) {
    this.name = name;
    this.url = url;
    this.lastSync = lastSync;
  }

}
