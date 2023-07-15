package org.catools.web.axe.entities;

import lombok.Data;
import org.catools.common.collections.CSet;

@Data
public class CAxeNode {
  private String id;
  private String description;
  private String help;
  private String helpUrl;
  private CSet<String> tags = new CSet<>();
  private CSet<CAxeNodeInfo> nodes = new CSet<>();
}
