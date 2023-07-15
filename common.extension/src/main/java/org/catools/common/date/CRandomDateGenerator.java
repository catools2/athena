package org.catools.common.date;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

public class CRandomDateGenerator {
  private Calendar from = Calendar.getInstance();
  private Calendar to = Calendar.getInstance();

  private CRandomDateGenerator() {
  }

  public static CRandomDateGenerator instance() {
    return new CRandomDateGenerator();
  }

  public static CRandomDateGenerator instance(Calendar from, Calendar to) {
    return new CRandomDateGenerator().setFrom(from).setTo(to);
  }

  public static CRandomDateGenerator instance(Date from, Date to) {
    return new CRandomDateGenerator().setFrom(from).setTo(to);
  }

  public static CRandomDateGenerator instance(int fromYear, int toYear) {
    return new CRandomDateGenerator().setFromYear(fromYear).setToYear(toYear);
  }

  public static String getFormattedDate(Calendar from, Calendar to, String format) {
    return new CRandomDateGenerator().setFrom(from).setTo(to).getDate(format);
  }

  public static String getFormattedDate(Date from, Date to, String format) {
    return new CRandomDateGenerator().setFrom(from).setTo(to).getDate(format);
  }

  public static String getFormattedDate(int fromYear, int toYear, String format) {
    return new CRandomDateGenerator().setFromYear(fromYear).setToYear(toYear).getDate(format);
  }

  public CRandomDateGenerator setFrom(Calendar from) {
    this.from = from;
    return this;
  }

  public CRandomDateGenerator setFrom(Date from) {
    this.from.setTime(from);
    return this;
  }

  public CRandomDateGenerator setFromYear(int amount) {
    this.from.set(Calendar.YEAR, amount);
    return this;
  }

  public CRandomDateGenerator setFromMonth(int amount) {
    this.from.set(Calendar.MONTH, amount);
    return this;
  }

  public CRandomDateGenerator setFromDayOfYear(int amount) {
    this.from.set(Calendar.DAY_OF_YEAR, amount);
    return this;
  }

  public CRandomDateGenerator setFromDayOfMonth(int amount) {
    this.from.set(Calendar.DAY_OF_MONTH, amount);
    return this;
  }

  public CRandomDateGenerator setFromHour(int amount) {
    this.from.set(Calendar.HOUR, amount);
    return this;
  }

  public CRandomDateGenerator setFromMinute(int amount) {
    this.from.set(Calendar.MINUTE, amount);
    return this;
  }

  public CRandomDateGenerator setFromSecond(int amount) {
    this.from.set(Calendar.SECOND, amount);
    return this;
  }

  public CRandomDateGenerator setFromMilliSecond(int amount) {
    this.from.set(Calendar.MILLISECOND, amount);
    return this;
  }

  public CRandomDateGenerator setTo(Calendar to) {
    this.to = to;
    return this;
  }

  public CRandomDateGenerator setTo(Date to) {
    this.to.setTime(to);
    return this;
  }

  public CRandomDateGenerator setToYear(int amount) {
    this.to.set(Calendar.YEAR, amount);
    return this;
  }

  public CRandomDateGenerator setToMonth(int amount) {
    this.to.set(Calendar.MONTH, amount);
    return this;
  }

  public CRandomDateGenerator setToDayOfYear(int amount) {
    this.to.set(Calendar.DAY_OF_YEAR, amount);
    return this;
  }

  public CRandomDateGenerator setToDayOfMonth(int amount) {
    this.to.set(Calendar.DAY_OF_MONTH, amount);
    return this;
  }

  public CRandomDateGenerator setToHour(int amount) {
    this.to.set(Calendar.HOUR, amount);
    return this;
  }

  public CRandomDateGenerator setToMinute(int amount) {
    this.to.set(Calendar.MINUTE, amount);
    return this;
  }

  public CRandomDateGenerator setToSecond(int amount) {
    this.to.set(Calendar.SECOND, amount);
    return this;
  }

  public CRandomDateGenerator setToMilliSecond(int amount) {
    this.to.set(Calendar.MILLISECOND, amount);
    return this;
  }

  public CDate getDate() {
    long fTime = from.getTime().getTime();
    long tTime = to.getTime().getTime();
    return new CDate(fTime + ((tTime - fTime) * (new SecureRandom().nextInt(8) + 1) / 10));
  }

  public String getDate(String format) {
    return getDate().toFormat(format);
  }
}
