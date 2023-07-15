package org.catools.web.axe.entities;

import lombok.Data;

@Data
public class CAxePage {
  private String title;
  private String url;
  private CAxeTestEnvironment testEnvironment;
  private final CAxePasses passes = new CAxePasses();
  private final CAxeViolations violations = new CAxeViolations();
}
