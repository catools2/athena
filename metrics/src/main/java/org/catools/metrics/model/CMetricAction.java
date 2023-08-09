package org.catools.metrics.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.metrics.configs.CMetricsConfigs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "action", schema = CMetricsConfigs.PERFORMANCE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CMetricAction implements Serializable {

  private static final long serialVersionUID = 2373708561876051404L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "action_time")
  private Date actionTime;

  @Column(name = "duration")
  private long duration;

  @ManyToOne(
      cascade = CascadeType.ALL,
      targetEntity = CMetricEnvironment.class,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "environment_code",
      referencedColumnName = "code",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_PIPELINE_ENVIRONMENT"))
  private CMetricEnvironment environment;

  @ManyToOne(
      cascade = CascadeType.ALL,
      targetEntity = CMetricProject.class,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "project_code",
      referencedColumnName = "code",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_PIPELINE_PROJECT"))
  private CMetricProject project;

  @ManyToMany(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      targetEntity = CMetric.class)
  @JoinTable(
      schema = CMetricsConfigs.PERFORMANCE_SCHEMA,
      name = "metric_metadata_mid",
      joinColumns = {@JoinColumn(name = "metric_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
  )
  private List<CMetric> metadata = new ArrayList<>();

}
