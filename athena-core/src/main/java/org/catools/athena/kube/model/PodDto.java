package org.catools.athena.kube.model;

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
public class PodDto {
  private Long id;

  @Size(max = 36, message = "The pod uid can be at most 36 character.")
  private String uid;

  @Size(max = 500, message = "The pod name can be at most 500 character.")
  private String name;

  @Size(max = 100, message = "The pod namespace can be at most 100 character.")
  private String namespace;

  @Size(max = 200, message = "The pod hostname can be at most 200 character.")
  private String hostname;

  @Size(max = 200, message = "The pod node name can be at most 200 character.")
  private String nodeName;

  @NotNull(message = "The pod created at must be provided.")
  private Instant createdAt;

  @NotNull(message = "The pod deleted at must be provided.")
  private Instant deletedAt;

  @NotNull(message = "The pod status must be provided.")
  private PodStatusDto status;

  @NotNull(message = "The pod project must be provided.")
  private String project;

  private Set<MetadataDto> metadata = new HashSet<>();

  private Set<MetadataDto> annotations = new HashSet<>();

  private Set<MetadataDto> selectors = new HashSet<>();

  private Set<MetadataDto> labels = new HashSet<>();

}
