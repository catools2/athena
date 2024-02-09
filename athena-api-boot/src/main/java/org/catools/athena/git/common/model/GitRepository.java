package org.catools.athena.git.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.git.common.config.GitConstant;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "repository", schema = GitConstant.ATHENA_GIT_SCHEMA)
@Setter
@Getter
@Accessors(chain = true)
public class GitRepository implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", length = 200, unique = true, nullable = false)
  private String name;

  @Column(name = "url", length = 300, unique = true, nullable = false)
  private String url;

  @Column(name = "last_sync", columnDefinition = "TIMESTAMPTZ")
  private Instant lastSync;
}
