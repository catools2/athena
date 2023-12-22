package org.catools.athena.rest.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.core.config.AthenaCoreConstant.ATHENA_SCHEMA;

@Entity
@Table(name = "project", schema = ATHENA_SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Project implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank
  @Size(max = 5)
  @Column(name = "code", length = 5, unique = true, updatable = false, nullable = false)
  private String code;

  @NotBlank
  @Size(max = 50)
  @Column(name = "name", length = 50, unique = true, nullable = false)
  private String name;
}
