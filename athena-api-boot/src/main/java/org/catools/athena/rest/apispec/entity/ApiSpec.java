package org.catools.athena.rest.apispec.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.Project;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.apispec.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;

@Entity
@Table(name = "api_spec", schema = ATHENA_OPENAPI_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiSpec implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "version", length = 10, nullable = false)
  private String version;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "title", length = 100, nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;

  @Column(name = "first_time_seen", columnDefinition = "TIMESTAMPTZ")
  private Instant firstTimeSeen;

  @Column(name = "last_sync_time", columnDefinition = "TIMESTAMPTZ")
  private Instant lastSyncTime;

  @OneToMany(mappedBy = "spec", orphanRemoval = true)
  private Set<ApiPath> paths = new HashSet<>();

  @ManyToMany(
      cascade = CascadeType.REMOVE,
      fetch = FetchType.EAGER,
      targetEntity = ApiSpecMetadata.class)
  @JoinTable(
      schema = ATHENA_OPENAPI_SCHEMA,
      name = "api_spec_metadata_mid",
      joinColumns = {@JoinColumn(name = "spec_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<ApiSpecMetadata> metadata = new HashSet<>();

  public void setPaths(Set<ApiPath> paths) {
    if (paths == null) return;
    this.paths = paths;
    this.paths.forEach(p -> p.setSpec(this));
  }
}
