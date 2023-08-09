package org.catools.excel.utils;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.catools.common.io.CResource;
import org.catools.excel.exception.CExcelOperationException;
import org.catools.excel.model.CExcelCell;
import org.catools.excel.model.CExcelRow;
import org.catools.excel.model.CExcelSheet;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class CExcelUtil {

  /**
   * Read XSS workbook from an input stream
   *
   * @param resource   resource to read workbook from
   * @param sheetIndex the sheet index to read
   * @return the sheet data
   */
  public static CExcelSheet readXSSFWorksheet(CResource resource, int sheetIndex, boolean hasHeader) {
    return resource.performActionOnResource((s, inputStream) -> readXSSFWorksheet(inputStream, sheetIndex, hasHeader));
  }

  /**
   * Read XSS workbook from an input stream
   *
   * @param inputStream stream to read workbook from
   * @param sheetIndex  the sheet index to read
   * @return the sheet data
   */
  public static CExcelSheet readXSSFWorksheet(InputStream inputStream, int sheetIndex, boolean hasHeader) {
    Workbook workbook = getXSSFWorksheet(inputStream);
    return readWorksheet(workbook, sheetIndex, hasHeader);
  }

  /**
   * Get the XSSFWorkbook from input stream
   *
   * @param inputStream the input stream to read XSSFWorkbook from
   * @return XSSFWorkbook object
   */
  public static XSSFWorkbook getXSSFWorksheet(InputStream inputStream) {
    try {
      return new XSSFWorkbook(inputStream);
    } catch (IOException e) {
      throw new CExcelOperationException("Failed to read workbook from ", e);
    }
  }

  /**
   * @param workbook   the Excel workbook to read
   * @param sheetIndex the index of the sheet to read
   * @return the sheet data
   */
  public static CExcelSheet readWorksheet(Workbook workbook, int sheetIndex, boolean hasHeader) {
    CExcelSheet excelSheet = new CExcelSheet();
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    CExcelRow header = null;

    for (Row row : sheet) {
      if (hasHeader && header == null) {
        header = readRow(excelSheet, row, null);
        continue;
      }

      CExcelRow excelRow = readRow(excelSheet, row, header);

      if (excelRow.isNotEmpty())
        excelSheet.put(row.getRowNum(), excelRow);
    }

    return excelSheet;
  }

  private static CExcelRow readRow(CExcelSheet excelSheet, Row row, CExcelRow headers) {
    CExcelRow excelRow = new CExcelRow();
    for (Cell cell : row) {
      CExcelCell excelCell = new CExcelCell();
      if (headers != null && headers.sizeIsGreaterThanOrEqual(cell.getColumnIndex()))
        excelCell.setHeader(headers.get(cell.getColumnIndex()));

      excelCell.setColumnIndex(cell.getColumnIndex());
      excelCell.setRowIndex(cell.getRowIndex());
      excelCell.setSheet(excelSheet);
      excelCell.setRow(excelRow);
      excelCell.setCellType(cell.getCellType());
      switch (cell.getCellType()) {
        case FORMULA -> {
          excelCell.setCachedFormulaResultType(cell.getCachedFormulaResultType());
          excelCell.setCellFormula(cell.getCellFormula());
          excelCell.setArrayFormulaRange(cell.getArrayFormulaRange());
        }
        case STRING -> excelCell.setStringCellValue(cell.getStringCellValue());
        case NUMERIC -> {
          excelCell.setNumericCellValue(cell.getNumericCellValue());
          excelCell.setDateCellValue(cell.getDateCellValue());
        }
        case BOOLEAN -> excelCell.setBooleanCellValue(cell.getBooleanCellValue());
        case ERROR -> excelCell.setErrorCellValue(cell.getErrorCellValue());
      }

      if (cell.getCellComment() != null) {
        excelCell.setCellComment(cell.getCellComment().getString().getString());
      }
      excelRow.put(cell.getColumnIndex(), excelCell);
    }
    return excelRow;
  }
}
