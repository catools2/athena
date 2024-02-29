package org.catools.athena.git.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.catools.athena.git.common.config.GitConstant;

import java.io.Serializable;


@Entity
@Table(name = "diff_entry", schema = GitConstant.ATHENA_GIT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DiffEntry implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "commit_id", referencedColumnName = "id", nullable = false)
  private Commit commit;

  @Column(name = "old", length = 1000, nullable = false)
  private String oldPath;

  @Column(name = "new", length = 1000, nullable = false)
  private String newPath;

  @Column(name = "change_type", length = 30, nullable = false)
  private String changeType;

  @Column(name = "inserted", nullable = false)
  private Integer inserted;

  @Column(name = "deleted", nullable = false)
  private Integer deleted;

  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    DiffEntry diffEntry = (DiffEntry) o;

    EqualsBuilder equalsBuilder = new EqualsBuilder();

    equalsBuilder.append(
        commit != null ? commit.getHash() : null,
        diffEntry.commit != null ? diffEntry.commit.getHash() : null
    );

    return equalsBuilder.append(oldPath, diffEntry.oldPath).append(newPath, diffEntry.newPath).append(inserted, diffEntry.inserted).append(deleted, diffEntry.deleted).isEquals();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
    hashCodeBuilder.append(commit != null ? commit.getHash() : "");
    return hashCodeBuilder.append(oldPath).append(newPath).append(inserted).append(deleted).toHashCode();
  }
}
