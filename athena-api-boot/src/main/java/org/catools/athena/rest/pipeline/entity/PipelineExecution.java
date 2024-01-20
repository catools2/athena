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
import java.time.LocalDateTime;
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
    @Column(name = "startTime", nullable = false)
    private LocalDateTime startTime;

    @NotNull(message = "The pipeline execution end time must be provided.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endTime", nullable = false)
    private LocalDateTime endTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "testStartTime")
    private LocalDateTime testStartTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "testEndTime")
    private LocalDateTime testEndTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeClassStartTime")
    private LocalDateTime beforeClassStartTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeClassEndTime")
    private LocalDateTime beforeClassEndTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeMethodStartTime")
    private LocalDateTime beforeMethodStartTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beforeMethodEndTime")
    private LocalDateTime beforeMethodEndTime;

    @NotNull(message = "The pipeline execution status must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    private PipelineExecutionStatus status;

    @NotNull(message = "The pipeline execution executor must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id", nullable = false, referencedColumnName = "id")
    private User executor;

    @NotNull(message = "The pipeline execution pipeline must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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
