package org.catools.athena.rest.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.core.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "project", schema = ATHENA_CORE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The project code must be provided.")
    @Size(max = 10)
    @Column(name = "code", length = 10, unique = true, updatable = false, nullable = false)
    private String code;

    @NotBlank(message = "The project name must be provided.")
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
