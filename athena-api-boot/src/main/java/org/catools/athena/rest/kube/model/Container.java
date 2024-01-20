package org.catools.athena.rest.kube.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.kube.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "container", schema = ATHENA_KUBE_SCHEMA)
@Data
@Accessors(chain = true)
public class Container implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "The container type must be provided.")
  @Size(max = 100, message = "The container type can be at most 100 character.")
  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @NotBlank(message = "The container name must be provided.")
  @Size(max = 300, message = "The container name can be at most 300 character.")
  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @NotBlank(message = "The container image must be provided.")
  @Size(max = 1000, message = "The container image can be at most 1000 character.")
  @Column(name = "image", length = 1000, nullable = false)
  private String image;

  @NotBlank(message = "The container image id must be provided.")
  @Size(max = 300, message = "The container image id can be at most 300 character.")
  @Column(name = "image_id", length = 300, nullable = false)
  private String imageId;

  @Column(name = "ready")
  private Boolean ready;

  @Column(name = "started")
  private Boolean started;

  @Column(name = "restart_count")
  private Integer restartCount;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @NotNull(message = "The container pod must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "pod_id", nullable = false, referencedColumnName = "id")
  private Pod pod;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
          schema = ATHENA_KUBE_SCHEMA,
          name = "container_metadata_mid",
          joinColumns = {@JoinColumn(name = "container_id")},
          inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<ContainerMetadata> metadata = new HashSet<>();
}
