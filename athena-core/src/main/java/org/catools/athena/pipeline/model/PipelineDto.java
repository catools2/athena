package org.catools.athena.pipeline.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class PipelineDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 300)
    private String description;

    @NotBlank
    @Size(max = 100)
    private String number;

    @NotNull
    private Date startDate;

    private Date endDate;

    @NotBlank
    @Size(min = 1, max = 10)
    private String environmentCode;

    private Set<MetadataDto> metadata = new HashSet<>();
}
