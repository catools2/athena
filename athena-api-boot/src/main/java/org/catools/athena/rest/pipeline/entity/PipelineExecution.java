package org.catools.athena.rest.pipeline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.pipeline.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;


@Entity
@Table(name = "execution", schema = ATHENA_PIPELINE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PipelineExecution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The pipeline execution package name must be provided.")
    @Size(max = 300, message = "The pipeline execution package name can be at most 300 character.")
    @Column(name = "packageName", length = 300, nullable = false)
    private String packageName;

    @NotBlank(message = "The pipeline execution class name must be provided.")
    @Size(max = 300, message = "The pipeline execution class name can be at most 300 character.")
    @Column(name = "className", length = 300, nullable = false)
    private String className;

    @NotBlank(message = "The pipeline execution method name must be provided.")
    @Size(max = 300, message = "The pipeline execution method name can be at most 300 character.")
    @Column(name = "methodName", length = 300, nullable = false)
    private String methodName;

    @Size(max = 300, message = "The pipeline execution parameters can be at most 2000 character.")
    @Column(name = "parameters", length = 2000)
    private String parameters;

    @NotNull(message = "The pipeline execution start time must be provided.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant startTime;

    @NotNull(message = "The pipeline execution end time must be provided.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endTime", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant endTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "testStartTime", columnDefinition = "TIMESTAMPTZ")
    private Instant testStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "testEndTime", columnDefinition = "TIMESTAMPTZ")
    private Instant testEndTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeClassStartTime", columnDefinition = "TIMESTAMPTZ")
    private Instant beforeClassStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeClassEndTime", columnDefinition = "TIMESTAMPTZ")
    private Instant beforeClassEndTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeMethodStartTime", columnDefinition = "TIMESTAMPTZ")
    private Instant beforeMethodStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeMethodEndTime", columnDefinition = "TIMESTAMPTZ")
    private Instant beforeMethodEndTime;

    @NotNull(message = "The pipeline execution status must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    private PipelineExecutionStatus status;

    @NotNull(message = "The pipeline execution executor must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "executor_id", nullable = false, referencedColumnName = "id")
    private User executor;

    @NotNull(message = "The pipeline execution pipeline must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pipeline_id", nullable = false, referencedColumnName = "id")
    private Pipeline pipeline;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            targetEntity = PipelineExecutionMetadata.class)
    @JoinTable(
            schema = ATHENA_PIPELINE_SCHEMA,
            name = "execution_metadata_mid",
            joinColumns = {@JoinColumn(name = "execution_id")},
            inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
    )
    private Set<PipelineExecutionMetadata> metadata = new HashSet<>();
}
