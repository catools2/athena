package org.catools.athena.rest.tms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.User;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.rest.tms.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "execution", schema = ATHENA_TMS_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TestExecution implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotNull(message = "The execution created date/time must be provided.")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created", columnDefinition = "TIMESTAMPTZ", nullable = false)
  private Instant createdOn;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "executed", columnDefinition = "TIMESTAMPTZ")
  private Instant executedOn;

  @NotNull(message = "The execution cycle must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "cycle_id", nullable = false, referencedColumnName = "id")
  private TestCycle cycle;

  @NotNull(message = "The execution item must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
  private Item item;

  @NotNull(message = "The item status must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
  private Status status;

  @NotNull(message = "The execution version must be provided.")
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "executor_id", nullable = false, referencedColumnName = "id")
  private User executor;
}
