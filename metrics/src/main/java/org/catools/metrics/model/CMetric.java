package org.catools.metrics.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.metrics.configs.CMetricsConfigs;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@NamedQuery(name = "getMetricsMetaData", query = "FROM CMetric where name=:name and value=:value and amount=:amount")
@Table(name = "metric_metadata", schema = CMetricsConfigs.PERFORMANCE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "metric_metadata")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CMetric implements Serializable {

  @Serial
  private static final long serialVersionUID = 6423057370048561187L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "value", length = 3000)
  private String value;

  @Column(name = "amount")
  private Number amount;

  public CMetric(String name, String value, Number amount) {
    this.name = name;
    this.value = value;
    this.amount = amount;
  }
}
