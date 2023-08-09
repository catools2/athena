package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DateUtils;
import org.catools.common.configs.CDateConfigs;
import org.catools.common.exception.CInvalidDateFormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@UtilityClass
public class CDateUtil {
  public static final String DATE_FORMAT_STRING = "MM/dd/yyyy";
  public static final String TIME_FORMAT = "HH:mm:ss";

  public static final String LONG_DATE_FORMAT_STRING_MILLI_SECONDS = "yyyy-MMM-dd HH:mm:ss:SSS";
  public static final String FILENAME_TIMESTAMP_FORMAT = "yyyy-MM-dd_HHmmss_SSS";

  /**
   * Get the default timezone (from defined configuration)
   *
   * @return default timezone
   */
  public static TimeZone getDefaultTimeZone() {
    return CDateConfigs.getDefaultTimeZone();
  }

  /**
   * Parses a string representing a date by trying a variety of different parsers, using the default
   * date format symbols for the given locale..
   *
   * <p>The parse will try each parse pattern in turn. A parse is only deemed successful if it
   * parses the whole of the input string. If no parse patterns match, a ParseException is thrown.
   * The parser parses strictly - it does not allow for dates such as "February 942, 1996".
   *
   * @param str           the date to parse, not null
   * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
   * @return the parsed date
   * @throws {@code CInvalidDateFormatException} if the date string or pattern array is null
   */
  public static Date valueOf(final String str, final String... parsePatterns) {
    return valueOf(str, Locale.getDefault(), parsePatterns);
  }

  /**
   * Parses a string representing a date by trying a variety of different parsers, using the default
   * date format symbols for the given locale..
   *
   * <p>The parse will try each parse pattern in turn. A parse is only deemed successful if it
   * parses the whole of the input string. If no parse patterns match, a ParseException is thrown.
   * The parser parses strictly - it does not allow for dates such as "February 942, 1996".
   *
   * @param str           the date to parse, not null
   * @param locale        the locale whose date format symbols should be used. If <code>null</code>, the
   *                      system locale is used (as per {@see #valueOf(String, String...)}).
   * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
   * @return the parsed date
   * @throws {@code CInvalidDateFormatException} if anything goes wrong
   */
  public static Date valueOf(final String str, final Locale locale, final String... parsePatterns) {
    try {
      return DateUtils.parseDateStrictly(str, locale, parsePatterns);
    } catch (Exception e) {
      throw new CInvalidDateFormatException(String.format("cannot convert %s to date", str), e);
    }
  }

  /**
   * Parses a string representing a date by trying a variety of different parsers, using the default
   * date format symbols for the given locale..
   *
   * <p>The parse will try each parse pattern in turn. A parse is only deemed successful if it
   * parses the whole of the input string. If no parse patterns match, a ParseException is thrown.
   * The parser parses strictly - it does not allow for dates such as "February 942, 1996".
   *
   * @param str           the date to parse, not null
   * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
   * @return the parsed date or null if could not parse
   */
  public static Date valueOfOrNull(final String str, final String... parsePatterns) {
    return valueOfOrNull(str, Locale.getDefault(), parsePatterns);
  }

