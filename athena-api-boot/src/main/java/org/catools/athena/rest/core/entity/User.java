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
@Table(name = "user", schema = ATHENA_CORE_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The user name must be provided.")
    @Size(max = 300, message = "The user name can be at most 300 character.")
    @Column(name = "name", length = 300, unique = true, nullable = false)
    private String name;
}
