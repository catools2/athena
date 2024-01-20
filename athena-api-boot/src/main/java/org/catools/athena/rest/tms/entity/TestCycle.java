package org.catools.athena.rest.tms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.Version;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.catools.athena.rest.tms.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "cycle", schema = ATHENA_TMS_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TestCycle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The cycle code must be provided.")
    @Size(max = 10, message = "The cycle code can be at most 300 character.")
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @NotBlank(message = "The cycle name must be provided.")
    @Size(max = 300, message = "The cycle name can be at most 300 character.")
    @Column(name = "name", length = 300, nullable = false)
    private String name;

    @NotNull(message = "The cycle start date/time must be provided.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotNull(message = "The cycle version must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "version_id", nullable = false, referencedColumnName = "id")
    private Version version;
}
