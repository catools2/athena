package org.catools.athena.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KubeContainerStateInfoDto {
    private String message;
    private String reason;
}
