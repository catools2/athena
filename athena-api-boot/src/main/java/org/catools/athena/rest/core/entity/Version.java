package org.catools.athena.rest.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.core.config.CoreConstant.ATHENA_CORE_SCHEMA;

@Entity
@Table(name = "version", schema = ATHENA_CORE_SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Version implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The version code must be provided.")
    @Size(max = 20)
    @Column(name = "code", length = 10, unique = true, nullable = false)
    private String code;

    @NotBlank(message = "The version name must be provided.")
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull(message = "The version project must be provided.")
    @ManyToOne(
            cascade = CascadeType.MERGE,
            targetEntity = Project.class,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_VERSION_PROJECT"))
    private Project project;
}
