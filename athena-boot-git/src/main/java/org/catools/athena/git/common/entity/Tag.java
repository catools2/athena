package org.catools.athena.git.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Entity
@Table(
    name = "tag",
    uniqueConstraints = {@UniqueConstraint(name = "uk_tag_name_hash", columnNames = {"name", "hash"})},
    indexes = {@Index(name = "idx_tag_name_hash", columnList = "name, hash")}
)
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class Tag implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, updatable = false, nullable = false)
  private Long id;

  @Column(name = "hash", length = 50, nullable = false)
  private String hash;

  @Column(name = "name", length = 200, nullable = false)
  private String name;
}
