package org.catools.athena.metric.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.metric.common.config.MetricConstant;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "metric", schema = MetricConstant.ATHENA_METRIC_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
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
