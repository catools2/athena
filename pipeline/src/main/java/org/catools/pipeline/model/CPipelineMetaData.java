package org.catools.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

import static org.catools.pipeline.configs.CPipelineConfigs.PIPELINE_SCHEMA;


@Entity
@NamedQuery(name = "getPipelineMetaDataByNameAndValue", query = "FROM CPipelineMetaData where name=:name and value=:value")
@Table(name = "pipeline_metadata", schema = PIPELINE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "pipeline_metadata")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CPipelineMetaData implements Serializable {

  @Serial
  private static final long serialVersionUID = 4067874018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "value", length = 1000, nullable = false)
  private String value;

  public CPipelineMetaData(String name, String value) {
    this.name = name;
    this.value = value;
  }
}