  /**
   * Parses a string representing a date by trying a variety of different parsers, using the default
   * date format symbols for the given locale..
   *
   * <p>The parse will try each parse pattern in turn. A parse is only deemed successful if it
   * parses the whole of the input string. If no parse patterns match, a ParseException is thrown.
   * The parser parses strictly - it does not allow for dates such as "February 942, 1996".
   *
   * @param str           the date to parse, not null
   * @param locale        the locale whose date format symbols should be used. If <code>null</code>, the
   *                      system locale is used (as per {@see #valueOf(String, String...)}).
   * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
   * @return the parsed date or null if could not parse
   */
  public static Date valueOfOrNull(
      final String str, final Locale locale, final String... parsePatterns) {
    try {
      return valueOf(str, locale, parsePatterns);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Create a deep copy of Date object
   *
   * @return Date with the time set to value of getTime()
   */
  public static Date clone(Date date) {
    return new Date(date.getTime());
  }

  /**
   * Compare value of given date against current object using the particular date format. To do so,
   * we convert date values to formatted string using provided format and then compare then using
   * string compression.
   *
   * @param date1  first date to compare current value against
   * @param date2  second date to compare current value against
   * @param format the format which should be used during compression.
   * @return the value {@code 0} if the argument Date is equal to date Date; a value less than
   * {@code 0} if date Date is before the Date argument; and a value greater than
   */
  public static int compareDateByFormat(final Date date1, final Date date2, String format) {
    if (date1 == null && date2 == null) {
      return 0;
    }

    if (date1 == null) {
      return -1;
    }

    if (date2 == null) {
      return 1;
    }
    return toFormat(date1, format).compareTo(toFormat(date2, format));
  }

  /**
   * Compare value of given date against current object using MM/dd/yyyy date format. To do so, we
   * convert date values to formatted string using MM/dd/yyyy format and then compare then using
   * string compression.
   *
   * @param date1 first date to compare current value against
   * @param date2 second date to compare current value against
   * @return the value {@code 0} if the values are equal; the value less than {@code 0} if the
   * string value is less and greater than {@code 0} if it is bigger
   * @see #compareDateByFormat(Date, Date, String)
   * @see #compareTimePortion(Date, Date)
   */
  public static int compareDatePortion(final Date date1, final Date date2) {
    return compareDateByFormat(date1, date2, DATE_FORMAT_STRING);
  }

  /**
   * Compare value of given date against current object using HH:mm:ss date format. To do so, we
   * convert date values to formatted string using HH:mm:ss format and then compare then using
   * string compression.
   *
   * @param date1 first date to compare current value against
   * @param date2 second date to compare current value against
   * @return the value {@code 0} if the values are equal; the value less than {@code 0} if the
   * string value is less and greater than {@code 0} if it is bigger
   * @see #compareDateByFormat(Date, Date, String)
   * @see #compareDatePortion(Date, Date)
   */
  public static int compareTimePortion(final Date date1, final Date date2) {
    return compareDateByFormat(date1, date2, TIME_FORMAT);
  }

  /**
   * Get diff between from and to in ChronoUnit
   *
   * @param from date
   * @param to   date
   * @return time diff
   */
  public static long getDiff(final Date from, final Date to, final ChronoUnit unit) {
    return unit.between(from.toInstant(), to.toInstant());
  }

  /**
   * Get diff between from and current in ChronoUnit
   *
   * @param from date
   * @return time diff
   */
  public static long getDiffToNow(final Date from, final ChronoUnit unit) {
    return unit.between(from.toInstant(), Instant.now());
  }

  /**
   * Get diff between current time and to in ChronoUnit
   *
   * @param to date
   * @return time diff
   */
  public static long getDiffFromNow(final Date to, final ChronoUnit unit) {
    return unit.between(Instant.now(), to.toInstant());
  }

  /**
   * Get durarion between from and to (to.getTime() - from.getTime())
   *
   * @param from date
   * @param to   date
   * @return to.getTime() - from.getTime()
   */
  public static Duration getDuration(final Date from, final Date to) {
    return Duration.between(from.toInstant(), to.toInstant());
  }

  /**
   * Returns the number of days within the fragment. All datefields greater than the fragment will
   * be ignored.
   *
   * <p>Asking the days of any date will only return the number of days of the current month
   * (resulting in a number betweenExclusive 1 and 31). date method will retrieve the number of days
   * for any fragment. For example, if you want to calculate the number of days past date year, your
   * fragment is Calendar.YEAR. The result will be all days of the past month(s).
   *
   * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both Calendar.DAY_OF_YEAR and
   * Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
   * A fragment less than or equal to a DAY field will return 0.
   *
   * <ul>
   *   <li>January 28, 2008 with Calendar.MONTH as fragment will return 28 (equivalent to deprecated
   *       date.getDay())
   *   <li>February 28, 2008 with Calendar.MONTH as fragment will return 28 (equivalent to
   *       deprecated date.getDay())
   *   <li>January 28, 2008 with Calendar.YEAR as fragment will return 28
   *   <li>February 28, 2008 with Calendar.YEAR as fragment will return 59
   *   <li>January 28, 2008 with Calendar.MILLISECOND as fragment will return 0 (a millisecond
   *       cannot be split in days)
   * </ul>
   *
   * @param fragment the {@code Calendar} field part of date to calculate
   * @return number of days within the fragment of date
   * @throws IllegalArgumentException if the date is <code>null</code> or fragment is not supported
   */
  public static long getFragmentInDays(final Date date, final int fragment) {
    return DateUtils.getFragmentInDays(date, fragment);
  }

  /**
   * Returns the number of hours within the fragment. All datefields greater than the fragment will
   * be ignored.
   *
   * <p>Asking the hours of any date will only return the number of hours of the current day
   * (resulting in a number betweenExclusive 0 and 23). date method will retrieve the number of
   * hours for any fragment. For example, if you want to calculate the number of hours past date
   * month, your fragment is Calendar.MONTH. The result will be all hours of the past day(s).
   *
   * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both Calendar.DAY_OF_YEAR and
   * Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
   * A fragment less than or equal to a HOUR field will return 0.
   *
   * <ul>
   *   <li>January 1, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 7
   *       (equivalent to deprecated date.getHours())
   *   <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 7
   *       (equivalent to deprecated date.getHours())
   *   <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 7
   *   <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 127 (5*24 + 7)
   *   <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0 (a
   *       millisecond cannot be split in hours)
   * </ul>
   *
   * @param fragment the {@code Calendar} field part of date to calculate
   * @return number of hours within the fragment of date
   * @throws IllegalArgumentException if the date is <code>null</code> or fragment is not supported
   */
  public static long getFragmentInHours(final Date date, final int fragment) {
    return DateUtils.getFragmentInHours(date, fragment);
  }

  /**
   * Returns the number of milliseconds within the fragment. All datefields greater than the
   * fragment will be ignored.
   *
   * <p>Asking the milliseconds of any date will only return the number of milliseconds of the
   * current second (resulting in a number betweenExclusive 0 and 999). date method will retrieve
   * the number of milliseconds for any fragment. For example, if you want to calculate the number
   * of milliseconds past today, your fragment is Calendar.DATE or Calendar.DAY_OF_YEAR. The result
   * will be all milliseconds of the past hour(s), minutes(s) and second(s).
   *
   * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both Calendar.DAY_OF_YEAR and
   * Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
   * A fragment less than or equal to a SECOND field will return 0.
   *
   * <ul>
   *   <li>January 1, 2008 7:15:10.538 with Calendar.SECOND as fragment will return 538
   *   <li>January 6, 2008 7:15:10.538 with Calendar.SECOND as fragment will return 538
   *   <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10538 (10*1000 +
   *       538)
   *   <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0 (a
   *       millisecond cannot be split in milliseconds)
   * </ul>
   *
   * @param fragment the {@code Calendar} field part of date to calculate
   * @return number of milliseconds within the fragment of date
   * @throws IllegalArgumentException if the date is <code>null</code> or fragment is not supported
   */
  public static long getFragmentInMilliseconds(final Date date, final int fragment) {
    return DateUtils.getFragmentInMilliseconds(date, fragment);
  }

  /**
   * Returns the number of minutes within the fragment. All datefields greater than the fragment
   * will be ignored.
   *
   * <p>Asking the minutes of any date will only return the number of minutes of the current hour
   * (resulting in a number betweenExclusive 0 and 59). date method will retrieve the number of
   * minutes for any fragment. For example, if you want to calculate the number of minutes past date
   * month, your fragment is Calendar.MONTH. The result will be all minutes of the past day(s) and
   * hour(s).
   *
   * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both Calendar.DAY_OF_YEAR and
   * Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
   * A fragment less than or equal to a MINUTE field will return 0.
   *
   * <ul>
   *   <li>January 1, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment will return 15
   *       (equivalent to deprecated date.getMinutes())
   *   <li>January 6, 2008 7:15:10.538 with Calendar.HOUR_OF_DAY as fragment will return 15
   *       (equivalent to deprecated date.getMinutes())
   *   <li>January 1, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 15
   *   <li>January 6, 2008 7:15:10.538 with Calendar.MONTH as fragment will return 435 (7*60 + 15)
   *   <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0 (a
   *       millisecond cannot be split in minutes)
   * </ul>
   *
   * @param fragment the {@code Calendar} field part of date to calculate
   * @return number of minutes within the fragment of date
   * @throws IllegalArgumentException if the date is <code>null</code> or fragment is not supported
   */
  public static long getFragmentInMinutes(final Date date, final int fragment) {
    return DateUtils.getFragmentInMinutes(date, fragment);
  }

  /**
   * Returns the number of seconds within the fragment. All datefields greater than the fragment
   * will be ignored.
   *
   * <p>Asking the seconds of any date will only return the number of seconds of the current minute
   * (resulting in a number betweenExclusive 0 and 59). date method will retrieve the number of
   * seconds for any fragment. For example, if you want to calculate the number of seconds past
   * today, your fragment is Calendar.DATE or Calendar.DAY_OF_YEAR. The result will be all seconds
   * of the past hour(s) and minutes(s).
   *
   * <p>Valid fragments are: Calendar.YEAR, Calendar.MONTH, both Calendar.DAY_OF_YEAR and
   * Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND and Calendar.MILLISECOND
   * A fragment less than or equal to a SECOND field will return 0.
   *
   * <ul>
   *   <li>January 1, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10 (equivalent
   *       to deprecated date.getSeconds())
   *   <li>January 6, 2008 7:15:10.538 with Calendar.MINUTE as fragment will return 10 (equivalent
   *       to deprecated date.getSeconds())
   *   <li>January 6, 2008 7:15:10.538 with Calendar.DAY_OF_YEAR as fragment will return 26110
   *       (7*3600 + 15*60 + 10)
   *   <li>January 16, 2008 7:15:10.538 with Calendar.MILLISECOND as fragment will return 0 (a
   *       millisecond cannot be split in seconds)
   * </ul>
   *
   * @param fragment the {@code Calendar} field part of date to calculate
   * @return number of seconds within the fragment of date
   * @throws IllegalArgumentException if the date is <code>null</code> or fragment is not supported
   */
  public static long getFragmentInSeconds(final Date date, final int fragment) {
    return DateUtils.getFragmentInSeconds(date, fragment);
  }

  /**
   * Gets the month-of-year field using the {@code Month} enum.
   *
   * <p>date method returns the enum {@link Month} for the month. date avoids confusion as to what
   * {@code int} values mean. If you need access to the primitive {@code int} value then the enum
   * provides the {@link Month#getValue() int value}.
   *
   * @return the month-of-year, not null
   */
  public static Month getMonthName(final Date date) {
    return Month.of(get(date, Calendar.MONTH) + 1);
  }

  /**
   * Checks if the year is a leap year, according to the ISO proleptic calendar system rules.
   *
   * <p>date method applies the current rules for leap years across the whole time-line. In general,
   * a year is a leap year if it is divisible by four without remainder. However, years divisible by
   * 100, are not leap years, with the exception of years divisible by 400 which are.
   *
   * <p>For example, 1904 is a leap year it is divisible by 4. 1900 was not a leap year as it is
   * divisible by 100, however 2000 was a leap year as it is divisible by 400.
   *
   * <p>The calculation is proleptic - applying the same rules into the far future and far past.
   * date is historically inaccurate, but is correct for the ISO-8601 standard.
   *
   * @return true if the year is leap, false otherwise
   */
  public static boolean isLeapYear(final Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isLeapYear();
  }

  /**
   * Checks if two calendar objects represent the same local time.
   *
   * <p>date method compares the values of the fields of the two objects. In addition, both
   * calendars must be the same of the same type.
   *
   * @param date1 the first date, not altered, not null
   * @param date2 the second date, not altered, not null
   * @return true if they represent the same millisecond instant
   * @throws IllegalArgumentException if date is <code>null</code>
   */
  public static boolean isSameLocalTime(final Date date1, final Date date2) {
    Calendar instance1 = Calendar.getInstance();
    instance1.setTime(date1);
    Calendar instance2 = Calendar.getInstance();
    instance2.setTime(date2);
    return DateUtils.isSameLocalTime(instance1, instance2);
  }

  /**
   * Check if date is a today date
   *
   * @param date input date
   * @return true if date is today otherwise false
   */
  public static boolean isTodayDate(final Date date) {
    return compareDatePortion(date, new Date()) == 0;
  }

  /**
   * Returns the value of the given calendar field. In lenient mode, all calendar fields are
   * normalized. In non-lenient mode, all calendar fields are validated and date method throws an
   * exception if any calendar fields have out-of-range values.
   *
   * @param field the given calendar field.
   * @return the value for the given calendar field.
   * @throws ArrayIndexOutOfBoundsException if the specified field is out of range (<code>
   *                                        field &lt; 0 || field &gt;= FIELD_COUNT</code>).
   */
  public static int get(final Date date, int field) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(field);
  }

  /**
   * Check if date is a Monday
   *
   * @param date input date
   * @return true if date is Monday otherwise false
   */
  public static boolean isMonday(final Date date) {
    return get(date, Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
  }

  /**
   * Check if date is a Saturday
   *
   * @param date input date
   * @return true if date is Saturday otherwise false
   */
  public static boolean isSaturday(final Date date) {
    return get(date, Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
  }

  /**
   * Check if date is a Sunday
   *
   * @param date input date
   * @return true if date is Sunday otherwise false
   */
  public static boolean isSunday(final Date date) {
    return get(date, Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
  }

  /**
   * Check if date is a Thursday
   *
   * @param date input date
   * @return true if date is Thursday otherwise false
   */
  public static boolean isThursday(final Date date) {
    return get(date, Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
  }

  /**
   * Check if date is a Tuesday
   *
   * @param date input date
   * @return true if date is Tuesday otherwise false
   */
  public static boolean isTuesday(final Date date) {
    return get(date, Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
  }

  /**
   * Check if date is a Wednesday
   *
   * @param date input date
   * @return true if date is Wednesday otherwise false
   */
  public static boolean isWednesday(final Date date) {
    return get(date, Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
  }

  /**
   * Check if date is a Friday
   *
   * @param date input date
   * @return true if date is Friday otherwise false
   */
  public static boolean isFriday(final Date date) {
    return get(date, Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
  }

  /**
   * Return date in defined format
   *
   * @param date
   * @param format
   * @return formatted date
   */
  public static String toFormat(final Date date, String format) {
    return getFormatter(format).format(date);
  }

  /**
   * Return date in defined format
   *
   * @param date
   * @param format
   * @param timeZone
   * @return formatted date in defined timezone
   */
  public static String toFormat(final Date date, String format, TimeZone timeZone) {
    DateFormat converter = getFormatter(format);
    converter.setTimeZone(timeZone);
    return converter.format(date);
  }

  /**
   * Return date in yyyy-MM-dd_HHmmss_SSS format
   *
   * @return formatted date
   */
  public static String toLongDate(final Date date) {
    return toFormat(date, LONG_DATE_FORMAT_STRING_MILLI_SECONDS);
  }

  /**
   * Return date in yyyy-MMM-dd HH:mm:ss:SSS format
   *
   * @return formatted date
   */
  public static String toTimeStampForFileName(final Date date) {
    return toFormat(date, FILENAME_TIMESTAMP_FORMAT);
  }

  /**
   * Adds to a date returning a new object. if
   *
   * @param calendarField the calendar field to add to
   * @param amount        the amount to add, may be negative
   * @return current {@code Date}
   * @throws IllegalArgumentException if the date is null
   */
  public static Date add(Date date, final int calendarField, final int amount) {
    final Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(calendarField, amount);
    return c.getTime();
  }

  private static SimpleDateFormat getFormatter(String format) {
    return new SimpleDateFormat(format, Locale.US);
  }
}
