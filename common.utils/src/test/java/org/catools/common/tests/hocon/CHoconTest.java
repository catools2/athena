package org.catools.common.tests.hocon;

import org.catools.common.hocon.CHocon;
import org.testng.annotations.Test;

public class CHoconTest {

  @Test
  public void testReload() {
    CHocon.asObject("catools");
  }
}