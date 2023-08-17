package org.catools.git.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

import static org.catools.git.configs.CGitConfigs.GIT_SCHEMA;

@Entity
@NamedQuery(name = "getGitBranchByHash", query = "FROM CGitBranch where hash=:hash")
@Table(name = "branch", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "branch")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CGitBranch implements Serializable {

  private static final long serialVersionUID = 41874058561307260L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "hash", length = 50, nullable = false)
  private String hash;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  public CGitBranch(String hash, String name) {
    this.hash = hash;
    this.name = name;
  }

  public CGitBranch setHash(String hash) {
    this.hash = CStringUtil.trySubstring(hash, 50);
    return this;
  }

  public CGitBranch setName(String name) {
    this.name = CStringUtil.trySubstring(name, 300);
    return this;
  }
}
