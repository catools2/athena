package org.catools.athena.rest.apispec.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static org.catools.athena.rest.apispec.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;


@Entity
@Table(name = "api_path", schema = ATHENA_OPENAPI_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiPath implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The path method must be provided.")
    @Size(max = 10, message = "The path method can be at most 10 character.")
    @Column(name = "method", length = 10, nullable = false)
    private String method;

    @NotBlank(message = "The path title must be provided.")
    @Size(max = 1000, message = "The path title can be at most 1000 character.")
    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Size(max = 5000, message = "The path description can be at most 1000 character.")
    @Column(name = "description", length = 5000)
    private String description;

    @NotBlank(message = "The path url must be provided.")
    @Size(max = 500, message = "The path url can be at most 500 character.")
    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "parameters", length = 2000)
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<ApiParameter> parameters = new HashSet<>();

    @NotNull(message = "The api spec must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "api_spec_id", nullable = false, referencedColumnName = "id")
    private ApiSpec apiSpec;

    @ManyToMany(
        cascade = CascadeType.MERGE,
        fetch = FetchType.EAGER,
        targetEntity = ApiPathMetadata.class)
    @JoinTable(
        schema = ATHENA_OPENAPI_SCHEMA,
        name = "server_path_metadata_mid",
        joinColumns = {@JoinColumn(name = "path_id")},
        inverseJoinColumns = {@JoinColumn(name = "metadata_id")}
    )
    private Set<ApiPathMetadata> metadata = new HashSet<>();

    public void addParameter(String type, String name) {
        parameters.add(new ApiParameter().setType(type).setName(name));
    }

    public void addMetadata(String name, String value) {
        metadata.add(new ApiPathMetadata().setValue(value).setName(name));
    }
}
