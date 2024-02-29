package org.catools.athena.core.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.core.common.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "project", indexes = @Index(columnList = "code"), schema = ATHENA_CORE_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class Project implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "code", length = 10, unique = true, nullable = false)
  private String code;

  @Column(name = "name", length = 50, nullable = false)
  private String name;
}
