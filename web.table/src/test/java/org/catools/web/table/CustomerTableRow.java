package org.catools.web.table;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.interfaces.CMap;
import org.catools.web.drivers.CDriver;

public class CustomerTableRow extends CWebTableRow<CDriver, CWebTable<CDriver, CustomerTableRow>> {

  public CustomerTableRow(String name, CDriver driver, int idx, CWebTable<CDriver, CustomerTableRow> parentTable) {
    super(name, driver, idx, parentTable);
  }

  public CMap<String, String> getRecord() {
    CMap<String, String> values = new CHashMap<>();
    CList<CWebTableCell> cells = readRowCells();
    for (CWebTableCell cell : cells) {
      values.put(cell.header(), cell.value());
    }
    return values;
  }

  @Override
  public String toString() {
    return String.valueOf(getRecord());
  }
}
