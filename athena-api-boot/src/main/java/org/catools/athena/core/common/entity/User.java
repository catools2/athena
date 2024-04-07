package org.catools.athena.core.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.core.common.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "user", schema = ATHENA_CORE_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "username", length = 150, unique = true, nullable = false)
  private String username;

  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<UserAlias> aliases = new HashSet<>();

  public User(Long id, String username, UserAlias... aliases) {
    this.id = id;
    this.username = username;
    if (aliases != null) {
      for (UserAlias alias : aliases) {
        this.aliases.add(alias.setUser(this));
      }
    }
  }

  public void addAlias(Long id, String alias) {
    aliases.add(new UserAlias().setUser(this).setAlias(alias).setId(id));
  }
}
