package org.catools.athena.rest.git.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.git.config.GitConstant.ATHENA_GIT_SCHEMA;


@Entity
@Table(name = "commit", schema = ATHENA_GIT_SCHEMA)
@Data
@Accessors(chain = true)
public class Commit implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "hash", length = 50, unique = true, nullable = false)
  private String hash;

  @Column(name = "parent_hash", length = 50)
  private String parentHash;

  @Column(name = "short_message", length = 5000, nullable = false)
  private String shortMessage;

  @Column(name = "commit_time", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant commitTime;

  @Column(name = "parent_count", nullable = false)
  private Integer parentCount;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(name = "committer_id", referencedColumnName = "id", nullable = false)
  private User committer;

  @Column(name = "inserted")
  private Integer inserted;

  @Column(name = "deleted")
  private Integer deleted;

  @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL)
  private Set<DiffEntry> diffEntries = new HashSet<>();

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_GIT_SCHEMA,
      name = "commit_tag_mid",
      joinColumns = {@JoinColumn(name = "commit_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private Set<Tag> tags = new HashSet<>();

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(
      schema = ATHENA_GIT_SCHEMA,
      name = "commit_metadata_mid",
      joinColumns = {@JoinColumn(name = "commit_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<CommitMetadata> metadata = new HashSet<>();
}