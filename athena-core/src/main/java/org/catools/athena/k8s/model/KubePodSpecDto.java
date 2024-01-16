package org.catools.athena.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class KubePodSpecDto {
    private Map<String, String> nodeSelector = new HashMap<>();
    private String hostname;
    private String nodeName;
}
