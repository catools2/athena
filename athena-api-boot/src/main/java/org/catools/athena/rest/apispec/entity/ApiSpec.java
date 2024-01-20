package org.catools.athena.rest.apispec.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.rest.core.entity.Project;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.apispec.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;

@Entity
@Table(name = "api_spec", schema = ATHENA_OPENAPI_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiSpec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The api spec version must be provided.")
    @Size(max = 10, message = "The api spec version can be at most 10 character.")
    @Column(name = "version", length = 10, nullable = false)
    private String version;

    @NotBlank(message = "The api spec name must be provided.")
    @Size(max = 100, message = "The api spec name can be at most 100 character.")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "The api spec title must be provided.")
    @Size(max = 100, message = "The api spec title can be at most 100 character.")
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotNull(message = "The api spec project must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
    private Project project;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "first_time_seen")
    private LocalDateTime firstTimeSeen;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_time_seen")
    private LocalDateTime lastTimeSeen;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            targetEntity = ApiSpecMetadata.class)
    @JoinTable(
            schema = ATHENA_OPENAPI_SCHEMA,
            name = "api_spec_metadata_mid",
            joinColumns = {@JoinColumn(name = "api_spec_id")},
            inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
    )
    private Set<ApiSpecMetadata> metadata = new HashSet<>();

    public void addMetadata(String name, String value) {
        metadata.add(new ApiSpecMetadata().setName(name).setValue(value));
    }
}
