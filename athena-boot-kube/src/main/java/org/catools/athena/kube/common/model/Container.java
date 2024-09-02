package org.catools.athena.kube.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.kube.common.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "container", schema = ATHENA_KUBE_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "pod"})
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

  @Column(name = "ready")
  private Boolean ready;

  @Column(name = "started")
  private Boolean started;

  @Column(name = "restart_count")
  private Integer restartCount;

  @Column(name = "started_at", columnDefinition = "TIMESTAMPTZ")
  private Instant startedAt;

  @Column(name = "last_sync", nullable = false, columnDefinition = "TIMESTAMPTZ")
  private Instant lastSync;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pod_id", nullable = false, referencedColumnName = "id")
  private Pod pod;

  @ManyToMany
  @JoinTable(
      schema = ATHENA_KUBE_SCHEMA,
      name = "container_metadata_mid",
      joinColumns = {@JoinColumn(name = "container_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<ContainerMetadata> metadata = new HashSet<>();

}
