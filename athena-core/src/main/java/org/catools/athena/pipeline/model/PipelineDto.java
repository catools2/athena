package org.catools.athena.pipeline.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class PipelineDto {
    private Long id;

    private String name;

    private String description;

    private String number;

  private Instant startInstant;

  private Instant endInstant;

    private String environmentCode;

    private Set<MetadataDto> metadata = new HashSet<>();
}
