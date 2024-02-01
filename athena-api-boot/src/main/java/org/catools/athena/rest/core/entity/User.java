package org.catools.athena.rest.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.core.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "user", schema = ATHENA_CORE_SCHEMA)
@Data
@Accessors(chain = true)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "username", length = 150, unique = true, nullable = false)
  private String username;

  @OneToMany(mappedBy = "user")
  private Set<UserAlias> aliases = new HashSet<>();

  public User addAlias(Long id, String alias) {
    aliases.add(new UserAlias(id, this, alias));
    return this;
  }
}
