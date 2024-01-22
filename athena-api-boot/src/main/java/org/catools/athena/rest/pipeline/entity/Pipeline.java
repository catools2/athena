package org.catools.athena.rest.pipeline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.Environment;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.pipeline.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;

@Entity
@Table(name = "pipeline", schema = ATHENA_PIPELINE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Pipeline implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank(message = "The pipeline name must be provided.")
  @Size(max = 100, message = "The pipeline name can be at most 100 character.")
  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @NotBlank(message = "The pipeline description must be provided.")
  @Size(max = 100, message = "The pipeline description can be at most 300 character.")
  @Column(name = "description", length = 300, nullable = false)
  private String description;

  @NotBlank(message = "The pipeline number must be provided.")
  @Size(max = 100, message = "The pipeline number can be at most 100 character.")
  @Column(name = "number", length = 100, nullable = false)
  private String number;

  @NotNull(message = "The pipeline startInstant must be provided.")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "start_date", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startInstant;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "end_date", columnDefinition = "TIMESTAMPTZ")
  private Instant endInstant;

  @NotNull(message = "The pipeline environment must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "environment_code", nullable = false, referencedColumnName = "id")
  private Environment environment;

  @ManyToMany(
      cascade = CascadeType.MERGE,
      fetch = FetchType.EAGER,
      targetEntity = PipelineMetadata.class)
  @JoinTable(
      schema = ATHENA_PIPELINE_SCHEMA,
      name = "pipeline_metadata_mid",
      joinColumns = {@JoinColumn(name = "pipeline_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<PipelineMetadata> metadata = new HashSet<>();
}
