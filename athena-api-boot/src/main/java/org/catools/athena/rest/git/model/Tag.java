package org.catools.athena.rest.git.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.git.config.GitConstant.ATHENA_GIT_SCHEMA;

@Entity
@Table(name = "tag", schema = ATHENA_GIT_SCHEMA)
@Data
@Accessors(chain = true)
public class Tag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "hash", length = 50, nullable = false)
  private String hash;

  @Column(name = "name", length = 300, nullable = false)
  private String name;
}
