package org.catools.athena.git.common.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.git.common.config.GitConstant;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "commit", schema = GitConstant.ATHENA_GIT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
  private User author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "committer_id", referencedColumnName = "id", nullable = false)
  private User committer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "repository_id", referencedColumnName = "id", nullable = false)
  private GitRepository repository;

  @Column(name = "total_file")
  private Integer totalImpactedFiles;

  @Column(name = "line_inserted")
  private Integer totalInsertedLine;

  @Column(name = "line_deleted")
  private Integer totalDeletedLines;

  @OneToMany(mappedBy = "commit", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<DiffEntry> diffEntries = new HashSet<>();

  @ManyToMany
  @JoinTable(
      schema = GitConstant.ATHENA_GIT_SCHEMA,
      name = "commit_tag_mid",
      joinColumns = {@JoinColumn(name = "commit_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private Set<Tag> tags = new HashSet<>();

  @ManyToMany
  @JoinTable(
      schema = GitConstant.ATHENA_GIT_SCHEMA,
      name = "commit_metadata_mid",
      joinColumns = {@JoinColumn(name = "commit_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<CommitMetadata> metadata = new HashSet<>();

  public void setDiffEntries(Set<DiffEntry> diffEntries) {
    if (diffEntries == null) {
      this.diffEntries.forEach(this::removeDiffEntries);
      return;
    }
    this.diffEntries.clear();
    diffEntries.forEach(this::addDiffEntry);
  }

  public void removeDiffEntries(DiffEntry diffEntry) {
    if (diffEntry == null) return;
    this.diffEntries.remove(diffEntry.setCommit(null));
  }

  public void addDiffEntry(DiffEntry diffEntry) {
    if (diffEntry == null) return;
    this.diffEntries.add(diffEntry.setCommit(this));
  }
}
