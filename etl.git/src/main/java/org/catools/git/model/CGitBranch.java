package org.catools.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

import static org.catools.git.configs.CGitConfigs.GIT_SCHEMA;

@Entity
@NamedQueries({
    @NamedQuery(name = "getByName", query = "FROM CGitRepository where name=:name"),
})
@Table(name = "branch", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "branch")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CGitBranch implements Serializable {

  private static final long serialVersionUID = 41874058561307260L;

  @Id
  @Column(name = "id", length = 50, nullable = false)
  private String id;

  @Column(name = "name", length = 300, nullable = false)
  private String name;
}
