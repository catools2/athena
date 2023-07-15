package org.catools.web.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

public class CDriverConfigs {

  public static boolean waitCompleteReadyStateBeforeEachAction() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_DRIVER_WAIT_READY_STATE_BEFORE_ACTION);
  }

  public static boolean waitCompleteReadyStateAfterEachAction() {
    return CHocon.asBoolean(Configs.CATOOLS_WEB_DRIVER_WAIT_READY_STATE_AFTER_ACTION);
  }

  public static int getTimeout() {
    return CHocon.asInteger(Configs.CATOOLS_WEB_DRIVER_BROWSER_TIMEOUT);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_WEB_DRIVER_WAIT_READY_STATE_BEFORE_ACTION("catools.web.driver.wait_ready_state_before_action"),
    CATOOLS_WEB_DRIVER_WAIT_READY_STATE_AFTER_ACTION("catools.web.driver.wait_ready_state_after_action"),
    CATOOLS_WEB_DRIVER_BROWSER_TIMEOUT("catools.web.driver.browser_timeout");

    private final String path;
  }
}
