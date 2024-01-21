package org.catools.athena.rest.tms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

import static org.catools.athena.rest.tms.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "status_transition", schema = ATHENA_TMS_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class StatusTransition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "The status transition occurred must be provided.")
    @Column(name = "occurred", columnDefinition = "TIMESTAMPTZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant occurred;

    @NotNull(message = "The status transition from status must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "from_status", nullable = false, referencedColumnName = "id")
    private Status from;

    @NotNull(message = "The status transition to status must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "to_status", nullable = false, referencedColumnName = "id")
    private Status to;

    @NotNull(message = "The status transition item must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "item_id", nullable = false, referencedColumnName = "id")
    private Item item;
}
