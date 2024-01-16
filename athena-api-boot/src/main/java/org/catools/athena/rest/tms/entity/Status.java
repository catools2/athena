package org.catools.athena.rest.tms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.catools.athena.rest.tms.config.TmsConstant.ATHENA_TMS_SCHEMA;


@Entity
@Table(name = "status", schema = ATHENA_TMS_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The item status code must be provided.")
    @Size(max = 10, message = "The item status code can be at most 10 character.")
    @Column(name = "code", length = 10, unique = true, nullable = false)
    private String code;

    @NotBlank(message = "The item status name must be provided.")
    @Size(max = 50, message = "The item status name can be at most 50 character.")
    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
