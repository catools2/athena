package org.catools.athena.kube.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class PodDto {
    private Long id;
    private String uid;
    private String name;
    private String namespace;
    private String hostname;
    private String nodeName;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private String project;
    private PodStatusDto status;
    private Set<MetadataDto> metadata = new HashSet<>();
    private Set<MetadataDto> annotations = new HashSet<>();
    private Set<MetadataDto> labels = new HashSet<>();
    private Set<MetadataDto> selectors = new HashSet<>();
}
