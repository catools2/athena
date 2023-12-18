package org.catools.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CKubePod {

  private String name;
  private String uid;
  private CKubeContainers containers = new CKubeContainers();
  private CKubeContainers ephemeralContainers = new CKubeContainers();
  private CKubeContainers initContainers = new CKubeContainers();
  private CMap<String, String> metadata = new CHashMap<>();
  private CMap<String, String> annotations = new CHashMap<>();
  private CMap<String, String> labels = new CHashMap<>();
  private CKubePodSpec spec;
  private CKubePodStatus status;
  private Date createdAt;
  private Date deletedAt;
}
