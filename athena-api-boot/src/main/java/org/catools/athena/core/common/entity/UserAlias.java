package org.catools.athena.core.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

import static org.catools.athena.core.common.config.CoreConstant.ATHENA_CORE_SCHEMA;


@Entity
@Table(name = "user_alias", schema = ATHENA_CORE_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserAlias implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "alias", length = 200, unique = true, nullable = false)
  private String alias;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    UserAlias userAlias = (UserAlias) o;

    EqualsBuilder equalsBuilder = new EqualsBuilder();

    equalsBuilder.append(user != null ? user.getUsername() : null, userAlias.user != null ? userAlias.user.getUsername() : null);

    return equalsBuilder.append(alias, userAlias.alias).isEquals();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
    hashCodeBuilder.append(user != null ? user.getUsername() : "");
    return hashCodeBuilder.append(alias).toHashCode();
  }
}
