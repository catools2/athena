package org.catools.athena.pipeline.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.pipeline.common.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;

@Entity
@Table(name = "pipeline", schema = ATHENA_PIPELINE_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "environment_id", nullable = false, referencedColumnName = "id")
  private Environment environment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "version_id", nullable = false, referencedColumnName = "id")
  private AppVersion version;

  @ManyToMany
  @JoinTable(
      schema = ATHENA_PIPELINE_SCHEMA,
      name = "pipeline_metadata_mid",
      joinColumns = {@JoinColumn(name = "pipeline_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<PipelineMetadata> metadata = new HashSet<>();
}
