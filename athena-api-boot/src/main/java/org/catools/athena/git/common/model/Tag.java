package org.catools.athena.git.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.git.common.config.GitConstant;

import java.io.Serializable;

@Entity
@Table(name = "tag", schema = GitConstant.ATHENA_GIT_SCHEMA)
@Setter
@Getter
@Accessors(chain = true)
public class Tag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "hash", length = 50, nullable = false)
  private String hash;

  @Column(name = "name", length = 200, nullable = false)
  private String name;
}
