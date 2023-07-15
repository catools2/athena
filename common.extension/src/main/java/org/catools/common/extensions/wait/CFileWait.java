package org.catools.common.extensions.wait;

import org.catools.common.extensions.wait.interfaces.CFileWaiter;
import org.catools.common.io.CFile;

import java.io.File;

/**
 * File wait class contains all wait method which is related to File and its content
 */
public class CFileWait extends CObjectWait {
  /**
   * Wait for defined number of seconds till the file exists
   *
   * @param actualFile             file full name to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsExists(
      final String actualFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitIsExists(new File(actualFile), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till the file exists
   *
   * @param actualFile             file full name to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsExists(
      final File actualFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actualFile).waitIsExists(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till the file does not exist
   *
   * @param actualFile             file full name to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotExists(
      final String actualFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitIsNotExists(new File(actualFile), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till the file does not exist
   *
   * @param actualFile             file full name to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitIsNotExists(
      final File actualFile, final int waitInSeconds, final int intervalInMilliSeconds) {
    return toWaiter(actualFile).waitIsNotExists(waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected file have the exact same content.
   *
   * @param actualFile             file full name to compare
   * @param expectedFile           file full name to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsStringContent(
      final String actualFile,
      final String expectedFile,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return waitEqualsStringContent(
        new CFile(actualFile), new CFile(expectedFile), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected file have the exact same content.
   *
   * @param actualFile             file to compare
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitEqualsStringContent(
      final File actualFile,
      final File expectedFile,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actualFile)
        .waitEqualsStringContent(expectedFile, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected file does not have the exact same
   * content.
   *
   * @param actualFile             file full name to compare
   * @param expectedFile           file full name to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEqualsStringContent(
      final String actualFile,
      final String expectedFile,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return waitNotEqualsStringContent(
        new CFile(actualFile), new CFile(expectedFile), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for defined number of seconds till actual and expected file does not have the exact same
   * content.
   *
   * @param actualFile             file to compare
   * @param expectedFile           file to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return return actual value or null if the timeout reached
   */
  public static boolean waitNotEqualsStringContent(
      final File actualFile,
      final File expectedFile,
      final int waitInSeconds,
      final int intervalInMilliSeconds) {
    return toWaiter(actualFile)
        .waitNotEqualsStringContent(new CFile(expectedFile), waitInSeconds, intervalInMilliSeconds);
  }

  private static CFileWaiter toWaiter(File actual) {
    return () -> actual == null ? null : new CFile(actual);
  }
}
