package org.catools.athena.kube.common.model;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "pod")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class Pod implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "project_id", nullable = false)
  private Long projectId;

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

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private PodStatus status;

  @ManyToMany
  @JoinTable(
      name = "pod_metadata_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<PodMetadata> metadata = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "pod_annotation_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "annotation_id")})
  private Set<PodAnnotation> annotations = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "pod_label_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "label_id")})
  private Set<PodLabel> labels = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "pod_selector_mid",
      joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "selector_id")})
  private Set<PodSelector> selectors = new HashSet<>();

  @OneToMany(mappedBy = "pod", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
