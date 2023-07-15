package org.catools.web.forms;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.catools.web.drivers.CDriver;
import org.catools.web.factory.CWebElementFactory;
import org.catools.web.pages.CWebComponent;

@Slf4j
public abstract class CWebForm<DR extends CDriver> implements CWebComponent<DR> {

  @Getter
  @Setter(AccessLevel.NONE)
  protected final DR driver;

  public CWebForm(DR driver) {
    this.driver = driver;
    CWebElementFactory.initElements(this);
  }
}
