package org.catools.common.extensions.wait.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.date.CDate;
import org.catools.common.extensions.CTypeExtensionConfigs;
import org.catools.common.extensions.states.interfaces.CBaseState;
import org.catools.common.utils.CSleeper;
import org.catools.common.utils.CStringUtil;

import java.util.function.Predicate;

/**
 * CBaseWaiter is an interface to hold shared method between all waiter classes.
 */
public interface CBaseWaiter<O> extends CBaseState<O> {

  /**
   * The default interval in milliseconds during wait for defined state. It is possible to set this
   * value for whole system using {@code EXTENSION_DEFAULT_WAIT_INTERVAL_IN_MILLIS} config parameter
   * or just overwrite this method for particular implementation
   *
   * @return the interval time in milliseconds
   */
  @JsonIgnore
  default int getDefaultWaitIntervalInMilliSeconds() {
    return CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds();
  }

  /**
   * The default max time to wait for defined state. It is possible to set this value for whole
   * system using {@code EXTENSION_DEFAULT_WAIT_IN_SECONDS} config parameter or just overwrite this
   * method for particular implementation.
   *
   * @return max time to wait for defined state.
   */
  @JsonIgnore
  default int getDefaultWaitInSeconds() {
    return CTypeExtensionConfigs.getDefaultWaitInSeconds();
  }

  /**
   * Build default message
   *
   * @param methodDescription
   * @param params
   * @return default message for waiters and verifier
   */
  default String getDefaultMessage(final String methodDescription, final Object... params) {
    return getDefaultMessage(String.format(methodDescription, params));
  }

  /**
   * Build default message
   *
   * @param methodDescription
   * @return default message for waiters and verifier
   */
  default String getDefaultMessage(final String methodDescription) {
    if (CStringUtil.isBlank(getVerifyMessagePrefix())) {
      return "Verify " + methodDescription + ".";
    }
    return String.format("Verify %s %s.", getVerifyMessagePrefix(), methodDescription);
  }

  /**
   * Get the prefix for message
   *
   * @return the prefix for message
   */
  @JsonIgnore
  default String getVerifyMessagePrefix() {
    return "";
  }


  default boolean _waiter(Predicate<O> waitMethod, final int waitInSeconds, final int intervalInMilliSeconds) {
    boolean isTimeOuted = false;
    Throwable lastException = null;

    CDate deadLine = new CDate().addSeconds(waitInSeconds);
    // A little ugly code for sake of debugging and branch readability
    while (true) {
      try {
        if (waitMethod.test(_get())) {
          break;
        }
      } catch (Exception e) {
        lastException = e;
      }

      if (deadLine.before(CDate.now())) {
        isTimeOuted = true;
        break;
      }

      if ((waitInSeconds * 1000) > intervalInMilliSeconds) {
        CSleeper.sleepTight(intervalInMilliSeconds);
      }
    }

    if (isTimeOuted && lastException != null) {
      if (lastException instanceof RuntimeException exception) {
        throw exception;
      }
      throw new RuntimeException(lastException);
    }

    return !isTimeOuted;
  }
}
