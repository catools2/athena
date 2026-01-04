package org.catools.athena.model.kube;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PodDto {
  @NotNull(groups = IdRequired.class, message = "The id must be provided.")
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

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant deletedAt;

  @NotNull(message = "The container lastSync must be provided.")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Instant lastSync;

  @NotNull(message = "The pod status must be provided.")
  private PodStatusDto status;

  @NotNull(message = "The pod project must be provided.")
  private String project;

  private Set<ContainerDto> containers = new HashSet<>();

  private Set<MetadataDto> metadata = new HashSet<>();

  private Set<MetadataDto> annotations = new HashSet<>();

  private Set<MetadataDto> selectors = new HashSet<>();

  private Set<MetadataDto> labels = new HashSet<>();

  public PodDto(String uid,
                String name,
                String namespace,
                String hostname,
                String nodeName,
                Instant createdAt,
                Instant deletedAt,
                Instant lastSync,
                PodStatusDto status,
                String project) {
    this.uid = uid;
    this.name = name;
    this.namespace = namespace;
    this.hostname = hostname;
    this.nodeName = nodeName;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.lastSync = lastSync;
    this.status = status;
    this.project = project;
  }
}
