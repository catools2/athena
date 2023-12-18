package org.catools.media.extensions.verify.hard;

import boofcv.factory.template.TemplateScoreType;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.hard.CBaseVerification;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.extensions.verify.interfaces.base.CImageComparisonVerify;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Image verification class contains all verification method which is related to Image
 */
@Slf4j
public class CImageComparisonVerification<O> extends CBaseVerification {

  /**
   * Verify actual is null
   *
   * @return execution result
   */
  public void isNull(final O actual) {
    toVerifier(actual).verifyIsNull();
  }

  /**
   * Verify actual is null
   *
   * @return execution result
   */
  public void isNull(final O actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNull(message, params);
  }

  /**
   * Verify actual is NOT null
   *
   * @return execution result
   */
  public void isNotNull(final O actual) {
    toVerifier(actual).verifyIsNotNull();
  }

  /**
   * Verify actual is NOT null
   *
   * @return execution result
   */
  public void isNotNull(final O actual, final String message, final Object... params) {
    toVerifier(actual).verifyIsNotNull(message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   */
  public void equals(final O actual, final BufferedImage expected, final String diffFileName) {
    toVerifier(actual).verifyEquals(expected, diffFileName);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void equals(final O actual, final BufferedImage expected, final String diffFileName, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, diffFileName, message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   */
  public void equals(final O actual, final CFile expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(final O actual, final CFile expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   */
  public void equals(final O actual, final CResource expected) {
    toVerifier(actual).verifyEquals(expected);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void equals(final O actual, final CResource expected, final String message, final Object... params) {
    toVerifier(actual).verifyEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   */
  public void notEquals(final O actual, final BufferedImage expected, final String diffFileName) {
    toVerifier(actual).verifyNotEquals(expected, diffFileName);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  public void notEquals(final O actual, final BufferedImage expected, final String diffFileName, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, diffFileName, message, params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   */
  public void notEquals(final O actual, final CFile expected) {
    toVerifier(actual).verifyNotEquals(expected);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(final O actual, final CFile expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   */
  public void notEquals(final O actual, final CResource expected) {
    toVerifier(actual).verifyNotEquals(expected);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  public void notEquals(final O actual, final CResource expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotEquals(expected, message, params);
  }

  /**
   * Verify that actual equals to any expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   */
  public void equalsAny(final O actual, final Iterable<?> expected, final String diffFileNamePrefix) {
    toVerifier(actual).verifyEqualsAny(expected, diffFileNamePrefix);
  }

  /**
   * Verify that actual equals to any expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param message            information about the purpose of this verification
   * @param params             parameters in case if message is a format {@link String#format}
   */
  public void equalsAny(final O actual, final Iterable<?> expected, final String diffFileNamePrefix, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsAny(expected, diffFileNamePrefix, message, params);
  }

  /**
   * Verify that actual equals to none of expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   */
  public void equalsNone(final O actual, final Iterable<?> expected, final String diffFileNamePrefix) {
    toVerifier(actual).verifyEqualsNone(expected, diffFileNamePrefix);
  }

  /**
   * Verify that actual equals to none of expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param message            information about the purpose of this verification
   * @param params             parameters in case if message is a format {@link String#format}
   */
  public void equalsNone(final O actual, final Iterable<?> expected, final String diffFileNamePrefix, final String message, final Object... params) {
    toVerifier(actual).verifyEqualsNone(expected, diffFileNamePrefix, message, params);
  }


  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected) {
    toVerifier(actual).verifyContains(expected);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  public void contains(final O actual, final File expected) {
    toVerifier(actual).verifyContains(expected);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final File expected, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  public void contains(final O actual, final CResource expected) {
    toVerifier(actual).verifyContains(expected);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyContains(expected, scoreType);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyContains(expected, scoreType);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyContains(expected, scoreType);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void contains(final O actual, final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected) {
    toVerifier(actual).verifyNotContains(expected);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  public void notContains(final O actual, final File expected) {
    toVerifier(actual).verifyNotContains(expected);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final File expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected) {
    toVerifier(actual).verifyNotContains(expected);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyNotContains(expected, scoreType);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyNotContains(expected, scoreType);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyNotContains(expected, scoreType);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void notContains(final O actual, final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyNotContains(expected, scoreType, mask, matchPrecision, message, params);
  }


  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected) {
    toVerifier(actual).verifyContainsAny(expected);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAny(expected, message, params);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return false if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyContainsAny(expected, scoreType);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return false if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, matchPrecision);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsAny(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContainsAny(expected, scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return true if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected) {
    toVerifier(actual).verifyContainsNone(expected);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return true if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected, final String message, final Object... params) {
    toVerifier(actual).verifyContainsNone(expected, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return true if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected, TemplateScoreType scoreType) {
    toVerifier(actual).verifyContainsNone(expected, scoreType);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return true if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, final String message, final Object... params) {
    toVerifier(actual).verifyContainsNone(expected, scoreType, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    toVerifier(actual).verifyContainsNone(expected, scoreType, matchPrecision);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContainsNone(expected, scoreType, matchPrecision, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    toVerifier(actual).verifyContainsNone(expected, scoreType, mask, matchPrecision);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   * @return execution result
   */
  public void containsNone(final O actual, final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    toVerifier(actual).verifyContainsNone(expected, scoreType, mask, matchPrecision, message, params);
  }

  protected CImageComparisonVerify toVerifier(O actual) {
    return () -> actual;
  }
}
