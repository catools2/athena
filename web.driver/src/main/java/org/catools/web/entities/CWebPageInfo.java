package org.catools.web.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CWebPageInfo {
  private String url = "";
  private String title = "";
}
