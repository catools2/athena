package org.catools.athena.rest.entity.pipeline;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.config.AthenaConstant.ATHENA_SCHEMA;


@Entity
@Table(name = "execution_metadata", schema = ATHENA_SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PipelineExecutionMetadata implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank
  @Size(max = 100)
  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @NotBlank
  @Size(max = 2000)
  @Column(name = "value", length = 2000, nullable = false)
  private String value;
}
