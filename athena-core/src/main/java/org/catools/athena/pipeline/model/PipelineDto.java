package org.catools.athena.pipeline.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class PipelineDto {
    private Long id;

    private String name;

    private String description;

    private String number;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String environmentCode;

    private Set<MetadataDto> metadata = new HashSet<>();
}
