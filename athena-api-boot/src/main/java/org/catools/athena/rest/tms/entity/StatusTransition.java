package org.catools.athena.rest.tms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @Column(name = "occurred")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime occurred;

    @NotNull(message = "The status transition from status must be provided.")
    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "from_status", referencedColumnName = "id")
    private Status from;

    @NotNull(message = "The status transition to status must be provided.")
    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "to_status", referencedColumnName = "id")
    private Status to;

    @NotNull(message = "The status transition item must be provided.")
    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
}
