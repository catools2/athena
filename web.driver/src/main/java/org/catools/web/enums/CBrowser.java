package org.catools.web.enums;

public enum CBrowser {
  FIREFOX,
  CHROME,
  EDGE;

  public boolean isChrome() {
    return CHROME.equals(this);
  }

  public boolean isFirefox() {
    return FIREFOX.equals(this);
  }

  public boolean isEdge() {
    return EDGE.equals(this);
  }
}
