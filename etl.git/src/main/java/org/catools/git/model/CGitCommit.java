package org.catools.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static org.catools.git.configs.CGitConfigs.GIT_SCHEMA;


@Entity
@NamedQuery(name = "getGitCommitByHash", query = "FROM CGitCommit where hash=:hash")
@Table(name = "commit", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "commit")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CGitCommit implements Serializable {

  private static final long serialVersionUID = 8406787401185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "hash", length = 50, nullable = false)
  private String hash;

  @Column(name = "short_message", length = 500, nullable = false)
  private String shortMessage;

  @Column(name = "full_message", length = 2000, nullable = false)
  private String fullMessage;

  @Column(name = "commit_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date commitTime;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      targetEntity = CGitUser.class,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "author_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_COMMIT_AUTHOR"))
  private CGitUser author;

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      targetEntity = CGitUser.class,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "committer_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_COMMIT_COMMITTER"))
  private CGitUser committer;

  @OneToMany(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "commit_id", referencedColumnName = "id")
  private Set<CGitFileChange> fileChanges = new HashSet<>();

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY,
      targetEntity = CGitBranch.class)
  @JoinTable(
      schema = GIT_SCHEMA,
      name = "commit_branch_mid",
      joinColumns = {@JoinColumn(name = "commit_id")},
      inverseJoinColumns = {@JoinColumn(name = "branch_id")}
  )
  private List<CGitBranch> branches = new ArrayList<>();

  @ManyToOne(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      targetEntity = CGitRepository.class,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "repository_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_COMMIT_REPOSITORY"))
  private CGitRepository repository;

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.LAZY,
      targetEntity = CGitTag.class)
  @JoinTable(
      schema = GIT_SCHEMA,
      name = "commit_tag_mid",
      joinColumns = {@JoinColumn(name = "commit_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private List<CGitTag> tags = new ArrayList<>();

  public CGitCommit setHash(String hash) {
    this.hash = CStringUtil.trySubstring(hash, 50);
    return this;
  }

  public CGitCommit setShortMessage(String shortMessage) {
    this.shortMessage = CStringUtil.trySubstring(shortMessage, 500);
    return this;
  }

  public CGitCommit setFullMessage(String fullMessage) {
    this.fullMessage = CStringUtil.trySubstring(fullMessage, 2000);
    return this;
  }

  public void addBranch(CGitBranch branch) {
    branches.add(branch);
  }

  public void addFileChange(CGitFileChange fileChange) {
    fileChanges.add(fileChange);
  }

  public void addTag(CGitTag tag) {
    tags.add(tag);
  }
}
