package org.catools.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CKubeContainerStateInfo {
  private String message;
  private String reason;

}
