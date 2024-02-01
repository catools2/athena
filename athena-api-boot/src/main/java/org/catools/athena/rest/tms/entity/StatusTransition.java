package org.catools.athena.rest.tms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.rest.tms.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "status_transition", schema = ATHENA_TMS_SCHEMA)
@Data
@Accessors(chain = true)
public class StatusTransition implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "occurred", columnDefinition = "TIMESTAMPTZ")
  private Instant occurred;

  @ManyToOne
  @JoinColumn(name = "from_status", nullable = false, referencedColumnName = "id")
  private Status from;

  @ManyToOne
  @JoinColumn(name = "to_status", nullable = false, referencedColumnName = "id")
  private Status to;

  @ManyToOne
  @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
  private Item item;
}
