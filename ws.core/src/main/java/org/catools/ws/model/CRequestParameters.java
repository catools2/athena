package org.catools.ws.model;

import org.catools.common.collections.CHashMap;

import java.util.Map;

public class CRequestParameters extends CHashMap<String, Object> {
  public CRequestParameters() {
  }

  public CRequestParameters(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public CRequestParameters(int initialCapacity) {
    super(initialCapacity);
  }

  public CRequestParameters(Map<? extends String, ? extends String> m) {
    super(m);
  }
}
