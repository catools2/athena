package org.catools.excel.model;

import lombok.Data;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Date;

@Data
public class CExcelCell {
  private CExcelCell header;
  private int columnIndex;
  private int rowIndex;
  private CExcelSheet sheet;
  private CExcelRow row;
  private CellType cellType;
  private CellType cachedFormulaResultType;
  private String cellFormula;
  private double numericCellValue;
  private Date dateCellValue;
  private String stringCellValue;
  private boolean booleanCellValue;
  private byte errorCellValue;
  private String cellComment;
  private CellRangeAddress arrayFormulaRange;
}
