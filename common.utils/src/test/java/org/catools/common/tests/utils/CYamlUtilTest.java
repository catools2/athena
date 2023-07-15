package org.catools.common.tests.utils;

import org.catools.common.utils.CResourceUtil;
import org.catools.common.utils.CYamlUtil;
import org.testng.annotations.Test;

public class CYamlUtilTest {

  @Test
  public void doesNotCrash() {
    CYamlUtil.toString(CResourceUtil.getString("testYaml.yaml", null));
  }
}
