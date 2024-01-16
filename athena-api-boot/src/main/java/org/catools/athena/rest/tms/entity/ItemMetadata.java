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
@Table(name = "metadata", schema = ATHENA_TMS_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ItemMetadata implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "The item metadata name must be provided.")
    @Size(max = 100, message = "The item metadata name can be at most 100 character.")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "The item metadata value must be provided.")
    @Size(max = 2000, message = "The item metadata value can be at most 2000 character.")
    @Column(name = "value", length = 2000, nullable = false)
    private String value;
}
