package org.catools.common.date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.time.DateUtils;
import org.catools.common.extensions.types.interfaces.CDynamicDateExtension;
import org.catools.common.utils.CDateUtil;
import org.catools.common.utils.CStringUtil;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoEra;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.catools.common.configs.CDateConfigs.getDefaultTimeZone;

/**
 * A Wrapper to make our life easier with date related operations which is usually does through
 * DateUtil.
 */
public class CDate extends Date implements CDynamicDateExtension {
  private static final String DATE_ONLY_FORMAT_STRING = "MM/dd/yyyy";
  private static final String TIME_FORMAT = "HH:mm:ss";

  private static final String LONG_DATE_FORMAT_STRING_MILI_SECONDS = "yyyy-MMM-dd HH:mm:ss:SSS";
  private static final String FILENAME_TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss_SSS";

  public CDate() {
    super();
  }

  public CDate(long date) {
    super(date);
  }

  public CDate(String date, String format) {
    if (CStringUtil.isNotBlank(date)) {
      valueOf(date, format);
    }
  }

  public CDate(Date date) {
    super(date.getTime());
  }

  public static CDate now() {
    return now(getDefaultTimeZone());
  }

  public static CDate now(TimeZone timeZone) {
    return new CDate(Calendar.getInstance(timeZone).getTime());
  }

  /**
   * Parses a string representing a date by trying a variety of different parsers, using the default
   * date format symbols for the given locale..
   *
   * <p>The parse will try each parse pattern in turn. A parse is only deemed successful if it
   * parses the whole of the input string. If no parse patterns match, a ParseException is thrown.
   * The parser parses strictly - it does not allow for dates such as "February 942, 1996".
   *
   * @param date the date, not null
   * @return the parsed date
   */
  public static CDate of(final Date date) {
    return new CDate(date);
  }

