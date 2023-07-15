package org.catools.web.axe.entities;

import lombok.Data;
import org.catools.common.collections.CSet;

@Data
public class CAxeNodeInfo {
  private String html;
  private CSet<String> target = new CSet<>();
  private CSet<CAxeInfo> all = new CSet<>();
  private CSet<CAxeInfo> any = new CSet<>();
  private CSet<CAxeInfo> none = new CSet<>();
}
