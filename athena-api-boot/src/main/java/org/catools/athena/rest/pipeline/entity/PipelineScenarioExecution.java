package org.catools.athena.rest.pipeline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.pipeline.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;


@Entity
@Table(name = "scenario_execution", schema = ATHENA_PIPELINE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PipelineScenarioExecution implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank(message = "The pipeline scenario feature must be provided.")
  @Size(max = 1000, message = "The pipeline scenario feature can be at most 1000 character.")
  @Column(name = "feature", length = 1000, nullable = false)
  private String feature;

  @NotBlank(message = "The pipeline scenario scenario must be provided.")
  @Size(max = 500, message = "The pipeline scenario scenario can be at most 500 character.")
  @Column(name = "scenario", length = 500, nullable = false)
  private String scenario;

  @NotBlank(message = "The pipeline scenario parameters must be provided.")
  @Size(max = 2000, message = "The pipeline scenario parameters can be at most 2000 character.")
  @Column(name = "parameters", length = 2000)
  private String parameters;

  @NotNull(message = "The pipeline scenario start time must be provided.")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "startTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startTime;

  @NotNull(message = "The pipeline scenario end time must be provided.")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "endTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant endTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeScenarioStartTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeScenarioStartTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeScenarioEndTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeScenarioEndTime;

  @NotNull(message = "The pipeline scenario status must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private PipelineExecutionStatus status;

  @NotNull(message = "The pipeline scenario executor must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "executor_id", nullable = false, referencedColumnName = "id")
  private User executor;

  @NotNull(message = "The pipeline scenario pipeline must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "pipeline_id", nullable = false, referencedColumnName = "id")
  private Pipeline pipeline;

  @ManyToMany(
      cascade = CascadeType.MERGE,
      fetch = FetchType.EAGER,
      targetEntity = PipelineExecutionMetadata.class)
  @JoinTable(
      schema = ATHENA_PIPELINE_SCHEMA,
      name = "scenario_metadata_mid",
      joinColumns = {@JoinColumn(name = "execution_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<PipelineExecutionMetadata> metadata = new HashSet<>();
}
