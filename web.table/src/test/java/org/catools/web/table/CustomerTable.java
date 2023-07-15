package org.catools.web.table;

import org.catools.web.drivers.CDriver;

public class CustomerTable extends CWebTable<CDriver, CustomerTableRow> {
  public CustomerTable(CDriver driver) {
    super("Customers", driver, "//table[@id='customers']");
    setTHeadXpath("/tbody");
    setHeaderRowXpath("/tr[1]");
    setRowXpath("/tr[position()>1]");
  }

  @Override
  public CustomerTableRow getRecord(int idx) {
    return new CustomerTableRow("Row" + idx, driver, idx, this);
  }
}
