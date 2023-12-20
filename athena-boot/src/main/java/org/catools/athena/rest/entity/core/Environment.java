package org.catools.athena.rest.entity.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.config.AthenaConstant.ATHENA_SCHEMA;

@Entity
@Table(name = "environment", schema = ATHENA_SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Environment implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @NotBlank
  @Size(max = 10)
  @Column(name = "code", length = 10, unique = true, nullable = false)
  private String code;

  @NotBlank
  @Size(max = 100)
  @Column(name = "name", length = 100)
  private String name;

  @NotNull
  @ManyToOne(
      cascade = CascadeType.MERGE,
      targetEntity = Project.class,
      fetch = FetchType.EAGER)
  @JoinColumn(name = "project_code",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_ENVIRONMENT_PROJECT"))
  private Project project;
}
