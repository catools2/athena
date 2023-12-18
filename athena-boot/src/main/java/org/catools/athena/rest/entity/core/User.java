package org.catools.athena.rest.entity.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.config.AthenaConstant.ATHENA_SCHEMA;

@Entity
@Table(name = "user", schema = ATHENA_SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank
  @Size(max = 300)
  @Column(name = "name", length = 300, unique = true, nullable = false)
  private String name;
}
