package org.catools.athena.kube.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.kube.common.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "container", schema = ATHENA_KUBE_SCHEMA)
@Getter
@Setter
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

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_KUBE_SCHEMA,
      name = "container_metadata_mid",
      joinColumns = {@JoinColumn(name = "container_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<ContainerMetadata> metadata = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Container container = (Container) o;

    return new EqualsBuilder()
        .append(id, container.id)
        .append(type, container.type)
        .append(name, container.name)
        .append(image, container.image)
        .append(imageId, container.imageId)
        .append(ready, container.ready)
        .append(started, container.started)
        .append(restartCount, container.restartCount)
        .append(startedAt, container.startedAt)
        .append(lastSync, container.lastSync)
        .append(pod == null ? null : pod.getId(), container.pod == null ? null : container.pod.getId())
        .append(metadata, container.metadata).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(type)
        .append(name)
        .append(image)
        .append(imageId)
        .append(ready)
        .append(started)
        .append(restartCount)
        .append(startedAt)
        .append(lastSync)
        .append(pod == null ? null : pod.getId())
        .append(metadata)
        .toHashCode();
  }
}
