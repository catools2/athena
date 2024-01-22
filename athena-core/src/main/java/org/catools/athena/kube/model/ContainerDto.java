package org.catools.athena.kube.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ContainerDto {
    private Long id;
    private Long podId;
    private String type;
    private String name;
    private String image;
    private String imageId;
    private Boolean ready;
    private Boolean started;
    private Integer restartCount;
    private Instant startedAt;
    private Set<MetadataDto> metadata = new HashSet<>();
}
