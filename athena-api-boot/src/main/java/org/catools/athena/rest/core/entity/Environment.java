package org.catools.athena.rest.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.core.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "environment", schema = ATHENA_CORE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Environment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The environment code must be provided.")
    @Size(max = 10, message = "The environment code can be at most 10 character.")
    @Column(name = "code", length = 10, unique = true, nullable = false)
    private String code;

    @NotBlank(message = "The environment name must be provided.")
    @Size(max = 50, message = "The environment name can be at most 50 character.")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull(message = "The environment project must be provided.")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id")
    private Project project;
}
