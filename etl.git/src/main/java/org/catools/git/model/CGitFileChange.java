package org.catools.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static org.catools.git.configs.CGitConfigs.GIT_SCHEMA;


@Entity
@NamedQueries({@NamedQuery(name = "getByCommitId", query = "FROM CGitFileChange where commit.id=:commit_id")})
@Table(name = "file_change", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "file_change")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CGitFileChange implements Serializable {

  private static final long serialVersionUID = 606744018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "commit_id", referencedColumnName = "id")
  private CGitCommit commit;

  @Column(name = "path", length = 500, nullable = false)
  private String path;

  @Column(name = "new_path", length = 500, nullable = false)
  private String newPath;

  @Column(name = "inserted", nullable = false)
  private int inserted;

  @Column(name = "deleted", nullable = false)
  private int deleted;

  private String getCommitId() {
    return commit == null ? null : commit.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, getCommitId(), path, newPath, inserted, deleted);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CGitFileChange that = (CGitFileChange) o;
    return id == that.id && inserted == that.inserted && deleted == that.deleted && Objects.equals(getCommitId(), that.getCommitId()) && Objects.equals(path, that.path) && Objects.equals(newPath, that.newPath);
  }

  @Override
  public String toString() {
    return "CGitFileChange{" +
        "id=" + id +
        ", commitId=" + getCommitId() +
        ", path='" + path + '\'' +
        ", newPath='" + newPath + '\'' +
        ", inserted=" + inserted +
        ", deleted=" + deleted +
        '}';
  }
}
