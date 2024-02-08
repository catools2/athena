package org.catools.athena.pipeline.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.pipeline.common.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;


@Entity
@Table(name = "status", schema = ATHENA_PIPELINE_SCHEMA)
@Data
@Accessors(chain = true)
public class PipelineExecutionStatus implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", length = 100, unique = true, nullable = false)
  private String name;
}
