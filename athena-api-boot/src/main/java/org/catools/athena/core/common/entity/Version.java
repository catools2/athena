package org.catools.athena.core.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.core.common.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "version", schema = ATHENA_CORE_SCHEMA)
@Setter
@Getter
@Accessors(chain = true)
public class Version implements Serializable {

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
