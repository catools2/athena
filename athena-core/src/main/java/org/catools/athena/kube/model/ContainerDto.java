package org.catools.athena.kube.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ContainerDto {
    private Long id;
    private String pod;
    private String type;
    private String name;
    private String image;
    private String imageId;
    private Boolean ready;
    private Boolean started;
    private Integer restartCount;
    private LocalDateTime startedAt;
    private Set<ContainerStateDto> states = new HashSet<>();
    private Set<MetadataDto> metadata = new HashSet<>();
}
