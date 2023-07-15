package org.catools.reportportal.utils;

import com.epam.reportportal.service.ReportPortal;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.Level;
import org.catools.common.date.CDate;
import org.catools.common.io.CFile;
import org.catools.common.security.CSensitiveDataMaskingManager;
import org.catools.common.utils.CStringUtil;
import org.catools.reportportal.configs.CRPConfigs;

@UtilityClass
public class CReportPortalUtil {
  public static void sendToReportPortal(Level level, String msg) {
    sendToReportPortal(level, msg, null);
  }

  public static void sendToReportPortal(Level level, String msg, CFile file) {
    if (CRPConfigs.isReportPortalEnable()) {
      String message = CSensitiveDataMaskingManager.mask(msg);
      if (CStringUtil.isNotBlank(message) || file != null) {
        if (file == null) {
          ReportPortal.emitLog(message, level.name(), CDate.now());
        } else {
          ReportPortal.emitLog(message, level.name(), CDate.now(), file);
        }
      }
    }
  }
}
