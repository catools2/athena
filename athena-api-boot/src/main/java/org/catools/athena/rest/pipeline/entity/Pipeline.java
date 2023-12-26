package org.catools.athena.rest.pipeline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.Environment;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.core.config.AthenaCoreConstant.ATHENA_SCHEMA;

@Entity
@Table(name = "pipeline", schema = ATHENA_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Pipeline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank
    @Size(max = 300)
    @Column(name = "description", length = 300, nullable = false)
    private String description;

    @NotBlank
    @Size(max = 100)
    @Column(name = "number", length = 100, nullable = false)
    private String number;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @NotNull
    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = Environment.class,
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "environment_code",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PIPELINE_ENVIRONMENT"))
    private Environment environment;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            targetEntity = PipelineMetadata.class)
    @JoinTable(
            schema = ATHENA_SCHEMA,
            name = "pipeline_metadata_mid",
            joinColumns = {@JoinColumn(name = "pipeline_id")},
            inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
    )
    private Set<PipelineMetadata> metadata = new HashSet<>();
}
