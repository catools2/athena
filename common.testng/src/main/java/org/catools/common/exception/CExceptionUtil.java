package org.catools.common.exception;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CList;
import org.catools.common.utils.CStringUtil;

@UtilityClass
@Slf4j
public class CExceptionUtil {
  public static String getMessageAndStackTrace(Throwable t) {
    if (t == null) {
      return CStringUtil.EMPTY;
    }
    return t + "\n\n" + getStackTrace(t);
  }

  public static String getStackTrace(Throwable t) {
    return t == null
        ? CStringUtil.EMPTY
        : new CList<>(t.getStackTrace()).join(System.lineSeparator());
  }

  public static void printCurrentStackTrace() {
    log.trace(getCurrentStackTrace());
  }

  private static String getCurrentStackTrace() {
    return new CList<>(Thread.currentThread().getStackTrace()).join(System.lineSeparator());
  }
}
