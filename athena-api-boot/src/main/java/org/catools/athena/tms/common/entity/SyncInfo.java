package org.catools.athena.tms.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.Project;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.tms.common.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "sync_info", schema = ATHENA_TMS_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class SyncInfo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "action", length = 50, nullable = false)
  private String action;

  @Column(name = "component", length = 100, nullable = false)
  private String component;

  @Column(name = "start_time", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant startTime;

  @Column(name = "end_time", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant endTime;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
  private Project project;

}
