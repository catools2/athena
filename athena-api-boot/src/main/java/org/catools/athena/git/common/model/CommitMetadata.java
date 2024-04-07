package org.catools.athena.git.common.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;
import org.catools.athena.git.common.config.GitConstant;

import java.io.Serializable;


@Entity
@Table(name = "commit_metadata", schema = GitConstant.ATHENA_GIT_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class CommitMetadata implements NameValuePair, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "value", length = 1000, nullable = false)
  private String value;

}
