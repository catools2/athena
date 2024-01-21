package org.catools.athena.rest.apispec.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.apispec.model.NameTypePair;

import java.io.Serializable;

import static org.catools.athena.rest.apispec.config.ApiSpecConstant.ATHENA_OPENAPI_SCHEMA;


@Entity
@Table(name = "api_parameter", schema = ATHENA_OPENAPI_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiParameter implements NameTypePair, Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank(message = "The parameter type must be provided.")
  @Size(max = 100, message = "The parameter type can be at most 100 character.")
  @Column(name = "type", length = 100, nullable = false)
  private String type;

  @NotBlank(message = "The parameter name must be provided.")
  @Size(max = 100, message = "The parameter name can be at most 100 character.")
  @Column(name = "name", length = 100, nullable = false)
  private String name;
}
