package org.catools.athena.apispec.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiSpecDto implements Serializable {

    private Long id;
    private String version;
    private String name;
    private String title;
    private String project;
    private Instant firstTimeSeen;
    private Instant lastTimeSeen;
    private Set<MetadataDto> metadata = new HashSet<>();

}
