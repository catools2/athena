package org.catools.common.tests.date;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CDateUtil;
import org.catools.common.utils.CSleeper;
import org.testng.annotations.Test;

import java.time.Month;
import java.time.chrono.IsoEra;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CDateTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddDays() {
    CDate date = CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd");
    CVerify.String.equals(
        date.addDays(1).toFormat("yyyy-MM-dd"), "2012-07-22", "CDateTest ::> addDays");
    CVerify.String.equals(
        date.addDays(-2).toFormat("yyyy-MM-dd"), "2012-07-20", "CDateTest ::> addDays");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddHours() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.addHours(1).toFormat(format), "2012-07-21 13:12:12:000", "CDateTest ::> addHours");
    CVerify.String.equals(
        date.addHours(-2).toFormat(format), "2012-07-21 11:12:12:000", "CDateTest ::> addHours");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddMilliseconds() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.addMilliseconds(1).toFormat(format),
        "2012-07-21 12:12:12:001",
        "CDateTest ::> addMilliseconds");
    CVerify.String.equals(
        date.addMilliseconds(-2).toFormat(format),
        "2012-07-21 12:12:11:999",
        "CDateTest ::> addMilliseconds");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddMinutes() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.addMinutes(1).toFormat(format), "2012-07-21 12:13:12:000", "CDateTest ::> addMinutes");
    CVerify.String.equals(
        date.addMinutes(-2).toFormat(format),
        "2012-07-21 12:11:12:000",
        "CDateTest ::> addMinutes");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddMonths() {
    CDate date = CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd");
    CVerify.String.equals(
        date.addMonths(1).toFormat("yyyy-MM-dd"), "2012-08-21", "CDateTest ::> addMonths");
    CVerify.String.equals(
        date.addMonths(-2).toFormat("yyyy-MM-dd"), "2012-06-21", "CDateTest ::> addMonths");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddSeconds() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.addSeconds(1).toFormat(format), "2012-07-21 12:12:13:000", "CDateTest ::> addSeconds");
    CVerify.String.equals(
        date.addSeconds(-2).toFormat(format),
        "2012-07-21 12:12:11:000",
        "CDateTest ::> addSeconds");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddWeeks() {
    CDate date = CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd");
    CVerify.String.equals(
        date.addWeeks(1).toFormat("yyyy-MM-dd"), "2012-07-28", "CDateTest ::> addWeeks");
    CVerify.String.equals(
        date.addWeeks(-2).toFormat("yyyy-MM-dd"), "2012-07-14", "CDateTest ::> addWeeks");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddYears() {
    CDate date = CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd");
    CVerify.String.equals(
        date.addYears(1).toFormat("yyyy-MM-dd"), "2013-07-21", "CDateTest ::> addYears");
    CVerify.String.equals(
        date.addYears(-2).toFormat("yyyy-MM-dd"), "2011-07-21", "CDateTest ::> addYears");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAsLocalDate() {
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, "yyyy-MM-dd HH:mm:ss:SSS");
    CVerify.String.equals(
        date1.asLocalDate().toString(), "2012-07-21", "CDateTest ::> toDateOnlyString");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCDate1() {
    CDate now = CDate.now();
    CVerify.Long.equalsP(now.getTime(), new CDate(now).getTime(), 10L, "CDateTest ::> CDate");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCeiling() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.ceiling(Calendar.DAY_OF_MONTH).toFormat(format),
        "2012-07-22 00:00:00:000",
        "CDateTest ::> ceiling");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClone() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CDate date2 = date1.clone();
    date1.addDays(2);
    CVerify.String.equals(date1.toFormat(format), "2012-07-23 12:12:12:000", "CDateTest ::> clone");
    CVerify.String.equals(date2.toFormat(format), "2012-07-21 12:12:12:000", "CDateTest ::> clone");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareDateByFormat() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CDate date2 = date1.clone().addMilliseconds(10);
    CVerify.Bool.isTrue(
        date1.compareDateByFormat(date2, "yyyy-MM-dd HH:mm") == 0,
        "CDateTest ::> compareDatePortion");
    CVerify.Bool.isFalse(
        date1.compareDateByFormat(null, "yyyy-MM-dd HH:mm") == 0,
        "CDateTest ::> compareDatePortion");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareDatePortion() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CDate date2 = date1.clone().addHours(1);
    CVerify.Bool.isTrue(date1.compareDatePortion(date2) == 0, "CDateTest ::> compareDatePortion");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareTimePortion() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CDate date2 = date1.clone().addDays(1);
    CVerify.Bool.isTrue(date1.compareTimePortion(date2) == 0, "CDateTest ::> compareTimePortion");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGet() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Int.equals(date.get(Calendar.HOUR_OF_DAY), 12, "CDateTest ::> get");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetDayOfMonth() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Int.equals(date.getDayOfMonth(), 21, "CDateTest ::> getDayOfMonth");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetDayOfWeek() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.Int.equals(date1.getDayOfWeek(), 7, "CDateTest ::> getDayOfWeek");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetDayOfYear() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Int.equals(date.getDayOfYear(), 203, "CDateTest ::> getDayOfYear");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetDurationFrom() {
    CDate date1 = CDate.now().addSeconds(2);
    CVerify.Long.betweenInclusive(
        date1.getDurationFrom(CDate.now()).getSeconds(), 1L, 2L, "CDateTest ::> getDurationFrom");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetDurationFromNow() {
    CDate date1 = CDate.now().addSeconds(2);
    CVerify.Long.betweenInclusive(
        date1.getDurationFromNow().getSeconds(), 1L, 2L, "CDateTest ::> getDurationFrom");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetDurationTo() {
    CDate date1 = CDate.now().addSeconds(-2);
    CVerify.Long.betweenInclusive(
        date1.getDurationTo(CDate.now()).getSeconds(), 1L, 2L, "CDateTest ::> getDurationTo");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetDurationToNow() {
    CDate date = CDate.now();
    date.addSeconds(-8000);
    CVerify.Long.equals(
        date.getDurationToNow().toSeconds(), 8000L, "CDateTest ::> getDurationToNowInSeconds");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetEra() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Object.equals(date.getEra(), IsoEra.CE, "CDateTest ::> getEra");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFragmentInDays() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Long.equals(
        date.getFragmentInDays(Calendar.MONTH), 21L, "CDateTest ::> getFragmentInDays");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFragmentInHours() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Long.equals(
        date.getFragmentInHours(Calendar.DAY_OF_MONTH), 12L, "CDateTest ::> getFragmentInHours");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFragmentInMilliseconds() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Long.equals(
        date.getFragmentInMilliseconds(Calendar.SECOND),
        123L,
        "CDateTest ::> getFragmentInMilliseconds");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFragmentInMinutes() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Long.equals(
        date.getFragmentInMinutes(Calendar.DAY_OF_MONTH),
        732L,
        "CDateTest ::> getFragmentInMinutes");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFragmentInSeconds() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Long.equals(
        date.getFragmentInSeconds(Calendar.MINUTE), 12L, "CDateTest ::> getFragmentInSeconds");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetMonthName() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Object.equals(date.getMonthName(), Month.JULY, "CDateTest ::> getMonthName");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetTimeStamp() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.getTimeStamp().toString(), "2012-07-21 12:12:12.0", "CDateTest ::> getTimeStamp");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsFriday() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-27 12:12:12:000", Locale.US, format).isFriday(),
        "CDateTest ::> isFriday");
    CVerify.Bool.isFalse(
        CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format).isFriday(),
        "CDateTest ::> isFriday");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsLeapYear() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-21 12:12:12:123", format).isLeapYear(), "CDateTest ::> isLeapYear");
    CVerify.Bool.isFalse(
        CDate.valueOf("2018-07-21 12:12:12:123", format).isLeapYear(), "CDateTest ::> isLeapYear");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMonday() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-23 12:12:12:000", Locale.US, format).isMonday(),
        "CDateTest ::> isMonday");
    CVerify.Bool.isFalse(
        CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format).isMonday(),
        "CDateTest ::> isMonday");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsSameDay() {
    CVerify.Bool.isTrue(new CDate(new Date()).isSameDay(new Date()), "CDateTest ::> isSameDay");
    CVerify.Bool.isFalse(
        new CDate(new Date()).isSameDay(new CDate().addDays(1)), "CDateTest ::> isSameDay");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsSameInstant() {
    CDate cDate1 = new CDate(new Date());
    CDate cDate2 = new CDate(cDate1);
    CVerify.Bool.isTrue(cDate1.isSameInstant(cDate2), "CDateTest ::> isSameInstant");
    CSleeper.sleepTight(10);
    CVerify.Bool.isFalse(cDate1.isSameInstant(new Date()), "CDateTest ::> isSameInstant");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsSameLocalTime() {
    CDate cDate1 = CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd");
    CDate cDate2 = CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd");
    CDate cDate3 = CDate.valueOf("2012-07-21", Locale.CANADA, "yyyy-MM-dd").addHours(1);
    CVerify.Bool.isTrue(cDate1.isSameLocalTime(cDate2), "CDateTest ::> isSameLocalTime");
    CVerify.Bool.isFalse(cDate1.isSameLocalTime(cDate3), "CDateTest ::> isSameLocalTime");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsSaturday() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format).isSaturday(),
        "CDateTest ::> isSaturday");
    CVerify.Bool.isFalse(
        CDate.valueOf("2012-07-20 12:12:12:000", Locale.US, format).isSaturday(),
        "CDateTest ::> isSaturday");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsSunday() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-22 12:12:12:000", Locale.US, format).isSunday(),
        "CDateTest ::> isSunday");
    CVerify.Bool.isFalse(
        CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format).isSunday(),
        "CDateTest ::> isSunday");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsThursday() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-26 12:12:12:000", Locale.US, format).isThursday(),
        "CDateTest ::> isThursday");
    CVerify.Bool.isFalse(
        CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format).isThursday(),
        "CDateTest ::> isThursday");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTodayDate() {
    CVerify.Bool.isTrue(CDate.now().isTodayDate(), "CDateTest ::> isTodayDate");
    CVerify.Bool.isFalse(CDate.now().addDays(1).isTodayDate(), "CDateTest ::> isTodayDate");
    CVerify.Bool.isFalse(CDate.now().addDays(-1).isTodayDate(), "CDateTest ::> isTodayDate");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsTuesday() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-24 12:12:12:000", Locale.US, format).isTuesday(),
        "CDateTest ::> isTuesday");
    CVerify.Bool.isFalse(
        CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format).isTuesday(),
        "CDateTest ::> isTuesday");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsWednesday() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Bool.isTrue(
        CDate.valueOf("2012-07-25 12:12:12:000", Locale.US, format).isWednesday(),
        "CDateTest ::> isWednesday");
    CVerify.Bool.isFalse(
        CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format).isWednesday(),
        "CDateTest ::> isWednesday");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthOfMonth() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Int.equals(
        CDate.valueOf("2012-07-21 12:12:12:123", format).lengthOfMonth(),
        31,
        "CDateTest ::> lengthOfMonth");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthOfYear() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CVerify.Int.equals(
        CDate.valueOf("2012-07-21 12:12:12:123", format).lengthOfYear(),
        366,
        "CDateTest ::> lengthOfYear");
    CVerify.Int.equals(
        CDate.valueOf("2018-07-21 12:12:12:123", format).lengthOfYear(),
        365,
        "CDateTest ::> lengthOfYear");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNow() {
    CVerify.Long.equalsP(
        CDate.now().getTime(),
        new CDate(Calendar.getInstance(CDateUtil.getDefaultTimeZone()).getTime()).getTime(),
        10L,
        "CDateTest ::> now");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRound() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.round(Calendar.DAY_OF_MONTH).toFormat(format),
        "2012-07-22 00:00:00:000",
        "CDateTest ::> round");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSetDays() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.setDays(10).toFormat(format), "2012-07-10 12:12:12:000", "CDateTest ::> setDays");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSetMilliseconds() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.setMilliseconds(10).toFormat(format),
        "2012-07-21 12:12:12:010",
        "CDateTest ::> setMilliseconds");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSetMonths() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.setMonths(Calendar.OCTOBER).toFormat(format),
        "2012-10-21 12:12:12:000",
        "CDateTest ::> setMonths");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSetYears() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.setYears(2018).toFormat(format), "2018-07-21 12:12:12:000", "CDateTest ::> setYears");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToCalendar() {
    CVerify.Bool.isTrue(new CDate().toCalendar() instanceof Calendar, "CDateTest ::> toCalendar");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToCalendar1() {
    Calendar calendar = new CDate().toCalendar(CDateUtil.getDefaultTimeZone());
    CVerify.Bool.isTrue(calendar instanceof Calendar, "CDateTest ::> toCalendar");
    CVerify.Bool.isTrue(
        calendar.getTimeZone().equals(CDateUtil.getDefaultTimeZone()),
        "CDateTest ::> toCalendar(TimeZone)");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToDateOnlyString() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(date1.toDateOnlyString(), "07/21/2012", "CDateTest ::> toDateOnlyString");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToDuration() {
    CVerify.Int.greater((int) CDate.now().toDuration().getSeconds(), 0, "CDateTest ::> toDuration");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToFormat() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.toFormat("yyyy-MMM-dd HH:mm"), "2012-Jul-21 12:12", "CDateTest ::> toFormat");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToFormat1() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.toFormat("yyyy-MMM-dd HH:mm", CDateUtil.getDefaultTimeZone()),
        "2012-Jul-21 12:12",
        "CDateTest ::> toFormat");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToFormattedDuration() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.toFormattedDuration(),
        "15542d 16:12:12:000",
        "CDateTest ::> getDurationToNowInSeconds");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, priority = 10)
  public void testToFormattedDurationFrom() {
    CVerify.String.startsWith(
        CDate.now().addSeconds(2).toFormattedDurationFrom(CDate.now()),
        "00:00:02:000",
        "CDateTest ::> toFormattedDurationTo");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, priority = 10)
  public void testToFormattedDurationFromNow() {
    CVerify.String.startsWith(
        CDate.now().addSeconds(2).toFormattedDurationFromNow(),
        "00:00:02:000",
        "CDateTest ::> toFormattedDurationTo");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToFormattedDurationTo() {
    CVerify.String.startsWith(
        CDate.now().addSeconds(2).toFormattedDurationTo(CDate.now()),
        "00:00:-2:000",
        "CDateTest ::> toFormattedDurationTo");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToFormattedDurationToNow() {
    CVerify.String.startsWith(
        CDate.now().addSeconds(2).toFormattedDurationToNow(),
        "00:00:-2:00",
        "CDateTest ::> toFormattedDurationTo");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToLocalTime() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(date1.toLocalTime().toString(), "2012-07-21", "CDateTest ::> toLocalTime");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToLongDate() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.toLongDate(), "2012-Jul-21 12:12:12:000", "CDateTest ::> toLongDate");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToString() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(date1.toString(), "2012-Jul-21 12:12:12:000", "CDateTest ::> toString");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToString1() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.toString("yyyy-MMM-dd HH:mm:ss"), "2012-Jul-21 12:12:12", "CDateTest ::> toString");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToTimeStampForFileName() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.toTimeStampForFileName(),
        "20120721_121212_000",
        "CDateTest ::> toTimeStampForFileName");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimTime() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date1.trimTime().toFormat(format), "2012-07-21 00:00:00:000", "CDateTest ::> trimTime");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncate() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date = CDate.valueOf("2012-07-21 12:12:12:000", Locale.US, format);
    CVerify.String.equals(
        date.truncate(Calendar.DAY_OF_MONTH).toFormat(format),
        "2012-07-21 00:00:00:000",
        "CDateTest ::> truncate");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedCompareTo() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:59:12:123", Locale.US, format);
    CDate date2 = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Bool.isTrue(
        date1.truncatedCompareTo(date2, Calendar.HOUR) == 0, "CDateTest ::> truncatedCompareTo");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedEquals() {
    String format = "yyyy-MM-dd HH:mm:ss:SSS";
    CDate date1 = CDate.valueOf("2012-07-21 12:59:12:123", Locale.US, format);
    CDate date2 = CDate.valueOf("2012-07-21 12:12:12:123", Locale.US, format);
    CVerify.Bool.isTrue(
        date1.truncatedEquals(date2, Calendar.HOUR), "CDateTest ::> truncatedEquals");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testValueOf() {
    CVerify.Object.isNotNull(
        CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd", "dd-MM-yyyy"),
        "CDateTest ::> valueOf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testValueOf1() {
    CVerify.Object.isNotNull(
        CDate.valueOf("2012-07-21", Locale.US, "yyyy-MM-dd", "dd-MM-yyyy"),
        "CDateTest ::> valueOf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testValueOfOrNull() {
    CVerify.Object.isNotNull(
        CDate.valueOfOrNull("2012-07-21", "yyyy-MM-dd", "dd-MM-yyyy"), "CDateTest ::> valueOf");
    CVerify.Object.isNull(
        CDate.valueOfOrNull("2012-30-30", "yyyy-MM-dd", "dd-MM-yyyy"), "CDateTest ::> valueOf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testValueOfOrNull1() {
    CVerify.Object.isNotNull(
        CDate.valueOfOrNull("2012-07-21", Locale.US, "yyyy-MM-dd", "dd-MM-yyyy"),
        "CDateTest ::> valueOf");
    CVerify.Object.isNull(
        CDate.valueOfOrNull("2012-30-30", Locale.US, "yyyy-MM-dd", "dd-MM-yyyy"),
        "CDateTest ::> valueOf");
  }
}
