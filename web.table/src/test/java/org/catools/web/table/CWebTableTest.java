package org.catools.web.table;

import org.catools.web.drivers.CDriver;
import org.catools.web.tests.CWebTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CWebTableTest extends CWebTest<CDriver> {

  private CustomerTable customerTable;

  @Override
  @BeforeClass(enabled = false)
  public void beforeClass() {
    switchToChromeHeadless();
    getDriver().open("https://www.w3schools.com/html/html_tables.asp");
    customerTable = new CustomerTable(getDriver());
  }

  @Test(enabled = false)
  public void testGetRecord() {
    customerTable.getFirst()
        .getRecord()
        .verifyContains("Company", "Alfreds Futterkiste");
  }

  @Test(enabled = false)
  public void testGetAll() {
    customerTable.getAll().verifySizeEquals(6);
    customerTable.getAll().get(0)
        .getRecord()
        .verifyContains("Company", "Alfreds Futterkiste");
  }

  @Test(enabled = false)
  public void testGetFirst() {
    customerTable.getFirst("Company", "Island Trading")
        .getRecord()
        .verifyContains("Company", "Island Trading");
  }
}