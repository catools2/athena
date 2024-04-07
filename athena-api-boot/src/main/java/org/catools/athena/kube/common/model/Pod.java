package org.catools.athena.kube.common.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.Project;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.kube.common.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "pod", schema = ATHENA_KUBE_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class Pod implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "uid", length = 36, unique = true)
  private String uid;

  @Column(name = "name", length = 500, unique = true)
  private String name;

  @Column(name = "namespace", length = 100)
  private String namespace;

  @Column(name = "hostname", length = 200)
  private String hostname;

  @Column(name = "nodeName", length = 200)
  private String nodeName;

  @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ")
  private Instant createdAt;

  @Column(name = "deleted_at", columnDefinition = "TIMESTAMPTZ")
  private Instant deletedAt;

  @Column(name = "last_sync", nullable = false, columnDefinition = "TIMESTAMPTZ")
  private Instant lastSync;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private PodStatus status;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_KUBE_SCHEMA,
      name = "pod_metadata_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<PodMetadata> metadata = new HashSet<>();

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_KUBE_SCHEMA,
      name = "pod_annotation_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "annotation_id")})
  private Set<PodAnnotation> annotations = new HashSet<>();

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_KUBE_SCHEMA,
      name = "pod_label_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "label_id")})
  private Set<PodLabel> labels = new HashSet<>();

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_KUBE_SCHEMA,
      name = "pod_selector_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "selector_id")})
  private Set<PodSelector> selectors = new HashSet<>();

  @OneToMany(mappedBy = "pod", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<Container> containers = new HashSet<>();

  public void setContainers(Set<Container> containers) {
    if (containers == null) {
      this.containers.forEach(this::removeContainer);
      return;
    }
    this.containers.clear();
    containers.forEach(this::addContainer);
  }

  public void removeContainer(Container container) {
    if (container == null) return;
    this.containers.remove(container.setPod(null));
  }

  public void addContainer(Container container) {
    if (container == null) return;
    this.containers.add(container.setPod(this));
  }

}
