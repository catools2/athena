package org.catools.athena.rest.pipeline.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.pipeline.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;


@Entity
@Table(name = "execution", schema = ATHENA_PIPELINE_SCHEMA)
@Data
@Accessors(chain = true)
public class PipelineExecution implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "packageName", length = 300, nullable = false)
  private String packageName;

  @Column(name = "className", length = 300, nullable = false)
  private String className;

  @Column(name = "methodName", length = 300, nullable = false)
  private String methodName;

  @Column(name = "parameters", length = 2000)
  private String parameters;

  @Column(name = "startTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startTime;

  @Column(name = "endTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant endTime;

  @Column(name = "testStartTime", columnDefinition = "TIMESTAMPTZ")
  private Instant testStartTime;

  @Column(name = "testEndTime", columnDefinition = "TIMESTAMPTZ")
  private Instant testEndTime;

  @Column(name = "beforeClassStartTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeClassStartTime;

  @Column(name = "beforeClassEndTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeClassEndTime;

  @Column(name = "beforeMethodStartTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeMethodStartTime;

  @Column(name = "beforeMethodEndTime", columnDefinition = "TIMESTAMPTZ")
  private Instant beforeMethodEndTime;

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
      name = "execution_metadata_mid",
      joinColumns = {@JoinColumn(name = "execution_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private Set<PipelineExecutionMetadata> metadata = new HashSet<>();
}
