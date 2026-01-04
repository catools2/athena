package org.catools.athena.model.kube;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.common.markers.IdRequired;
import org.catools.athena.model.core.MetadataDto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ContainerDto {

  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
  private Long id;

  @NotBlank(message = "The container type must be provided.")
  @Size(max = 100, message = "The container type can be at most 100 character.")
  private String type;

  @NotBlank(message = "The container name must be provided.")
  @Size(max = 300, message = "The container name can be at most 300 character.")
  private String name;

  @NotBlank(message = "The container image must be provided.")
  @Size(max = 1000, message = "The container image can be at most 1000 character.")
  private String image;

  @NotBlank(message = "The container image id must be provided.")
  @Size(max = 300, message = "The container image id can be at most 300 character.")
  private String imageId;

  @NotNull(message = "The container ready value must be provided.")
  private Boolean ready;

  @NotNull(message = "The container started value must be provided.")
  private Boolean started;

  @NotNull(message = "The container restart count be provided.")
  private Integer restartCount;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant startedAt;

  @NotNull(message = "The container lastSync must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant lastSync;

  private Set<MetadataDto> metadata = new HashSet<>();

  public ContainerDto(String type,
                      String name,
                      String image,
                      String imageId,
                      Boolean ready,
                      Boolean started,
                      Integer restartCount,
                      Instant startedAt,
                      Instant lastSync) {
    this.type = type;
    this.name = name;
    this.image = image;
    this.imageId = imageId;
    this.ready = ready;
    this.started = started;
    this.restartCount = restartCount;
    this.startedAt = startedAt;
    this.lastSync = lastSync;
  }
}
