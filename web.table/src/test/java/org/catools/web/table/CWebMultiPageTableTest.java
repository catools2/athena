package org.catools.web.table;

import org.catools.common.extensions.verify.CVerify;
import org.catools.web.drivers.CDriver;
import org.catools.web.tests.CWebTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class CWebMultiPageTableTest extends CWebTest<CDriver> {

  private CustomerMultiPageTable customerTable;

  @Override
  @BeforeClass
  public void beforeClass() {
    switchToChromeHeadless();
    getDriver().open("https://mdbootstrap.com/docs/b4/jquery/tables/pagination/");
    customerTable = new CustomerMultiPageTable(getDriver());
  }

  @Test
  public void testGetRecord() {
    customerTable.getFirst()
        .getRecord()
        .verifyContains("Name", "Airi Satou");
  }

  @Test
  public void testGetAllSize() {
    customerTable.getAll().verifySizeEquals(57);
  }

  @Test
  public void testGetAllContains() {
    customerTable.verifyHas(r -> "Airi Satou".equals(r.getRecord().get("Name")));
  }

  @Test
  public void testGetFirst() {
    customerTable.getFirst("Age", "33")
        .getRecord()
        .verifyContains("Name", "Airi Satou");
  }

  @Test
  public void testCurrentPageRecordCount() {
    CVerify.Int.equals(customerTable.getCurrentPageRecordCount(), 10);
  }
}