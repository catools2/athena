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
@NamedQuery(name = "getGitTagByHash", query = "FROM CGitTag where hash=:hash")
@Table(name = "tag", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CGitTag implements Serializable {

  private static final long serialVersionUID = 41874058561307260L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "hash", length = 50, nullable = false)
  private String hash;


  @Column(name = "name", length = 300, nullable = false)
  private String name;

  public CGitTag(String hash, String name) {
    this.hash = hash;
    this.name = name;
  }

  public CGitTag setHash(String hash) {
    this.hash = CStringUtil.trySubstring(hash, 50);
    return this;
  }

  public CGitTag setName(String name) {
    this.name = CStringUtil.trySubstring(name, 300);
    return this;
  }
}
