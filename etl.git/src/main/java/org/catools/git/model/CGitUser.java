package org.catools.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

import static org.catools.git.configs.CGitConfigs.GIT_SCHEMA;

@NamedQueries({@NamedQuery(name = "getUserByName", query = "FROM CGitUser where name=:name")})
@Entity
@Table(name = "user", schema = GIT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "user")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CGitUser implements Serializable {
  private static final long serialVersionUID = 1626787401885513707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", length = 150, unique = true, nullable = false)
  private String name;

  public CGitUser(String name) {
    this.name = name;
  }
}
