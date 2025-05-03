package org.catools.athena.metric.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "metric")
@Data
@Accessors(chain = true)
public class Metric implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "environment_id", nullable = false)
  private Long environmentId;

  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @Column(name = "duration")
  private Long duration;

  @Column(name = "action_time", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant actionTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "action_id", nullable = false, referencedColumnName = "id")
  private Action action;

}
