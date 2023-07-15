package org.catools.web.table;

import org.catools.web.drivers.CDriver;
import org.openqa.selenium.By;

public class CustomerMultiPageTable extends CWebMultiPageTable<CDriver, CustomerTableRow> {

  public CustomerMultiPageTable(CDriver driver) {
    super("Customers",
        driver,
        "//table[@id='dtBasicExample']",
        By.xpath("//ul[@class='pagination']//a[.='1']"),
        By.xpath("//ul[@class='pagination']//a[.='Previous']"),
        By.xpath("//ul[@class='pagination']//a[.='Next']"),
        By.xpath("//ul[@class='pagination']//a[.='6']"),
        10,
        10);
  }

  @Override
  public CustomerTableRow getRecord(int idx) {
    return new CustomerTableRow("Row" + idx, driver, idx, this);
  }

  @Override
  public String getCurrentPageNumber() {
    return null;
  }
}
