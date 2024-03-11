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
@Table(name = "status_transition", schema = ATHENA_TMS_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "item"})
@Accessors(chain = true)
public class StatusTransition implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "occurred", columnDefinition = "TIMESTAMPTZ")
  private Instant occurred;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_status", nullable = false, referencedColumnName = "id")
  private Status from;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_status", nullable = false, referencedColumnName = "id")
  private Status to;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
  private Item item;

  @ManyToOne
  @JoinColumn(name = "author", nullable = false, referencedColumnName = "id")
  private User author;

}
