package org.catools.athena.pipeline.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "pipeline")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class Pipeline implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "description", length = 300, nullable = false)
  private String description;

  @Column(name = "number", length = 100, nullable = false)
  private String number;

  @Column(name = "start_date", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startDate;

  @Column(name = "end_date", columnDefinition = "TIMESTAMPTZ")
  private Instant endDate;

  @Column(name = "environment_id", nullable = false)
  private Long environmentId;

  @Column(name = "version_id", nullable = false)
  private Long versionId;

  @ManyToMany
  @JoinTable(
      name = "pipeline_metadata_mid",
      joinColumns = {@JoinColumn(name = "pipeline_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<PipelineMetadata> metadata = new HashSet<>();
}
