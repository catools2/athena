package org.catools.etl.k8s.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.common.utils.CStringUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

import static org.catools.etl.k8s.configs.CEtlKubeConfigs.K8S_SCHEMA;


@NamedQuery(name = "getEtlKubeContainerMetaData", query = "FROM CEtlKubeContainerMetaData where name=:name and value=:value")
@Entity
@Table(name = "container_metadata", schema = K8S_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "container_metadata")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlKubeContainerMetaData implements Serializable {

  @Serial
  private static final long serialVersionUID = 8561370606787401817L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "value", length = 500, nullable = false)
  private String value;

  public CEtlKubeContainerMetaData(String name, String value) {
    this.name = CStringUtil.substring(name, 0, 50);
    this.value = CStringUtil.substring(value, 0, 500);
  }
}
