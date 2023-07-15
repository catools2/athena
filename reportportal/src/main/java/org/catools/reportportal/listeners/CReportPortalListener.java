package org.catools.reportportal.listeners;

import com.epam.reportportal.testng.BaseTestNGListener;
import org.catools.common.functions.CMemoize;
import org.catools.reportportal.service.CReportPortalService;

public class CReportPortalListener extends BaseTestNGListener {
  private static final CMemoize<CReportPortalService> SERVICE = new CMemoize<>(CReportPortalService::new);

  public CReportPortalListener() {
    super(SERVICE.get());
  }
}
