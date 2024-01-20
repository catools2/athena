package org.catools.athena.kube.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ContainerStateInfoDto {
    private Long id;
    private String message;
    private String reason;
}
