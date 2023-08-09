package org.catools.web.drivers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.types.CDynamicBooleanExtension;
import org.catools.common.extensions.types.CDynamicStringExtension;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.catools.web.drivers.CDriver.DEFAULT_TIMEOUT;

@Slf4j
public class CWebAlert<DR extends CDriver> {
  @Getter
  protected final Logger logger = LoggerFactory.getLogger(CWebAlert.class);

  @Getter
  protected final DR driver;

  public CWebAlert(DR driver) {
    this.driver = driver;
  }

  public final CDynamicStringExtension Message =
      new CDynamicStringExtension() {
        @Override
        public String _get() {
          Alert alert = getAlert();
          return alert == null ? "" : alert.getText();
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return DEFAULT_TIMEOUT;
        }

        @Override
        public String getVerifyMessagePrefix() {
          return "Alert Text";
        }
      };

  public final CDynamicBooleanExtension Present =
      new CDynamicBooleanExtension() {
        @Override
        public Boolean _get() {
          return isPresent();
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return DEFAULT_TIMEOUT;
        }

        @Override
        public String getVerifyMessagePrefix() {
          return "Alert Presence";
        }
      };

  // Getter
  public boolean isPresent() {
    return isPresent(0);
  }

  public final boolean isPresent(int waitSec) {
    return driver.waitUntil("Is Present", waitSec, false, driver -> {
      try {
        return driver != null && getAlert() != null;
      } catch (NoAlertPresentException var3) {
        return false;
      }
    });
  }

  public boolean isNotPresent() {
    return isNotPresent(0);
  }

  public final boolean isNotPresent(int waitSec) {
    return driver.waitUntil("Is Not Present", waitSec, false, driver -> {
      try {
        return driver != null && getAlert() == null;
      } catch (NoAlertPresentException var3) {
        return true;
      }
    });
  }

  // Method
  public <A extends CWebAlert> A closeIfPresent() {
    closeIfPresent(true, DEFAULT_TIMEOUT);
    return (A) this;
  }

  public <A extends CWebAlert> A closeIfPresent(boolean accept) {
    closeIfPresent(accept, DEFAULT_TIMEOUT);
    return (A) this;
  }

  public <A extends CWebAlert> A closeIfPresent(int waitSec) {
    closeIfPresent(true, waitSec);
    return (A) this;
  }

  public <A extends CWebAlert> A closeIfPresent(boolean accept, int waitSec) {
    if (Present.waitIsTrue(waitSec)) {
      return close(accept);
    }
    return (A) this;
  }

  public <A extends CWebAlert> A close() {
    close(true, DEFAULT_TIMEOUT);
    return (A) this;
  }

  public <A extends CWebAlert> A close(boolean accept) {
    close(accept, DEFAULT_TIMEOUT);
    return (A) this;
  }

  public <A extends CWebAlert> A close(int waitSec) {
    close(true, waitSec);
    return (A) this;
  }

  public <A extends CWebAlert> A close(boolean accept, int waitSec) {
    verifyIsPresent(waitSec);
    return accept ? accept() : cancel();
  }

  public <A extends CWebAlert> A accept() {
    accept(DEFAULT_TIMEOUT);
    return (A) this;
  }

  public <A extends CWebAlert> A accept(int waitSec) {
    verifyIsPresent(waitSec);
    getAlert().accept();
    return (A) this;
  }

  public <A extends CWebAlert> A cancel() {
    cancel(DEFAULT_TIMEOUT);
    return (A) this;
  }

  public <A extends CWebAlert> A cancel(int waitSec) {
    verifyIsPresent(waitSec);
    getAlert().dismiss();
    return (A) this;
  }

  public <A extends CWebAlert> A verifyIsPresent() {
    return verifyIsPresent(DEFAULT_TIMEOUT);
  }

  public <A extends CWebAlert> A verifyIsPresent(int waitSec) {
    Present.verifyIsTrue(waitSec, "Verify that alert is present");
    return (A) this;
  }

  public <A extends CWebAlert> A verifyMessage(String message) {
    return verifyMessage(message, DEFAULT_TIMEOUT);
  }

  public <A extends CWebAlert> A verifyMessage(String message, int waitSec) {
    verifyIsPresent(waitSec);
    Message.verifyEquals(message, 0, "Verify that alert has a right message");
    return (A) this;
  }

  private Alert getAlert() {
    return driver.performActionOnDriver(
        "Get Alert",
        driver -> {
          try {
            return driver.switchTo().alert();
          } catch (NoAlertPresentException e) {
            return null;
          }
        });
  }
}