  /**
   * Parses a string representing a date by trying a variety of different parsers, using the default
   * date format symbols for the given locale..
   *
   * <p>The parse will try each parse pattern in turn. A parse is only deemed successful if it
   * parses the whole of the input string. If no parse patterns match, a ParseException is thrown.
   * The parser parses strictly - it does not allow for dates such as "February 942, 1996".
   *
   * @param date the date, not null
   * @return the parsed date
   */
  public static CDate valueOf(final Date date) {
    return of(date);
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
   */
  public static CDate of(final String str, final String... parsePatterns) {
    return new CDate(CDateUtil.valueOf(str, parsePatterns));
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
   */
  public static CDate valueOf(final String str, final String... parsePatterns) {
    return of(str, parsePatterns);
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
   */
  public static CDate of(final String str, final Locale locale, final String... parsePatterns) {
    return new CDate(CDateUtil.valueOf(str, locale, parsePatterns));
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
   */
  public static CDate valueOf(final String str, final Locale locale, final String... parsePatterns) {
    return of(CDateUtil.valueOf(str, locale, parsePatterns));
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
  public static CDate valueOfOrNull(final String str, final String... parsePatterns) {
    Date date = CDateUtil.valueOfOrNull(str, parsePatterns);
    return date == null ? null : of(date);
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
  public static CDate valueOfOrNull(final String str, final Locale locale, final String... parsePatterns) {
    Date date = CDateUtil.valueOfOrNull(str, locale, parsePatterns);
    return date == null ? null : of(date);
  }

  /**
   * Adds a number of days to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addDays(final int amount) {
    return add(Calendar.DAY_OF_YEAR, amount);
  }

  /**
   * Adds a number of hours to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addHours(final int amount) {
    return add(Calendar.HOUR_OF_DAY, amount);
  }

  /**
   * Adds a number of milliseconds to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addMilliseconds(final int amount) {
    return add(Calendar.MILLISECOND, amount);
  }

  /**
   * Adds a number of minutes to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addMinutes(final int amount) {
    return add(Calendar.MINUTE, amount);
  }

  /**
   * Adds a number of months to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addMonths(final int amount) {
    return add(Calendar.MONTH, amount);
  }

  /**
   * Adds a number of seconds to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addSeconds(final int amount) {
    return add(Calendar.SECOND, amount);
  }

  /**
   * Adds a number of weeks to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addWeeks(final int amount) {
    return add(Calendar.WEEK_OF_YEAR, amount);
  }

  /**
   * Adds a number of years to a date returning a new object.
   *
   * @param amount the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate addYears(final int amount) {
    return add(Calendar.YEAR, amount);
  }

  public LocalDate asLocalDate() {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * Gets a date ceiling, leaving the field specified as the most significant field.
   *
   * <p>For example, if you had the date-time of 28 Mar 2002 13:45:01.231, if you passed with HOUR,
   * it would return 28 Mar 2002 14:00:00.000. If this was passed with MONTH, it would return 1 Apr
   * 2002 0:00:00.000.
   *
   * @param field the field from {@code Calendar} or <code>SEMI_MONTH</code>
   * @return the different ceil date, not null
   * @throws IllegalArgumentException if the date is <code>null</code>
   * @throws ArithmeticException      if the year is over 280 million
   */
  public CDate ceiling(final int field) {
    setTime(DateUtils.ceiling(this, field).getTime());
    return this;
  }

  /**
   * Create a deep copy of CDate object
   *
   * @return CDate with the time set to value of getTime()
   */
  public CDate clone() {
    return new CDate(getTime());
  }

  /**
   * Compare value of given date against current object using the particular date format. To do so,
   * we convert date values to formatted string using provided format and then compare then using
   * string compression.
   *
   * @param date   date to compare current value against
   * @param format the format which should be used during compression.
   * @return the value {@code 0} if the argument Date is equal to this Date; a value less than
   * {@code 0} if this Date is before the Date argument; and a value greater than
   */
  public int compareDateByFormat(Date date, String format) {
    return CDateUtil.compareDateByFormat(this, date, format);
  }

  /**
   * Compare value of given date against current object using MM/dd/yyyy date format. To do so, we
   * convert date values to formatted string using MM/dd/yyyy format and then compare then using
   * string compression.
   *
   * @param date date to compare current value against
   * @return the value {@code 0} if the values are equal; the value less than {@code 0} if the
   * string value is less and greater than {@code 0} if it is bigger
   * @see #compareDateByFormat(Date, String)
   * @see #compareTimePortion(Date)
   */
  public int compareDatePortion(Date date) {
    return compareDateByFormat(date, DATE_ONLY_FORMAT_STRING);
  }

  /**
   * Compare value of given date against current object using HH:mm:ss date format. To do so, we
   * convert date values to formatted string using HH:mm:ss format and then compare then using
   * string compression.
   *
   * @param date date to compare current value against
   * @return the value {@code 0} if the values are equal; the value less than {@code 0} if the
   * string value is less and greater than {@code 0} if it is bigger
   * @see #compareDateByFormat(Date, String)
   * @see #compareDatePortion(Date)
   */
  public int compareTimePortion(Date date) {
    return compareDateByFormat(date, TIME_FORMAT);
  }

  /**
   * Returns the value of the given calendar field. In lenient mode, all calendar fields are
   * normalized. In non-lenient mode, all calendar fields are validated and this method throws an
   * exception if any calendar fields have out-of-range values.
   *
   * @param field the given calendar field.
   * @return the value for the given calendar field.
   * @throws ArrayIndexOutOfBoundsException if the specified field is out of range (<code>
   *                                        field &lt; 0 || field &gt;= FIELD_COUNT</code>).
   * @see #set(int, int)
   */
  @JsonIgnore
  public int get(int field) {
    return toCalendar().get(field);
  }

  /**
   * Gets the day-of-month field.
   *
   * <p>This method returns the primitive {@code int} value for the day-of-month.
   *
   * @return the day-of-month, from 1 to 31
   */
  @JsonIgnore
  public int getDayOfMonth() {
    return get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Gets the day-of-week field.
   *
   * <p>This method returns the primitive {@code int} value for the day-of-week.
   *
   * @return the day-of-week, from 0 to 7
   */
  @JsonIgnore
  public int getDayOfWeek() {
    return get(Calendar.DAY_OF_WEEK);
  }

  /**
   * Gets the day-of-year field.
   *
   * <p>This method returns the primitive {@code int} value for the day-of-year.
   *
   * @return the day-of-year, from 1 to 365, or 366 in a leap year
   */
  @JsonIgnore
  public int getDayOfYear() {
    return get(Calendar.DAY_OF_YEAR);
  }

  @JsonIgnore
  public Duration getDurationFrom(Date date) {
    return CDateUtil.getDuration(date, this);
  }

  @JsonIgnore
  public Duration getDurationFromNow() {
    return CDateUtil.getDuration(now(), this);
  }

  @JsonIgnore
  public Duration getDurationTo(Date date) {
    return CDateUtil.getDuration(this, date);
  }

  @JsonIgnore
  public Duration getDurationToNow() {
    return CDateUtil.getDuration(this, now());
  }

  /**
   * Gets the era applicable at this date.
   *
   * <p>The official ISO-8601 standard does not define eras, however {@code IsoChronology} does. It
   * defines two eras, 'CE' from year one onwards and 'BCE' from year zero backwards. Since dates
   * before the Julian-Gregorian cutover are not in line with history, the cutover betweenExclusive
   * 'BCE' and 'CE' is also not aligned with the commonly used eras, often referred to using 'BC'
   * and 'AD'.
   *
   * <p>Users of this class should typically ignore this method as it exists primarily to fulfill
   * the {@link ChronoLocalDate} contract where it is necessary to support the Japanese calendar
   * system.
   *
   * @return the IsoEra applicable at this date, not null
   */
  @JsonIgnore
  public IsoEra getEra() {
    return toLocalTime().getEra();
  }

  /**
   * Returns the number of days within the fragment. All datefields greater than the fragment will
   * be ignored.
   *
   * <p>Asking the days of any date will only return the number of days of the current month
   * (resulting in a number betweenExclusive 1 and 31). This method will retrieve the number of days
   * for any fragment. For example, if you want to calculate the number of days past this year, your
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
  @JsonIgnore
  public long getFragmentInDays(final int fragment) {
    return DateUtils.getFragmentInDays(this, fragment);
  }

  // -----------------------------------------------------------------------

  /**
   * Returns the number of hours within the fragment. All datefields greater than the fragment will
   * be ignored.
   *
   * <p>Asking the hours of any date will only return the number of hours of the current day
   * (resulting in a number betweenExclusive 0 and 23). This method will retrieve the number of
   * hours for any fragment. For example, if you want to calculate the number of hours past this
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
  @JsonIgnore
  public long getFragmentInHours(final int fragment) {
    return DateUtils.getFragmentInHours(this, fragment);
  }

  /**
   * Returns the number of milliseconds within the fragment. All datefields greater than the
   * fragment will be ignored.
   *
   * <p>Asking the milliseconds of any date will only return the number of milliseconds of the
   * current second (resulting in a number betweenExclusive 0 and 999). This method will retrieve
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
  @JsonIgnore
  public long getFragmentInMilliseconds(final int fragment) {
    return DateUtils.getFragmentInMilliseconds(this, fragment);
  }

  /**
   * Returns the number of minutes within the fragment. All datefields greater than the fragment
   * will be ignored.
   *
   * <p>Asking the minutes of any date will only return the number of minutes of the current hour
   * (resulting in a number betweenExclusive 0 and 59). This method will retrieve the number of
   * minutes for any fragment. For example, if you want to calculate the number of minutes past this
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
  @JsonIgnore
  public long getFragmentInMinutes(final int fragment) {
    return DateUtils.getFragmentInMinutes(this, fragment);
  }

  /**
   * Returns the number of seconds within the fragment. All datefields greater than the fragment
   * will be ignored.
   *
   * <p>Asking the seconds of any date will only return the number of seconds of the current minute
   * (resulting in a number betweenExclusive 0 and 59). This method will retrieve the number of
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
  @JsonIgnore
  public long getFragmentInSeconds(final int fragment) {
    return DateUtils.getFragmentInSeconds(this, fragment);
  }

  /**
   * Gets the month-of-year field using the {@code Month} enum.
   *
   * <p>This method returns the enum {@link Month} for the month. This avoids confusion as to what
   * {@code int} values mean. If you need access to the primitive {@code int} value then the enum
   * provides the {@link Month#getValue() int value}.
   *
   * @return the month-of-year, not null
   */
  @JsonIgnore
  public Month getMonthName() {
    return Month.of(get(Calendar.MONTH) + 1);
  }

  @JsonIgnore
  public Timestamp getTimeStamp() {
    return new Timestamp(getTime());
  }

  /**
   * Checks if the year is a leap year, according to the ISO proleptic calendar system rules.
   *
   * <p>This method applies the current rules for leap years across the whole time-line. In general,
   * a year is a leap year if it is divisible by four without remainder. However, years divisible by
   * 100, are not leap years, with the exception of years divisible by 400 which are.
   *
   * <p>For example, 1904 is a leap year it is divisible by 4. 1900 was not a leap year as it is
   * divisible by 100, however 2000 was a leap year as it is divisible by 400.
   *
   * <p>The calculation is proleptic - applying the same rules into the far future and far past.
   * This is historically inaccurate, but is correct for the ISO-8601 standard.
   *
   * @return true if the year is leap, false otherwise
   */
  @JsonIgnore
  public boolean isLeapYear() {
    return toLocalTime().isLeapYear();
  }

  /**
   * Checks if two date objects are on the same day ignoring time.
   *
   * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002 13:45 and 12 Mar 2002
   * 13:45 would return false.
   *
   * @param date the date, not altered, not null
   * @return true if they represent the same day
   * @throws IllegalArgumentException if date is <code>null</code>
   */
  @JsonIgnore
  public boolean isSameDay(final Date date) {
    return DateUtils.isSameDay(this, date);
  }

  /**
   * Checks if two date objects represent the same instant in time.
   *
   * <p>This method compares the long millisecond time of the two objects.
   *
   * @param date the date, not altered, not null
   * @return true if they represent the same millisecond instant
   * @throws IllegalArgumentException if date is <code>null</code>
   */
  @JsonIgnore
  public boolean isSameInstant(final Date date) {
    return DateUtils.isSameInstant(this, date);
  }

  /**
   * Checks if two calendar objects represent the same local time.
   *
   * <p>This method compares the values of the fields of the two objects. In addition, both
   * calendars must be the same of the same type.
   *
   * @param date the calendar, not altered, not null
   * @return true if they represent the same millisecond instant
   * @throws IllegalArgumentException if date is <code>null</code>
   */
  @JsonIgnore
  public boolean isSameLocalTime(final Date date) {
    Calendar instance2 = Calendar.getInstance();
    instance2.setTime(date);
    return DateUtils.isSameLocalTime(toCalendar(), instance2);
  }

  @JsonIgnore
  public boolean isTodayDate() {
    return compareDatePortion(new CDate()) == 0;
  }

  @JsonIgnore
  public boolean isMonday() {
    return getDayOfWeek() == Calendar.MONDAY;
  }

  @JsonIgnore
  public boolean isSaturday() {
    return getDayOfWeek() == Calendar.SATURDAY;
  }

  @JsonIgnore
  public boolean isSunday() {
    return getDayOfWeek() == Calendar.SUNDAY;
  }

  @JsonIgnore
  public boolean isThursday() {
    return getDayOfWeek() == Calendar.THURSDAY;
  }

  @JsonIgnore
  public boolean isTuesday() {
    return getDayOfWeek() == Calendar.TUESDAY;
  }

  @JsonIgnore
  public boolean isWednesday() {
    return getDayOfWeek() == Calendar.WEDNESDAY;
  }

  @JsonIgnore
  public boolean isFriday() {
    return getDayOfWeek() == Calendar.FRIDAY;
  }

  /**
   * Returns the length of the month represented by this date.
   *
   * <p>This returns the length of the month in days. For example, a date in January would return
   * 31.
   *
   * @return the length of the month in days
   */
  public int lengthOfMonth() {
    return toLocalTime().lengthOfMonth();
  }

  /**
   * Returns the length of the year represented by this date.
   *
   * <p>This returns the length of the year in days, either 365 or 366.
   *
   * @return 366 if the year is leap, 365 otherwise
   */
  public int lengthOfYear() {
    return toLocalTime().lengthOfYear();
  }

  /**
   * Rounds a date, leaving the field specified as the most significant field.
   *
   * <p>For example, if you had the date-time of 28 Mar 2002 13:45:01.231, if this was passed with
   * HOUR, it would return 28 Mar 2002 14:00:00.000. If this was passed with MONTH, it would return
   * 1 April 2002 0:00:00.000.
   *
   * <p>For a date in a timezone that handles the change to daylight saving time, rounding to
   * Calendar.HOUR_OF_DAY will behave as follows. Suppose daylight saving time begins at 02:00 on
   * March 30. Rounding a date that crosses this time would produce the following values:
   *
   * <ul>
   *   <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00
   *   <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00
   *   <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00
   *   <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00
   * </ul>
   *
   * @param field the field from {@code Calendar} or {@code SEMI_MONTH}
   * @return the different rounded date, not null
   * @throws ArithmeticException if the year is over 280 million
   */
  public CDate round(final int field) {
    setTime(DateUtils.round(this, field).getTime());
    return this;
  }

  /**
   * Sets the day of month field to a date returning a new object.
   *
   * @param amount the amount to set
   * @return {@code CDate} set with the specified value
   * @throws IllegalArgumentException if the date is null
   */
  public CDate setDays(final int amount) {
    return set(Calendar.DAY_OF_MONTH, amount);
  }

  /**
   * Sets the milliseconds field to a date returning a new object.
   *
   * @param amount the amount to set
   * @return {@code CDate} set with the specified value
   * @throws IllegalArgumentException if the date is null
   */
  public CDate setMilliseconds(final int amount) {
    return set(Calendar.MILLISECOND, amount);
  }

  /**
   * Sets the months field to a date returning a new object.
   *
   * @param amount the amount to set
   * @return {@code CDate} set with the specified value
   * @throws IllegalArgumentException if the date is null
   */
  public CDate setMonths(final int amount) {
    return set(Calendar.MONTH, amount);
  }

  /**
   * Sets the years field to a date returning a new object.
   *
   * @param amount the amount to set
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  public CDate setYears(final int amount) {
    return set(Calendar.YEAR, amount);
  }

  /**
   * Converts a {@code Date} into a {@code Calendar}.
   *
   * @return the created Calendar
   * @throws NullPointerException if null is passed in
   */
  public Calendar toCalendar() {
    return DateUtils.toCalendar(this);
  }

  /**
   * Converts a {@code Date} of a given {@code TimeZone} into a {@code Calendar}
   *
   * @param tz the time zone of the {@code date}
   * @return the created Calendar
   * @throws NullPointerException if {@code date} or {@code tz} is null
   */
  public Calendar toCalendar(final TimeZone tz) {
    return DateUtils.toCalendar(this, tz);
  }

  public String toDateOnlyString() {
    return toFormat(DATE_ONLY_FORMAT_STRING);
  }

  // -----------------------------------------------------------------
  // Duration
  // -----------------------------------------------------------------
  public Duration toDuration() {
    return Duration.ofMillis(getTime());
  }

  public String toFormat(String format) {
    return CDateUtil.toFormat(this, format);
  }

  public String toFormat(String format, TimeZone timeZone) {
    return CDateUtil.toFormat(this, format, timeZone);
  }

  public String toFormattedDuration() {
    return getFormattedDuration(toDuration());
  }

  public String toFormattedDurationFrom(Date date) {
    return getFormattedDuration(getDurationFrom(date));
  }

  public String toFormattedDurationFromNow() {
    return getFormattedDuration(getDurationFrom(now()));
  }

  public String toFormattedDurationTo(Date date) {
    return getFormattedDuration(getDurationTo(date));
  }

  public String toFormattedDurationToNow() {
    return getFormattedDuration(getDurationTo(now()));
  }

  public LocalDate toLocalTime() {
    return toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public String toLongDate() {
    return toFormat(LONG_DATE_FORMAT_STRING_MILI_SECONDS);
  }

  public String toString(final String format) {
    return toFormat(format);
  }

  public String toString() {
    return toFormat(LONG_DATE_FORMAT_STRING_MILI_SECONDS);
  }

  public String toTimeStampForFileName() {
    return toFormat(FILENAME_TIMESTAMP_FORMAT);
  }

  public CDate trimTime() {
    setTime(of(toFormat(DATE_ONLY_FORMAT_STRING), DATE_ONLY_FORMAT_STRING).getTime());
    return this;
  }

  /**
   * Truncates a date, leaving the field specified as the most significant field.
   *
   * <p>For example, if you had the date-time of 28 Mar 2002 13:45:01.231, if you passed with HOUR,
   * it would return 28 Mar 2002 13:00:00.000. If this was passed with MONTH, it would return 1 Mar
   * 2002 0:00:00.000.
   *
   * @param field the field from {@code Calendar} or <code>SEMI_MONTH</code>
   * @return the different truncated date, not null
   * @throws IllegalArgumentException if the date is <code>null</code>
   * @throws ArithmeticException      if the year is over 280 million
   */
  public CDate truncate(final int field) {
    setTime(DateUtils.truncate(this, field).getTime());
    return this;
  }

  /**
   * Determines how two dates compare up to no more than the specified most significant field.
   *
   * @param date  the date, not <code>null</code>
   * @param field the field from <code>Calendar</code>
   * @return a negative integer, zero, or a positive integer as the first date is less than, equal
   * to, or greater than the second.
   * @throws IllegalArgumentException if any argument is <code>null</code>
   */
  public int truncatedCompareTo(final Date date, final int field) {
    return DateUtils.truncatedCompareTo(this, date, field);
  }

  /**
   * Determines if two dates are equal up to no more than the specified most significant field.
   *
   * @param date  the date, not <code>null</code>
   * @param field the field from {@code Calendar}
   * @return <code>true</code> if equal; otherwise <code>false</code>
   * @throws IllegalArgumentException if any argument is <code>null</code>
   */
  public boolean truncatedEquals(final Date date, final int field) {
    return DateUtils.truncatedEquals(this, date, field);
  }

  /**
   * Adds to a date returning a new object. if
   *
   * @param calendarField the calendar field to add to
   * @param amount        the amount to add, may be negative
   * @return current {@code CDate}
   * @throws IllegalArgumentException if the date is null
   */
  private CDate add(final int calendarField, final int amount) {
    setTime(CDateUtil.add(this, calendarField, amount).getTime());
    return this;
  }

  /**
   * Sets the specified field to a date returning a new object. This does not use a lenient
   * calendar.
   *
   * @param calendarField the {@code Calendar} field to set the amount to
   * @param amount        the amount to set
   * @return {@code CDate} set with the specified value
   * @throws IllegalArgumentException if the date is null
   */
  private CDate set(final int calendarField, final int amount) {
    final Calendar c = toCalendar();
    c.setLenient(false);
    c.set(calendarField, amount);
    setTime(c.getTime().getTime());
    return this;
  }

  @Override
  @JsonIgnore
  public CDate _get() {
    return this;
  }

  @JsonIgnore
  private static String getFormattedDuration(Duration duration) {
    String time = String.format("%02d:%02d:%02d:%03d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart(), duration.toMillisPart()).replaceAll("\\s+", "0");

    if (duration.toDaysPart() > 0) {
      return String.format("%dd %s", duration.toDaysPart(), time);
    }
    return time;
  }
}
