package org.catools.athena.metric.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.metric.common.config.MetricConstant.ATHENA_METRIC_SCHEMA;

@Entity
@Table(name = "action", indexes = @Index(columnList = "name, type, target, command"), schema = ATHENA_METRIC_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Action implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @Column(name = "target", length = 500, nullable = false)
  private String target;

  @Column(name = "command", length = 500, nullable = false)
  private String command;

  @Column(name = "parameter", length = 2000)
  private String parameter;

}
