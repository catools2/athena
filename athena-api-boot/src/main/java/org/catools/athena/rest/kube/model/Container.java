package org.catools.athena.rest.kube.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
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

  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "image", length = 1000, nullable = false)
  private String image;

  @Column(name = "image_id", length = 300, nullable = false)
  private String imageId;

  @Column(name = "ready", nullable = false)
  private Boolean ready;

  @Column(name = "started", nullable = false)
  private Boolean started;

  @Column(name = "restart_count", nullable = false)
  private Integer restartCount;

  @Column(name = "started_at", nullable = false, columnDefinition = "TIMESTAMPTZ")
  private Instant startedAt;

  @ManyToOne
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
