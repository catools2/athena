package org.catools.athena.rest.kube.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.Project;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.kube.config.KubeConstant.ATHENA_KUBE_SCHEMA;


@Entity
@Table(name = "pod", schema = ATHENA_KUBE_SCHEMA)
@Data
@Accessors(chain = true)
public class Pod implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 36, message = "The pod uid can be at most 36 character.")
  @Column(name = "uid", length = 36, unique = true)
  private String uid;

  @Size(max = 500, message = "The pod name can be at most 500 character.")
  @Column(name = "name", length = 500, unique = true)
  private String name;

  @Size(max = 100, message = "The pod namespace can be at most 100 character.")
  @Column(name = "namespace", length = 100)
  private String namespace;

  @Size(max = 200, message = "The pod hostname can be at most 200 character.")
  @Column(name = "hostname", length = 200)
  private String hostname;

  @Size(max = 200, message = "The pod node name can be at most 200 character.")
  @Column(name = "nodeName", length = 200)
  private String nodeName;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ")
  private Instant createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "deleted_at", columnDefinition = "TIMESTAMPTZ")
  private Instant deletedAt;

  @NotNull(message = "The pod status must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private PodStatus status;

  @NotNull(message = "The pod project must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
          schema = ATHENA_KUBE_SCHEMA,
          name = "pod_metadata_mid",
          joinColumns = {@JoinColumn(name = "pod_id", referencedColumnName = "name")},
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
}
