package org.catools.web.controls;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.collections.CList;
import org.catools.common.extensions.types.CDynamicStringExtension;
import org.catools.common.extensions.types.interfaces.CDynamicCollectionExtension;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.utils.CStringUtil;
import org.catools.web.drivers.CDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

import java.util.Collection;
import java.util.regex.Pattern;

@Slf4j
public class CWebSelect<DR extends CDriver> extends CWebElement<DR> {

  public CWebSelect(String name, DR driver, By locator) {
    super(name, driver, locator);
  }

  public CWebSelect(String name, DR driver, By locator, int waitSec) {
    super(name, driver, locator, waitSec);
  }

  // Extensions
  public CDynamicStringExtension SelectedText = new CDynamicStringExtension() {
    @Override
    public String _get() {
      return getSelectedText();
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Selected Text";
    }
  };

  public CDynamicStringExtension SelectedValue = new CDynamicStringExtension() {
    @Override
    public String _get() {
      return getSelectedValue();
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Selected Value";
    }
  };

  public CDynamicCollectionExtension<String> Values = new CDynamicCollectionExtension<>() {
    @Override
    public Collection<String> _get() {
      return getValues();
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Values";
    }
  };

  public CDynamicCollectionExtension<String> Texts = new CDynamicCollectionExtension<>() {
    @Override
    public Collection<String> _get() {
      return getTexts();
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name + " Texts";
    }
  };

  public CDynamicCollectionExtension<String> SelectedTexts = new CDynamicCollectionExtension<>() {
    @Override
    public Collection<String> _get() {
      return getSelectedTexts();
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name;
    }
  };

  public CDynamicCollectionExtension<String> SelectedValues = new CDynamicCollectionExtension<>() {
    @Override
    public Collection<String> _get() {
      return getSelectedValues();
    }

    @Override
    public String getVerifyMessagePrefix() {
      return name;
    }
  };

  // Getters
  public CList<String> getSelectedTexts() {
    return getSelectedTexts(0);
  }

  public CList<String> getSelectedTexts(int waitSec) {
    return waitUntil("Get Selected Texts", waitSec, el -> new CList<>(getSelect(el).getAllSelectedOptions()).mapToList(e -> e.getText().trim()));
  }

  public CList<String> getSelectedValues() {
    return getSelectedValues(0);
  }

  public CList<String> getSelectedValues(int waitSec) {
    return waitUntil("Get Selected Values", waitSec, el -> new CList<>(getSelect(el).getAllSelectedOptions()).mapToList(e -> e.getAttribute("value").trim()));
  }

  // Getter
  public String getSelectedText() {
    return getSelectedText(0);
  }

  public String getSelectedText(int waitSecs) {
    return waitUntil("Get Selected Text", waitSecs, el -> getSelect(el).getFirstSelectedOption().getText().trim());
  }

  public String getSelectedValue() {
    return getSelectedValue(0);
  }

  public String getSelectedValue(int waitSecs) {
    return waitUntil("Get Selected Value", waitSecs, el -> getSelect(el).getFirstSelectedOption().getAttribute("value").trim());
  }

  public CList<String> getTexts() {
    return getTexts(0);
  }

  public CList<String> getTexts(int waitSecs) {
    return waitUntil("Get Text", waitSecs, el -> new CList<>(getSelect(el).getOptions()).mapToList(e -> e.getText().trim()));
  }

  public CList<String> getValues() {
    return getValues(0);
  }

  public CList<String> getValues(int waitSecs) {
    return waitUntil("Get Values", waitSecs, el -> new CList<>(getSelect(el).getOptions()).mapToList(e -> e.getAttribute("value").trim()));
  }

  // Actions
  public void selectFirstItem() {
    selectFirstItem(waitSec);
  }

  public void selectFirstItem(int waitSec) {
    selectByIndex(0, waitSec);
  }

  public void selectByPartialText(String pattern) {
    selectByPartialText(pattern, DEFAULT_TIMEOUT);
  }

  public void selectByPartialText(String pattern, int waitSec) {
    selectByTextPattern("^.*" + pattern + ".*$", waitSec);
  }

  public void selectByText(String value) {
    selectByText(value, waitSec);
  }

  public void selectByText(String value, int waitSec) {
    waitUntil("Select By Text", waitSec, el -> {
      getSelect(el).selectByVisibleText(StringUtils.defaultString(value));
      return true;
    });
  }

  public void selectByValue(String value) {
    selectByValue(value, waitSec);
  }

  public void selectByValue(String value, int waitSec) {
    waitUntil("Select By Value", waitSec, el -> {
      getSelect(el).selectByValue(StringUtils.defaultString(value));
      return true;
    });
  }

  public void selectByIndex(int i) {
    selectByIndex(i, waitSec);
  }

  public void selectByIndex(int i, int waitSec) {
    waitUntil("Select By Index", waitSec, el -> {
      getSelect(el).selectByIndex(i);
      return true;
    });
  }

  public void selectByTextPattern(String pattern) {
    selectByTextPattern(pattern, waitSec);
  }

  public void selectByTextPattern(String pattern, int waitSec) {
    waitUntil("Select By Text Pattern", waitSec, el -> {
      ISelect dropdown = getSelect(el);
      int index = CList.of(dropdown.getOptions()).indexOf(e -> Pattern.matches(pattern, CStringUtil.strip(e.getText(), "\\n").trim()));
      CVerify.Int.greaterOrEqual(index, 0, pattern + " found in options.");
      getSelect(el).selectByIndex(index);
      return true;
    });
  }

  public void set(boolean state) {
    set(state, waitSec);
  }

  public void set(boolean state, int waitSec) {
    if (state) {
      select(waitSec);
    } else {
      deselect(waitSec);
    }
  }

  protected ISelect getSelect(WebElement el) {
    return new Select(el);
  }
}
