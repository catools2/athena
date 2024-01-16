package org.catools.athena.rest.pipeline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.NameValuePair;

import java.io.Serializable;

import static org.catools.athena.rest.pipeline.config.PipelineConstant.ATHENA_PIPELINE_SCHEMA;


@Entity
@Table(name = "pipeline_metadata", schema = ATHENA_PIPELINE_SCHEMA)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PipelineMetadata implements NameValuePair, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank
    @Size(max = 2000)
    @Column(name = "value", length = 2000, nullable = false)
    private String value;
}
