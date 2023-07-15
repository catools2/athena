package org.catools.reportportal.tests.utils;

import com.epam.ta.reportportal.ws.model.attribute.ItemAttributesRQ;
import org.catools.common.collections.CSet;
import org.catools.common.tests.CTest;
import org.catools.reportportal.utils.CReportPortalAttributeUtil;
import org.testng.annotations.Test;

public class CReportPortalAttributeUtilTest extends CTest {

  @Test
  public void testGetSingleKeyAttributes() {
    CSet<ItemAttributesRQ> evn = CReportPortalAttributeUtil.getAttributes("env");
    evn.verifySizeEquals(1);
    evn.verifyContains(new ItemAttributesRQ("env"));
  }

  @Test
  public void testGetSingleKeyValueAttributesWithoutTerminator() {
    CSet<ItemAttributesRQ> evn = CReportPortalAttributeUtil.getAttributes("env:qa");
    evn.verifySizeEquals(1);
    evn.verifyContains(new ItemAttributesRQ("env", "qa"));
  }

  @Test
  public void testGetSingleKeyValueAttributesWithTerminator() {
    CSet<ItemAttributesRQ> evn = CReportPortalAttributeUtil.getAttributes("env:qa;");
    evn.verifySizeEquals(1);
    evn.verifyContains(new ItemAttributesRQ("env", "qa"));
  }

  @Test
  public void testGetComplexAttributes1() {
    CSet<ItemAttributesRQ> evn = CReportPortalAttributeUtil.getAttributes("k1:v1;k2:v21,v22;k3;k4:v4;");
    evn.verifySizeEquals(4);
    evn.verifyContains(new ItemAttributesRQ("k1", "v1"));
    evn.verifyContains(new ItemAttributesRQ("k2", "v21,v22"));
    evn.verifyContains(new ItemAttributesRQ("k3"));
    evn.verifyContains(new ItemAttributesRQ("k4", "v4"));
  }

  @Test
  public void testGetComplexAttributes2() {
    CSet<ItemAttributesRQ> evn = CReportPortalAttributeUtil.getAttributes("k1:v1;k2:v21,v22;k3;k4:v4;");
    evn.verifySizeEquals(4);
    evn.verifyContains(new ItemAttributesRQ("k1", "v1"));
    evn.verifyContains(new ItemAttributesRQ("k2", "v21,v22"));
    evn.verifyContains(new ItemAttributesRQ("k3"));
    evn.verifyContains(new ItemAttributesRQ("k4", "v4"));
  }
}