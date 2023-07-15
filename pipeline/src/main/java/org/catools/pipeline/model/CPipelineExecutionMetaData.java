package org.catools.pipeline.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

import static org.catools.pipeline.configs.CPipelineConfigs.PIPELINE_SCHEMA;


@Entity
@NamedQueries({
    @NamedQuery(name = "getExecutionMetaDataByNameAndValue", query = "FROM CPipelineExecutionMetaData where name=:name and value=:value")
})
@Table(name = "execution_metadata", schema = PIPELINE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "execution_metadata")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CPipelineExecutionMetaData implements Serializable {

  private static final long serialVersionUID = 606744018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "value", length = 1000, nullable = false)
  private String value;

  public CPipelineExecutionMetaData(String name, String value) {
    this.name = name;
    this.value = value;
  }
}
