package org.catools.web.table;

import org.catools.common.extensions.verify.CVerify;
import org.catools.web.drivers.CDriver;
import org.catools.web.tests.CWebTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(enabled = false)
public class CWebMultiPageTableTest extends CWebTest<CDriver> {

  private CustomerMultiPageTable customerTable;

  @Override
  @BeforeClass(enabled = false)
  public void beforeClass() {
    switchToChromeHeadless();
    getDriver().open("https://mdbootstrap.com/docs/b4/jquery/tables/pagination/");
    customerTable = new CustomerMultiPageTable(getDriver());
  }

  @Test(enabled = false)
  public void testGetRecord() {
    customerTable.getFirst()
        .getRecord()
        .verifyContains("Name", "Airi Satou");
  }

  @Test(enabled = false)
  public void testGetAll() {
    customerTable.getAll().verifySizeEquals(57);
    customerTable.getAll().get(0)
        .getRecord()
        .verifyContains("Name", "Airi Satou");
  }

  @Test(enabled = false)
  public void testGetFirst() {
    customerTable.getFirst("Age", "33")
        .getRecord()
        .verifyContains("Name", "Airi Satou");
  }

  @Test(enabled = false)
  public void testCurrentPageRecordCount() {
    CVerify.Int.equals(customerTable.getCurrentPageRecordCount(), 10);
  }
}