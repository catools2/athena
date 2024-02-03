package org.catools.athena.rest.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import static org.catools.athena.rest.core.config.CoreConstant.ATHENA_CORE_SCHEMA;


@Entity
@Table(name = "user_alias", schema = ATHENA_CORE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserAlias implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "alias", length = 200, unique = true, nullable = false)
  private String alias;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  public UserAlias setUser(User user) {
    if (user == null) return this;
    this.user = user;
    this.user.getAliases().add(this);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    UserAlias userAlias = (UserAlias) o;

    EqualsBuilder equalsBuilder = new EqualsBuilder();

    if (user != null && userAlias.user != null) {
      equalsBuilder.append(user.getUsername(), userAlias.user.getUsername());
    } else if (user != null) {
      equalsBuilder.append(user.getUsername(), null);
    } else if (userAlias.user != null) {
      equalsBuilder.append(null, userAlias.user.getUsername());
    }

    return equalsBuilder.append(id, userAlias.id).append(alias, userAlias.alias).isEquals();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
    hashCodeBuilder.append(user != null ? user.getUsername() : "");
    return hashCodeBuilder.append(id).append(alias).toHashCode();
  }
}
