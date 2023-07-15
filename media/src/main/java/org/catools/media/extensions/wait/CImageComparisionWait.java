package org.catools.media.extensions.wait;

import org.catools.common.extensions.wait.CObjectWait;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.extensions.wait.interfaces.CImageComparisionWaiter;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;

/**
 * BufferedImage wait class contains all wait method which is related to BufferedImage
 */
public class CImageComparisionWait extends CObjectWait {
  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(final BufferedImage actual, final BufferedImage expected) {
    return toWaiter(actual).waitEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final BufferedImage actual, final BufferedImage expected, final int waitInSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final BufferedImage actual,
      final BufferedImage expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(final CFile actual, final BufferedImage expected) {
    return toWaiter(actual).waitEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final CFile actual, final BufferedImage expected, final int waitInSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final CFile actual,
      final BufferedImage expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(final CResource actual, final BufferedImage expected) {
    return toWaiter(actual).waitEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final CResource actual, final BufferedImage expected, final int waitInSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEquals(
      final CResource actual,
      final BufferedImage expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(final BufferedImage actual, final BufferedImage expected) {
    return toWaiter(actual).waitNotEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final BufferedImage actual, final BufferedImage expected, final int waitInSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final BufferedImage actual,
      final BufferedImage expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(final CFile actual, final BufferedImage expected) {
    return toWaiter(actual).waitNotEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final CFile actual, final BufferedImage expected, final int waitInSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final CFile actual,
      final BufferedImage expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(final CResource actual, final BufferedImage expected) {
    return toWaiter(actual).waitNotEquals(expected);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual        value to compare
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final CResource actual, final BufferedImage expected, final int waitInSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param actual                 value to compare
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEquals(
      final CResource actual,
      final BufferedImage expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actual).waitNotEquals(expected, waitInSeconds, intervalInMilliSeconds);
  }

  private static CImageComparisionWaiter toWaiter(BufferedImage actual) {
    return () -> actual;
  }

  private static CImageComparisionWaiter toWaiter(CFile actual) {
    return () -> CImageUtil.readImageOrNull(actual);
  }

  private static CImageComparisionWaiter toWaiter(CResource actual) {
    return () -> CImageUtil.readImageOrNull(actual);
  }
}
