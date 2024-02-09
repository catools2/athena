package org.catools.athena.core.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.core.common.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "user", schema = ATHENA_CORE_SCHEMA)
@Setter
@Getter
@Accessors(chain = true)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "username", length = 150, unique = true, nullable = false)
  private String username;

  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private Set<UserAlias> aliases = new HashSet<>();

  public void addAlias(Long id, String alias) {
    aliases.add(new UserAlias().setUser(this).setAlias(alias).setId(id));
  }
}
