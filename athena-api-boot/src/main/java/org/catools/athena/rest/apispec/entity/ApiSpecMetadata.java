package org.catools.athena.rest.apispec.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;

import java.io.Serializable;

import static org.catools.athena.rest.apispec.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;


@Entity
@Table(name = "api_spec_metadata", schema = ATHENA_OPENAPI_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiSpecMetadata implements NameValuePair, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank(message = "The metadata name must be provided.")
  @Size(max = 100, message = "The metadata name can be at most 100 character.")
  @Column(name = "name", length = 100, nullable = false)
  private String name;

  @NotBlank(message = "The metadata value must be provided.")
  @Size(max = 2000, message = "The metadata value can be at most 2000 character.")
  @Column(name = "value", length = 2000, nullable = false)
  private String value;
}
