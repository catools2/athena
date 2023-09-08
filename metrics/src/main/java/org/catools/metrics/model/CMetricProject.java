package org.catools.metrics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.metrics.configs.CMetricsConfigs;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "project", schema = CMetricsConfigs.PERFORMANCE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CMetricProject implements Serializable {

  @Serial
  private static final long serialVersionUID = 6569874018185613707L;

  @Id
  @Column(name = "code", length = 5, unique = true, nullable = false)
  private String code;

  @Column(name = "name", length = 50, unique = true, nullable = false)
  private String name;
}
