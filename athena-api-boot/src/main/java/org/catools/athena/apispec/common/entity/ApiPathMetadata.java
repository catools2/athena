package org.catools.athena.apispec.common.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;

import java.io.Serializable;

import static org.catools.athena.apispec.common.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;


@Entity
@Table(name = "api_path_metadata", schema = ATHENA_OPENAPI_SCHEMA)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Accessors(chain = true)
public class ApiPathMetadata implements NameValuePair, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @Column(name = "value", length = 2000, nullable = false)
  private String value;
}
