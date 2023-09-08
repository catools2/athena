package org.catools.common.tests;

import org.catools.common.collections.CHashMap;

import java.util.Map;

public class CTestStateData extends CHashMap<String, Object> {
  public CTestStateData() {
  }

  public CTestStateData(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public CTestStateData(int initialCapacity) {
    super(initialCapacity);
  }

  public CTestStateData(Map<? extends String, ?> m) {
    super(m);
  }

  public void updateDataState(String key, Object value) {
    put(key, value);
  }

  public <T> T getDataState(String key) {
    return (T) get(key);
  }
}
