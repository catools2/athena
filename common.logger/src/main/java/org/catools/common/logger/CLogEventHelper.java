package org.catools.common.logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.LogEvent;

public class CLogEventHelper {

  public static String getMessage(LogEvent event) {
    if (event.getMessage() == null) {
      return null;
    }

    return StringUtils.defaultIfBlank(
        event.getMessage().getFormattedMessage(),
        StringUtils.join(event.getMessage().getParameters(), "\n"));
  }
}
