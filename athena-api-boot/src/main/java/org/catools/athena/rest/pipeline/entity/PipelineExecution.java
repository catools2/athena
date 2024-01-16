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

    @NotBlank
    @Size(max = 300)
    @Column(name = "packageName", length = 300, nullable = false)
    private String packageName;

    @NotBlank
    @Size(max = 300)
    @Column(name = "className", length = 300, nullable = false)
    private String className;

    @NotBlank
    @Size(max = 300)
    @Column(name = "methodName", length = 300, nullable = false)
    private String methodName;

    @Size(max = 2000)
    @Column(name = "parameters", length = 300)
    private String parameters;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime", nullable = false)
    private LocalDateTime startTime;

    @NotNull
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

    @NotNull
    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            targetEntity = PipelineExecutionStatus.class)
    @JoinColumn(
            name = "status_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_EXECUTION_STATUS"))
    private PipelineExecutionStatus status;

    @NotNull
    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            targetEntity = User.class)
    @JoinColumn(
            name = "executor_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_EXECUTION_USER"))
    private User executor;

    @NotNull
    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            targetEntity = Pipeline.class)
    @JoinColumn(
            name = "pipeline_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_EXECUTION_PIPELINE"))
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
