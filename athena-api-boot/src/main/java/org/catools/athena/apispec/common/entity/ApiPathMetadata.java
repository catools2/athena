package org.catools.athena.apispec.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;

import java.io.Serializable;

import static org.catools.athena.apispec.common.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;


@Entity
@Table(name = "api_path_metadata", schema = ATHENA_OPENAPI_SCHEMA)
@Getter
@Setter
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
