package org.catools.athena.core.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.core.common.config.CoreConstant.ATHENA_CORE_SCHEMA;


@Entity
@Table(name = "user_alias", schema = ATHENA_CORE_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "user"})
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

}
