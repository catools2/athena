package org.catools.athena.metric.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.metric.common.config.MetricConstant.ATHENA_METRIC_SCHEMA;

@Entity
@Table(name = "metric", schema = ATHENA_METRIC_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Metric implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "duration")
  private Long duration;

  @Column(name = "action_time", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant actionTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "environment_id", nullable = false, referencedColumnName = "id")
  private Environment environment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "action_id", nullable = false, referencedColumnName = "id")
  private Action action;

}
