package org.catools.excel.utils;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CResource;
import org.catools.excel.model.CExcelRow;
import org.catools.excel.model.CExcelSheet;
import org.testng.annotations.Test;

public class CExcelUtilTest {

  @Test
  public void testReadXSSFWorksheetWithoutHeader() {
    CResource resource = new CResource("example.xlsx", getClass());
    CExcelSheet excelSheet = CExcelUtil.readXSSFWorksheet(resource, 0, false);
    excelSheet.verifySizeEquals(10, "Correct number of row returned.");
    excelSheet.forEach((integer, cExcelRow) -> {
      cExcelRow.verifySizeEquals(8, "Correct number of column returned.");
    });
    CExcelRow header = excelSheet.get(0);
    CVerify.Double.equals(header.get(0).getNumericCellValue(), 0.0, "Cell Value is correct");
    CVerify.String.equals(header.get(1).getStringCellValue(), "First Name", "Cell Value is correct");
    CVerify.String.equals(header.get(6).getStringCellValue(), "Date", "Cell Value is correct");

    CExcelRow firstRecord = excelSheet.get(1);
    CVerify.Double.equals(firstRecord.get(0).getNumericCellValue(), 1.0, "Cell Value is correct");
    CVerify.String.equals(firstRecord.get(1).getStringCellValue(), "Dulce", "Cell Value is correct");
    CVerify.Date.equals(firstRecord.get(6).getDateCellValue(), CDate.of("10/10/1983", "dd/MM/yyyy"), "Cell Value is correct");
  }

  @Test
  public void testReadXSSFWorksheetWithHeader() {
    CResource resource = new CResource("example.xlsx", getClass());
    CExcelSheet excelSheet = CExcelUtil.readXSSFWorksheet(resource, 0, true);
    excelSheet.verifySizeEquals(9, "Correct number of row returned.");
    excelSheet.forEach((integer, cExcelRow) -> {
      cExcelRow.verifySizeEquals(8, "Correct number of column returned.");
    });

    CExcelRow firstRow = excelSheet.get(1);
    CVerify.Double.equals(firstRow.get(0).getHeader().getNumericCellValue(), 0.0, "Cell header is correct");
    CVerify.String.equals(firstRow.get(1).getHeader().getStringCellValue(), "First Name", "Cell header is correct");
    CVerify.String.equals(firstRow.get(6).getHeader().getStringCellValue(), "Date", "Cell header is correct");

    CVerify.Double.equals(firstRow.get(0).getNumericCellValue(), 1.0, "Cell Value is correct");
    CVerify.String.equals(firstRow.get(1).getStringCellValue(), "Dulce", "Cell Value is correct");
    CVerify.Date.equals(firstRow.get(6).getDateCellValue(), CDate.of("10/10/1983", "dd/MM/yyyy"), "Cell Value is correct");
  }
}