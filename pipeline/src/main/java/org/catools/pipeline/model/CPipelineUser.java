package org.catools.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

import static org.catools.pipeline.configs.CPipelineConfigs.PIPELINE_SCHEMA;

@NamedQueries({@NamedQuery(name = "getPipelineUserByName", query = "FROM CPipelineUser where name=:name")})
@Entity
@Table(name = "user", schema = PIPELINE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "user")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CPipelineUser implements Serializable {
  private static final long serialVersionUID = 6267874018185513707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", length = 150, unique = true, nullable = false)
  private String name;

  public CPipelineUser(String name) {
    this.name = name;
  }
}
