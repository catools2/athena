package org.catools.athena.tms.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.Version;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.tms.common.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "cycle", schema = ATHENA_TMS_SCHEMA)
@Data
@Accessors(chain = true)
public class TestCycle implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "code", length = 10, nullable = false)
  private String code;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "start_date", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startDate;

  @Column(name = "end_date", columnDefinition = "TIMESTAMPTZ")
  private Instant endDate;

  @ManyToOne
  @JoinColumn(name = "version_id", nullable = false, referencedColumnName = "id")
  private Version version;

}
