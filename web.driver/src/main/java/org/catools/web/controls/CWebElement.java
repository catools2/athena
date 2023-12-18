package org.catools.web.controls;

import lombok.Getter;
import org.catools.common.extensions.types.CDynamicBooleanExtension;
import org.catools.common.extensions.types.CDynamicNumberExtension;
import org.catools.common.extensions.types.CDynamicStringExtension;
import org.catools.media.model.CScreenShot;
import org.catools.web.drivers.CDriver;
import org.openqa.selenium.By;

import java.awt.image.BufferedImage;

public class CWebElement<DR extends CDriver> implements CWebElementActions<DR> {

  @Getter
  protected final DR driver;

  @Getter
  protected final int waitSec;

  @Getter
  protected final String name;

  @Getter
  protected final By locator;

  public CWebElement(String name, DR driver, By locator) {
    this(name, driver, locator, CDriver.DEFAULT_TIMEOUT);
  }

  public CWebElement(String name, DR driver, By locator, int waitSec) {
    super();
    this.name = name;
    this.driver = driver;
    this.locator = locator;
    this.waitSec = waitSec;
  }

  // Control Extension
  public final CDynamicNumberExtension<Integer> Offset = new CDynamicNumberExtension<>() {
    @Override
    public Integer _get() {
      return getOffset(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Offset";
    }
  };
  public final CDynamicBooleanExtension Staleness = new CDynamicBooleanExtension() {
    @Override
    public Boolean _get() {
      return isStaleness(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Staleness";
    }
  };
  public final CDynamicBooleanExtension Present = new CDynamicBooleanExtension() {
    @Override
    public Boolean _get() {
      return isPresent(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Presence";
    }
  };
  public final CDynamicBooleanExtension Visible = new CDynamicBooleanExtension() {
    @Override
    public Boolean _get() {
      return isVisible(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Visibility";
    }
  };
  public final CDynamicBooleanExtension Enabled = new CDynamicBooleanExtension() {
    @Override
    public Boolean _get() {
      return isEnabled(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Enable State";
    }
  };
  public final CDynamicStringExtension Text = new CDynamicStringExtension() {
    @Override
    public String _get() {
      return getText(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Text";
    }
  };
  public final CDynamicStringExtension Value = new CDynamicStringExtension() {
    @Override
    public String _get() {
      return getValue(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Value";
    }
  };
  public final CDynamicStringExtension InnerHTML = new CDynamicStringExtension() {
    @Override
    public String _get() {
      return getInnerHTML(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Inner HTML";
    }
  };
  public final CDynamicStringExtension TagName = new CDynamicStringExtension() {
    @Override
    public String _get() {
      return getTagName(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Tag Name";
    }
  };

  public final CDynamicStringExtension Css(final String cssKey) {
    return new CDynamicStringExtension() {
      @Override
      public String _get() {
        return getCss(cssKey, 0);
      }

      @Override
      public int getDefaultWaitInSeconds() {
        return waitSec;
      }

      @Override
      public String getVerifyMessagePrefix() {
        return name + " " + cssKey + " CSS value";
      }
    };
  }

  public final CDynamicStringExtension Attribute(final String attribute) {
    return new CDynamicStringExtension() {
      @Override
      public String _get() {
        return getAttribute(attribute, 0);
      }

      @Override
      public int getDefaultWaitInSeconds() {
        return waitSec;
      }

      @Override
      public String getVerifyMessagePrefix() {
        return name + " " + attribute + " Attribute value";
      }
    };
  }

  public final CDynamicStringExtension AriaRole() {
    return new CDynamicStringExtension() {
      @Override
      public String _get() {
        return getAriaRole(0);
      }

      @Override
      public int getDefaultWaitInSeconds() {
        return waitSec;
      }

      @Override
      public String getVerifyMessagePrefix() {
        return name + " AriaRole value";
      }
    };
  }

  public final CScreenShot ScreenShot = new CScreenShot() {
    @Override
    public BufferedImage _get() {
      return getScreenShot(0);
    }

    @Override
    public int getDefaultWaitIntervalInMilliSeconds() {
      return 50;
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return CDriver.DEFAULT_TIMEOUT;
    }

    @Override
    public boolean withWaiter() {
      return true;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Screen Shot";
    }
  };
  public final CDynamicBooleanExtension Selected = new CDynamicBooleanExtension() {
    @Override
    public Boolean _get() {
      return isSelected(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Selected State";
    }
  };
  public final CDynamicBooleanExtension Clickable = new CDynamicBooleanExtension() {
    @Override
    public Boolean _get() {
      return isClickable(0);
    }

    @Override
    public int getDefaultWaitInSeconds() {
      return waitSec;
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Clickable";
    }
  };
}
