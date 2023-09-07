package org.catools.etl.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import static org.catools.etl.git.configs.CGitConfigs.GIT_SCHEMA;

@Entity
@NamedQuery(name = "getGitRepositoryByName", query = "FROM CGitRepository where name=:name")
@Table(name = "repository", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "repository")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CGitRepository implements Serializable {

  @Serial
  private static final long serialVersionUID = 41874058561307260L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "url", length = 1000, nullable = false)
  private String url;

  @Column(name = "last_update", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  public CGitRepository setName(String name) {
    this.name = CStringUtil.trySubstring(name, 50);
    return this;
  }

  public CGitRepository setUrl(String url) {
    this.url = CStringUtil.trySubstring(url, 1000);
    return this;
  }
}
