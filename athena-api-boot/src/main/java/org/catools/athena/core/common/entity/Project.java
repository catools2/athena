package org.catools.athena.core.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.core.common.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "project", indexes = @Index(columnList = "code"), schema = ATHENA_CORE_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
