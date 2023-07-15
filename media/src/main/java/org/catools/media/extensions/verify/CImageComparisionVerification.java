package org.catools.media.extensions.verify;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.hard.CBaseVerification;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.extensions.verify.interfaces.CImageComparisonVerify;

import java.awt.image.BufferedImage;

/**
 * Image verification class contains all verification method which is related to Image
 */
@Slf4j
public class CImageComparisionVerification extends CBaseVerification {

  /**
   * Verify that actual and expected have same boolean value or be null
   *
   * @param actual       value to compare
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equals(
      final BufferedImage actual,
      final BufferedImage expected,
      final String diffFileName,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyEquals(expected, diffFileName, message, params);
  }

  /**
   * Verify that actual and expected have same boolean value or be null
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(
      final BufferedImage actual,
      final CFile expected,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected have same boolean value or be null
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(
      final BufferedImage actual,
      final CResource expected,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(
      final BufferedImage actual,
      final BufferedImage expected,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(
      final BufferedImage actual,
      final CFile expected,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected has different boolean value
   *
   * @param actual   value to compare
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(
      final BufferedImage actual,
      final CResource expected,
      final String message,
      final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  private CImageComparisonVerify toVerifier(BufferedImage actual) {
    return new CImageComparisonVerify() {
      @Override
      public BufferedImage _get() {
        return actual;
      }
    };
  }
}
