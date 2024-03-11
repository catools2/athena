package org.catools.athena.tms.common.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.core.common.entity.User;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.tms.common.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "execution", indexes = @Index(columnList = "created, cycle_id, item_id"), schema = ATHENA_TMS_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "item"})
@Accessors(chain = true)
public class TestExecution implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "created", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant createdOn;

  @Column(name = "executed", columnDefinition = "TIMESTAMPTZ")
  private Instant executedOn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cycle_id", nullable = false, referencedColumnName = "id")
  private TestCycle cycle;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "executor_id", referencedColumnName = "id")
  private User executor;

}
