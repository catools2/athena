package org.catools.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;

@Data
@Accessors(chain = true)
public class CKubePodSpec {
  private CMap<String, String> nodeSelector = new CHashMap<>();
  private String hostname;
  private String nodeName;
}
