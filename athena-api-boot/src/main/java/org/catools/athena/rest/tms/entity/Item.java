package org.catools.athena.rest.tms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.apispec.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;
import static org.catools.athena.rest.tms.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "item", schema = ATHENA_TMS_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The item code must be provided.")
    @Size(max = 15, message = "The item code can be at most 15 character.")
    @Column(name = "code", length = 15, nullable = false)
    private String code;

    @NotBlank(message = "The item name must be provided.")
    @Size(max = 300, message = "The item name can be at most 300 character.")
    @Column(name = "name", length = 300, nullable = false)
    private String name;

    @NotNull(message = "The item created date/time must be provided.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private Instant createdOn;

    @NotNull(message = "The item created by must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "id")
    private User createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", columnDefinition = "TIMESTAMPTZ")
    private Instant updatedOn;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "updated_by", nullable = false, referencedColumnName = "id")
    private User updatedBy;

    @NotNull(message = "The item type must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "type_id", nullable = false, referencedColumnName = "id")
    private ItemType type;

    @NotNull(message = "The item status must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    private Status status;

    @NotNull(message = "The item priority must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "priority_id", nullable = false, referencedColumnName = "id")
    private Priority priority;

    @NotNull(message = "The item project must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
    private Project project;

    @ManyToMany(
        cascade = CascadeType.MERGE,
        fetch = FetchType.EAGER)
    @JoinTable(
        schema = ATHENA_OPENAPI_SCHEMA,
        name = "item_version_mid",
        joinColumns = {@JoinColumn(name = "item_id")},
        inverseJoinColumns = {@JoinColumn(name = "version_id")}
    )
    private Set<Version> versions = new HashSet<>();

    @ManyToMany(
        cascade = CascadeType.MERGE,
        fetch = FetchType.EAGER,
        targetEntity = ItemMetadata.class)
    @JoinTable(
        schema = ATHENA_OPENAPI_SCHEMA,
        name = "item_metadata_mid",
        joinColumns = {@JoinColumn(name = "item_id")},
        inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
    )
    private Set<ItemMetadata> metadata = new HashSet<>();
}
