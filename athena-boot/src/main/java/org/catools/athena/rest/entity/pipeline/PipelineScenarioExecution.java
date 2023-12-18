package org.catools.athena.rest.entity.pipeline;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.entity.core.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.catools.athena.rest.config.AthenaConstant.ATHENA_SCHEMA;


@Entity
@Table(name = "scenario_execution", schema = ATHENA_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PipelineScenarioExecution implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank
  @Size(max = 1000)
  @Column(name = "feature", length = 1000, nullable = false)
  private String feature;

  @NotBlank
  @Size(max = 300)
  @Column(name = "scenario", length = 300, nullable = false)
  private String scenario;

  @NotBlank
  @Size(max = 2000)
  @Column(name = "parameters", length = 300, nullable = false)
  private String parameters;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "startTime", nullable = false)
  private Date startTime;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "endTime", nullable = false)
  private Date endTime;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeScenarioStartTime", nullable = false)
  private Date beforeScenarioStartTime;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeScenarioEndTime", nullable = false)
  private Date beforeScenarioEndTime;

  @NotNull
  @ManyToOne(
      cascade = CascadeType.MERGE,
      fetch = FetchType.LAZY,
      targetEntity = PipelineExecutionStatus.class)
  @JoinColumn(
      name = "status_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_EXECUTION_STATUS"))
  private PipelineExecutionStatus status;

  @NotNull
  @ManyToOne(
      cascade = CascadeType.MERGE,
      fetch = FetchType.LAZY,
      targetEntity = User.class)
  @JoinColumn(
      name = "executor_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "FK_EXECUTION_USER"))
  private User executor;

  @NotNull
  @ManyToOne(
      cascade = CascadeType.MERGE,
      fetch = FetchType.LAZY,
      targetEntity = Pipeline.class)
  @JoinColumn(
      name = "pipeline_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "FK_EXECUTION_PIPELINE"))
  private Pipeline pipeline;

  @ManyToMany(
      cascade = CascadeType.MERGE,
      fetch = FetchType.EAGER,
      targetEntity = PipelineExecutionMetadata.class)
  @JoinTable(
      schema = ATHENA_SCHEMA,
      name = "scenario_metadata_mid",
      joinColumns = {@JoinColumn(name = "execution_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private List<PipelineExecutionMetadata> metadata = new ArrayList<>();
}
