package org.catools.athena.kube.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ContainerDto {

  private Long id;

  @NotNull(message = "The container pod must be provided.")
  private Long podId;

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

  @NotNull(message = "The container started at must be provided.")
  private Instant startedAt;

  private Set<MetadataDto> metadata = new HashSet<>();

}
