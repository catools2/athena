package org.catools.common.tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.catools.common.collections.CList;
import org.catools.common.utils.CStringUtil;

public class CTestMetadata extends CList<CTestMetadata.MetaData> {

  public void add(String name, String value) {
    add(new MetaData(name, value));
  }

  public boolean has(String name, String value) {
    return hasNot(m -> CStringUtil.equals(m.name, name) && CStringUtil.equals(m.value, value));
  }

  public boolean hasNot(String name, String value) {
    return hasNot(m -> CStringUtil.equals(m.name, name) && CStringUtil.equals(m.value, value));
  }

  public void addIfNotExists(String name, String value) {
    if (hasNot(name, value)) {
      add(name, value);
    }
  }

  @Data
  @AllArgsConstructor
  public static class MetaData {
    private String name;
    private String value;
  }
}
