package org.catools.common.tests.date;

import org.catools.common.collections.CList;
import org.catools.common.date.CDate;
import org.catools.common.date.CRandomDateGenerator;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Calendar;

public class CRandomDateGeneratorTest extends CBaseUnitTest {
  private CDate fromDate = CDate.valueOf("2011-07-21", "yyyy-MM-dd");
  private CDate toDate = CDate.valueOf("2013-09-24", "yyyy-MM-dd");

  private Calendar fromCalendar = fromDate.toCalendar();
  private Calendar toCalendar = toDate.toCalendar();

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testInstance() {
    CVerify.Object.isNotNull(CRandomDateGenerator.instance(), "Method works as design");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testInstance1() {
    CVerify.Object.isNotNull(
        CRandomDateGenerator.instance(fromCalendar, toCalendar), "Method works as design");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testInstance2() {
    CVerify.Object.isNotNull(
        CRandomDateGenerator.instance(fromDate, toDate), "Method works as design");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testInstance3() {
    CVerify.Object.isNotNull(CRandomDateGenerator.instance(2012, 2012), "Method works as design");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFormattedDate() {
    CVerify.String.equalsAny(
        CRandomDateGenerator.getFormattedDate(fromCalendar, toCalendar, "yyyy"),
        new CList<>("2011", "2012", "2013"),
        "Method works as design");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFormattedDate1() {
    CVerify.String.equalsAny(
        CRandomDateGenerator.getFormattedDate(fromDate, toDate, "yyyy"),
        new CList<>("2011", "2012", "2013"),
        "Method works as design");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFormattedDate2() {
    CVerify.String.equalsAny(
        CRandomDateGenerator.getFormattedDate(2011, 2013, "yyyy"),
        new CList<>("2011", "2012", "2013"),
        "Method works as design");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSetFromYear() {
    CDate fromDate = CDate.valueOf("2015-01-01 01:01:01.001", "yyyy-MM-dd hh:mm:ss.SSS");
    CDate toDate = CDate.valueOf("2016-02-02 02:02:02.002", "yyyy-MM-dd hh:mm:ss.SSS");

    CRandomDateGenerator generator1 =
        CRandomDateGenerator.instance()
            .setFromYear(2015)
            .setFromMonth(1)
            .setFromDayOfMonth(1)
            .setFromHour(1)
            .setFromMinute(1)
            .setFromSecond(1)
            .setFromMilliSecond(1)
            .setToYear(2016)
            .setToMonth(2)
            .setToDayOfMonth(2)
            .setToHour(2)
            .setToMinute(2)
            .setToSecond(2)
            .setToMilliSecond(2);

    CRandomDateGenerator generator2 =
        CRandomDateGenerator.instance()
            .setFromYear(2015)
            .setFromDayOfYear(1)
            .setFromMinute(1)
            .setFromSecond(1)
            .setFromMilliSecond(1)
            .setToYear(2016)
            .setToDayOfYear(2)
            .setToHour(2)
            .setToMinute(2)
            .setToSecond(2)
            .setToMilliSecond(2);

    int i = 1000;
    while (i-- > 0) {
      CDate date1 = generator1.getDate();
      CVerify.Long.betweenInclusive(
          date1.getTime(),
          fromDate.getTime(),
          toDate.getTime(),
          "%s is between %s and %s",
          date1,
          fromDate,
          toDate);

      CDate date2 = generator2.getDate();
      CVerify.Long.betweenInclusive(
          date2.getTime(),
          fromDate.getTime(),
          toDate.getTime(),
          "%s is between %s and %s",
          date2,
          fromDate,
          toDate);
    }
  }
}
