package org.catools.athena.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class KubeContainerDto {
    private KubeContainerStateInfoDto terminatedState = new KubeContainerStateInfoDto();
    private KubeContainerStateInfoDto waitingState = new KubeContainerStateInfoDto();
    private Boolean ready;
    private LocalDateTime startedAt;
    private String image;
    private String imageId;
    private String name;
    private Integer restartCount;
    private Boolean started;
}
