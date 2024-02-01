package org.catools.athena.rest.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.core.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "environment", schema = ATHENA_CORE_SCHEMA)
@Data
@Accessors(chain = true)
public class Environment implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "code", length = 10, unique = true, nullable = false)
  private String code;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;
}
