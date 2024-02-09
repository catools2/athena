package org.catools.athena.pipeline.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;

import java.io.Serializable;

import static org.catools.athena.pipeline.common.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;


@Entity
@Table(name = "execution_metadata", schema = ATHENA_PIPELINE_SCHEMA)
@Setter
@Getter
@Accessors(chain = true)
public class PipelineExecutionMetadata implements NameValuePair, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "value", length = 2000, nullable = false)
  private String value;
}
