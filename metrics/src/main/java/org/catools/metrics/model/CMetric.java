package org.catools.metrics.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.metrics.configs.CMetricsConfigs;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
    @NamedQuery(name = "getMetaDataByNameAndValue", query = "FROM CMetric where name=:name and value=:value")
})
@Table(name = "metric_metadata", schema = CMetricsConfigs.PERFORMANCE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "metric_metadata")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CMetric implements Serializable {

  private static final long serialVersionUID = 6423057370048561187L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "value", nullable = false)
  private Number value;

  public CMetric(String name, Number value) {
    this.name = name;
    this.value = value;
  }
}
