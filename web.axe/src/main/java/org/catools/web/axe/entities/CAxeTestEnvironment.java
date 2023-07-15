package org.catools.web.axe.entities;

import lombok.Data;

@Data
public class CAxeTestEnvironment {
  private String orientationType;
  private String userAgent;
  private int windowWidth;
  private int orientationAngle;
  private int windowHeight;
}
