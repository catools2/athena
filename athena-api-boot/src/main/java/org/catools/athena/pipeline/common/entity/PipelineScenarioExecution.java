package org.catools.athena.pipeline.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.pipeline.common.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;


@Entity
@Table(name = "scenario_execution", schema = ATHENA_PIPELINE_SCHEMA)
@Data
@Accessors(chain = true)
public class PipelineScenarioExecution implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "feature", length = 1000, nullable = false)
  private String feature;

  @Column(name = "scenario", length = 500, nullable = false)
  private String scenario;

  @Column(name = "parameters", length = 2000)
  private String parameters;

  @Column(name = "startTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startTime;

  @Column(name = "endTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant endTime;

  @Column(name = "beforeScenarioStartTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeScenarioStartTime;

  @Column(name = "beforeScenarioEndTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeScenarioEndTime;

  @ManyToOne
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private PipelineExecutionStatus status;

  @ManyToOne
  @JoinColumn(name = "executor_id", nullable = false, referencedColumnName = "id")
  private User executor;

  @ManyToOne
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
