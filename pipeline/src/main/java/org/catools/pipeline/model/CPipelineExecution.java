package org.catools.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.catools.pipeline.configs.CPipelineConfigs.PIPELINE_SCHEMA;


@Entity
@Table(name = "execution", schema = PIPELINE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CPipelineExecution implements Serializable {

  private static final long serialVersionUID = 6051874058285613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "packageName", nullable = false)
  private String packageName;

  @Column(name = "className", nullable = false)
  private String className;

  @Column(name = "methodName", nullable = false)
  private String methodName;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "startTime", nullable = false)
  private Date startTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "endTime", nullable = false)
  private Date endTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "testStartTime")
  private Date testStartTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "testEndTime")
  private Date testEndTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeClassStartTime")
  private Date beforeClassStartTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeMethodStartTime")
  private Date beforeMethodStartTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeClassEndTime")
  private Date beforeClassEndTime;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "beforeMethodEndTime")
  private Date beforeMethodEndTime;

  @OneToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      targetEntity = CPipelineExecutionException.class)
  @JoinColumn(
      name = "exception_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "FK_EXECUTION_EXCEPTION"))
  private CPipelineExecutionException exception;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      targetEntity = CPipelineStatus.class)
  @JoinColumn(
      name = "status_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_EXECUTION_STATUS"))
  private CPipelineStatus status;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      targetEntity = CPipelineUser.class)
  @JoinColumn(
      name = "executor_id",
      referencedColumnName = "name",
      foreignKey = @ForeignKey(name = "FK_EXECUTION_USER"))
  private CPipelineUser executor;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      targetEntity = CPipeline.class)
  @JoinColumn(
      name = "pipeline_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "FK_EXECUTION_PIPELINE"))
  private CPipeline pipeline;

  @ManyToMany(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      targetEntity = CPipelineExecutionMetaData.class)
  @JoinTable(
      schema = PIPELINE_SCHEMA,
      name = "execution_metadata_mid",
      joinColumns = {@JoinColumn(name = "execution_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private List<CPipelineExecutionMetaData> metadata = new ArrayList<>();
}
