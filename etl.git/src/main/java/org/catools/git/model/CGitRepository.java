package org.catools.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.catools.git.configs.CGitConfigs.GIT_SCHEMA;

@Entity
@NamedQueries({
    @NamedQuery(name = "getByName", query = "FROM CGitRepository where name=:name"),
    @NamedQuery(name = "getByUrl", query = "FROM CGitRepository where url=:url")
})
@Table(name = "repository", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "repository")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CGitRepository implements Serializable {

  private static final long serialVersionUID = 41874058561307260L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "url", length = 1000, nullable = false)
  private String url;

  @Column(name = "last_update", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  @ManyToOne(
      cascade = CascadeType.ALL,
      targetEntity = CProject.class,
      fetch = FetchType.LAZY)
  @JoinColumn(name = "project_code",
      referencedColumnName = "code",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_GIT_PROJECT"))
  private CProject project;


  @ManyToMany(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      targetEntity = CGitCommit.class)
  @JoinTable(
      schema = GIT_SCHEMA,
      name = "repository_commit_mid",
      joinColumns = {@JoinColumn(name = "repository_id")},
      inverseJoinColumns = {@JoinColumn(name = "commit_id")}
  )
  private List<CGitCommit> commits = new ArrayList<>();

  public void addCommit(CGitCommit commit) {
    commits.add(commit);
  }
}
