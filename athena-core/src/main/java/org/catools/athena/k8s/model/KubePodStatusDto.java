package org.catools.athena.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KubePodStatusDto {
    private String message;
    private String reason;
    private String status;
    private String phase;
}
