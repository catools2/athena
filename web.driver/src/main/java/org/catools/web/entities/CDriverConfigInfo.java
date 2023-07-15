package org.catools.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CDriverConfigInfo {
  private String url = "";
  private String title = "";
  private String windowHandler = "";
}
