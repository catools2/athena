package org.catools.athena.rest.git.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.rest.git.config.GitConstant.ATHENA_GIT_SCHEMA;

@Entity
@Table(name = "repository", schema = ATHENA_GIT_SCHEMA)
@Data
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
