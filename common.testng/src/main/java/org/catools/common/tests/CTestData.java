package org.catools.common.tests;

import org.catools.common.collections.CHashMap;

import java.util.Map;

public class CTestData extends CHashMap<String, Object> {
  public CTestData() {
  }

  public CTestData(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  public CTestData(int initialCapacity) {
    super(initialCapacity);
  }

  public CTestData(Map<? extends String, ?> m) {
    super(m);
  }

  public void updateDataState(String key, Object value) {
    put(key, value);
  }

  public <T> T getDataState(String key) {
    return (T) get(key);
  }
}
