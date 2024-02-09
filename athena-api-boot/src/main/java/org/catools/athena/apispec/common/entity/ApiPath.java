package org.catools.athena.apispec.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.catools.athena.apispec.common.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;


@Entity
@Table(name = "api_path", schema = ATHENA_OPENAPI_SCHEMA)
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class ApiPath implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "method", length = 10, nullable = false)
  private String method;

  @Column(name = "title", length = 1000)
  private String title;

  @Column(name = "description", length = 5000)
  private String description;

  @Column(name = "url", length = 500, nullable = false)
  private String url;

  @Column(name = "first_time_seen", columnDefinition = "TIMESTAMPTZ")
  private Instant firstTimeSeen;

  @Column(name = "last_sync_time", columnDefinition = "TIMESTAMPTZ")
  private Instant lastSyncTime;

  @Column(name = "parameters", length = 2000)
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, String> parameters = new HashMap<>();

  @NotNull(message = "The api spec must be provided.")
  @ManyToOne
  @JoinColumn(name = "spec_id", nullable = false, referencedColumnName = "id")
  private ApiSpec spec;

  @ManyToMany(fetch = FetchType.EAGER, targetEntity = ApiPathMetadata.class)
  @JoinTable(schema = ATHENA_OPENAPI_SCHEMA, name = "path_metadata_mid", joinColumns = {@JoinColumn(name = "path_id")}, inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<ApiPathMetadata> metadata;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    ApiPath apiPath = (ApiPath) o;

    EqualsBuilder equalsBuilder = new EqualsBuilder();

    if (spec != null && apiPath.spec != null) {
      equalsBuilder.append(spec.getProject(), apiPath.spec.getProject());
      equalsBuilder.append(spec.getVersion(), apiPath.spec.getVersion());
      equalsBuilder.append(spec.getTitle(), apiPath.spec.getTitle());
      equalsBuilder.append(spec.getName(), apiPath.spec.getName());
    } else if (spec != null) {
      equalsBuilder.append(spec.getName(), null);
    } else if (apiPath.spec != null) {
      equalsBuilder.append(null, apiPath.spec.getName());
    }

    return equalsBuilder.append(id, apiPath.id).append(method, apiPath.method).append(title, apiPath.title).append(description, apiPath.description).append(url, apiPath.url).append(firstTimeSeen, apiPath.firstTimeSeen).append(lastSyncTime, apiPath.lastSyncTime).append(parameters, apiPath.parameters).append(metadata, apiPath.metadata).isEquals();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
    hashCodeBuilder.append(spec != null ? spec.getProject() : "");
    hashCodeBuilder.append(spec != null ? spec.getVersion() : "");
    hashCodeBuilder.append(spec != null ? spec.getTitle() : "");
    hashCodeBuilder.append(spec != null ? spec.getName() : "");
    return hashCodeBuilder.append(id).append(method).append(title).append(description).append(url).append(firstTimeSeen).append(lastSyncTime).append(parameters).append(metadata).toHashCode();
  }
}
