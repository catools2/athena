package org.catools.metrics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static org.catools.metrics.configs.CMetricsConfigs.PERFORMANCE_SCHEMA;


@Entity
@Table(name = "environment", schema = PERFORMANCE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "environment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CMetricEnvironment implements Serializable {
  private static final long serialVersionUID = 6267674018185613707L;

  @Id
  @Column(name = "code", length = 10, unique = true, nullable = false)
  private String code;

  @Column(name = "name", length = 100)
  private String name;
}
