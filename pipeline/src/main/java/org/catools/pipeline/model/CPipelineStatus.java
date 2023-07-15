package org.catools.pipeline.model;

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

import static org.catools.pipeline.configs.CPipelineConfigs.PIPELINE_SCHEMA;


@Entity
@Table(name = "status", schema = PIPELINE_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CPipelineStatus implements Serializable {

  private static final long serialVersionUID = 6867875018185613707L;

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private int id;

  @Column(name = "name", length = 100, unique = true, nullable = false)
  private String name;
}
