package org.catools.web.table;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.utils.CStringUtil;
import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;
import org.catools.web.factory.CWebElementFactory;
import org.catools.web.pages.CWebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.util.function.Function;

@Getter
@Accessors(chain = true)
public abstract class CWebTableRow<DR extends CDriver, P extends CWebTable<DR, ?>>
    extends CWebElement<DR> implements CWebComponent<DR> {
  protected final P parentTable;
  @Setter
  private String cellXpath;
  private int rowIndex;

  public CWebTableRow(String name, DR driver, int idx, P parentTable) {
    this(name, driver, idx, parentTable, DEFAULT_TIMEOUT);
  }

  public CWebTableRow(String name, DR driver, int rowIndex, P parentTable, int waitSec) {
    super(name, driver, By.xpath(parentTable.getRowXpath(rowIndex)), waitSec);
    this.rowIndex = rowIndex;
    this.parentTable = parentTable;
    this.cellXpath = parentTable.getCellXpath() + "[%d]";
    CWebElementFactory.initElements(this);
  }

  public CList<CWebTableCell> readRowCells() {
    CList<CWebTableCell> cellValues = new CList<>();
    CMap<Integer, String> headersMap = parentTable.getHeadersMap();
    for (Integer idx : headersMap.keySet()) {
      String header = headersMap.get(idx);
      CWebElement<DR> element = createControl(header);
      cellValues.add(new CWebTableCell(idx, header, element.getText(0), element.Visible.isTrue()));
    }
    return cellValues;
  }

  protected <C extends CWebElement<DR>> C createControl(String header) {
    return createControl(header, 0);
  }

  @SuppressWarnings("unchecked")
  protected <C extends CWebElement<DR>> C createControl(String header, int index) {
    return (C) new CWebElement<>(header, driver, getLocator(header, index, ""));
  }

  protected <C extends CWebElement<DR>> C createControl(String header, Function<By, C> controlBuilder) {
    return controlBuilder.apply(getLocator(header, 0, ""));
  }

  protected <C extends CWebElement<DR>> C createControl(String header, String childLocator, Function<By, C> controlBuilder) {
    return controlBuilder.apply(getLocator(header, 0, childLocator));
  }

  private By getLocator(String header, int index, String childLocator) {
    CHashMap<Integer, String> allMatches = parentTable.getHeadersMap().getAll((k, v) -> CStringUtil.equals(header, v));

    if (allMatches.isEmpty() || allMatches.size() < index) {
      // We send invalid locator in case if header does not exist
      throw new IllegalArgumentException("Header not found, header:'" + header + "', index:" + index);
    }

    Object headerIndex = allMatches.keySet().stream().sorted().toList().get(index);

    String cellLocator = String.format(cellXpath + childLocator, headerIndex);
    cellLocator = CStringUtil.removeStart(cellLocator, ".");
    return new ByChained(getLocator(), By.xpath("." + cellLocator));
  }
}
