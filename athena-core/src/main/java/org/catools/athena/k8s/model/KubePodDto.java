package org.catools.athena.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(chain = true)
public class KubePodDto {
    private String name;
    private String uid;
    private Set<KubeContainerDto> containers = new HashSet<>();
    private Set<KubeContainerDto> ephemeralContainers = new HashSet<>();
    private Set<KubeContainerDto> initContainers = new HashSet<>();
    private Map<String, String> metadata = new HashMap<>();
    private Map<String, String> annotations = new HashMap<>();
    private Map<String, String> labels = new HashMap<>();
    private KubePodSpecDto spec;
    private KubePodStatusDto status;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
