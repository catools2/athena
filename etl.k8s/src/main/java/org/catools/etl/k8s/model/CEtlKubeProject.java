package org.catools.etl.k8s.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

import static org.catools.etl.k8s.configs.CEtlKubeConfigs.K8S_SCHEMA;

@NamedQuery(name = "getEtlKubeProjectByName", query = "FROM CEtlKubeProject where name=:name")
@Entity
@Table(name = "project", schema = K8S_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlKubeProject implements Serializable {

  @Serial
  private static final long serialVersionUID = 1370760698740181856L;

  public static final CEtlKubeProject UNSET = new CEtlKubeProject("UNSET");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, unique = true, nullable = false)
  private String name;

  public CEtlKubeProject(String name) {
    this.name = CStringUtil.substring(name, 0, 100);
  }
}
