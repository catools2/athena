package org.catools.media.extensions.verify.interfaces.base;

import boofcv.factory.template.TemplateScoreType;
import org.catools.common.extensions.verify.interfaces.CBaseVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.configs.CMediaConfigs;
import org.catools.media.extensions.states.interfaces.CImageComparisonState;
import org.catools.media.extensions.verify.hard.CImageComparisonVerification;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;
import static org.catools.media.utils.CImageUtil.toBufferedImage;

/**
 * CImageComparisonVerifier is an interface for BufferedImage verification related methods (from one of supported sources).
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link CImageComparisonVerification} for each new
 * added verification here
 */
public interface CImageComparisonVerify<O> extends CBaseVerify<O, CImageComparisonState<O>> {

  /**
   * Verify if actual is null
   */
  default void verifyIsNull() {
    verifyIsNull(getDefaultMessage("Is Null"));
  }

  /**
   * Verify if actual is null
   */
  default void verifyIsNull(final String message, final Object... params) {
    _verify(null, (a, b) -> _toState(a).isNull(), message, params);
  }

  /**
   * Verify if actual is NOT null
   */
  default void verifyIsNotNull() {
    verifyIsNotNull(getDefaultMessage("Is not null"));
  }

  /**
   * Verify if actual is NOT null
   */
  default void verifyIsNotNull(final String message, final Object... params) {
    _verify(null, (a, b) -> _toState(a).isNotNull(), message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   */
  default void verifyEquals(final BufferedImage expected, final String diffFileName) {
    verifyEquals(expected, diffFileName, getDefaultMessage("Equals"));
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
  default void verifyEquals(final BufferedImage expected, final String diffFileName, final String message, final Object... params) {
    _verify(expected,
        (a, b) -> _toState(a).isEqual(b),
        (a, b) -> CImageUtil.generateDiffFile(toBufferedImage(a), b, diffFileName, GRAY_FLOAT_32),
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   */
  default void verifyEquals(final CFile expected) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getName(), getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CFile expected, final String message, final Object... params) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getName(), message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   */
  default void verifyEquals(final CResource expected) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getResourceName(), getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CResource expected, final String message, final Object... params) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getResourceName(), message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   */
  default void verifyNotEquals(final BufferedImage expected, final String diffFileName) {
    verifyNotEquals(expected, diffFileName, getDefaultMessage("Not Equals"));
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
  default void verifyNotEquals(final BufferedImage expected, final String diffFileName, final String message, final Object... params) {
    _verify(expected,
        (a, b) -> _toState(a).isNotEqual(b),
        (a, b) -> CImageUtil.generateDiffFile(toBufferedImage(a), b, diffFileName, GRAY_FLOAT_32),
        message,
        params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   */
  default void verifyNotEquals(final CFile expected) {
    verifyNotEquals(expected, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CFile expected, final String message, final Object... params) {
    _verify(toBufferedImage(expected), (a, b) -> _toState(a).isNotEqual(b), message, params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   */
  default void verifyNotEquals(final CResource expected) {
    verifyNotEquals(expected, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CResource expected, final String message, final Object... params) {
    _verify(toBufferedImage(expected), (a, b) -> _toState(a).isNotEqual(b), message, params);
  }

  /**
   * Verify that actual equals to any expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   */
  default void verifyEqualsAny(final Iterable<?> expected, final String diffFileNamePrefix) {
    verifyEqualsAny(expected, diffFileNamePrefix, getDefaultMessage("Equals Any"));
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
  default void verifyEqualsAny(final Iterable<?> expected, final String diffFileNamePrefix, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).equalsAny(e), (a, e) -> _toState(a).generateDiffForAllExpected(diffFileNamePrefix, e), message, params);
  }

  /**
   * Verify that actual equals to none of expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   */
  default void verifyEqualsNone(final Iterable<?> expected, final String diffFileNamePrefix) {
    verifyEqualsNone(expected, diffFileNamePrefix, getDefaultMessage("Equals None"));
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
  default void verifyEqualsNone(final Iterable<?> expected, final String diffFileNamePrefix, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).equalsNone(e), (a, e) -> _toState(a).generateDiffForAllExpected(diffFileNamePrefix, e), message, params);
  }


  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   */
  default void verifyContains(final BufferedImage expected) {
    verifyContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   */
  default void verifyContains(final File expected) {
    verifyContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   */
  default void verifyContains(final CResource expected) {
    verifyContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    verifyContains(expected, scoreType, mask, matchPrecision, getDefaultMessage("Contains"));
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    _verify(expected,
        (a, b) -> _toState(a).contains(b, scoreType, mask, matchPrecision),
        message,
        params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   */
  default void verifyNotContains(final BufferedImage expected) {
    verifyNotContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   */
  default void verifyNotContains(final File expected) {
    verifyNotContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   */
  default void verifyNotContains(final CResource expected) {
    verifyNotContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @param message   information about the purpose of this verification
   * @param params    parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    verifyNotContains(expected, scoreType, mask, matchPrecision, getDefaultMessage("Not Contains"));
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    _verify(expected,
        (a, b) -> _toState(a).notContains(b, scoreType, mask, matchPrecision),
        message,
        params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, message, params);
  }


  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   */
  default void verifyContainsAny(final Iterable<?> expected) {
    verifyContainsAny(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, final String message, final Object... params) {
    verifyContainsAny(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return false if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType) {
    verifyContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
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
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision);
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
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    verifyContainsAny(expected, scoreType, mask, matchPrecision, getDefaultMessage("Contains Any"));
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
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    _verify(expected,
        (a, b) -> _toState(a).containsAny(b, scoreType, mask, matchPrecision),
        message,
        params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision);
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
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision);
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
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return true if no value provided. all {@code null} value in
   *                 expected list ignores.
   */
  default void verifyContainsNone(final Iterable<?> expected) {
    verifyContainsNone(expected, TemplateScoreType.NCC);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return true if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, final String message, final Object... params) {
    verifyContainsNone(expected, TemplateScoreType.NCC, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return true if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType) {
    verifyContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
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
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, final String message, final Object... params) {
    verifyContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    verifyContainsNone(expected, scoreType, null, matchPrecision);
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
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final String message, final Object... params) {
    verifyContainsNone(expected, scoreType, null, matchPrecision, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    verifyContainsNone(expected, scoreType, mask, matchPrecision, getDefaultMessage("Contains None"));
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
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final String message, final Object... params) {
    _verify(expected,
        (a, b) -> _toState(a).containsAny(b, scoreType, mask, matchPrecision),
        message,
        params);
  }

  default CImageComparisonState<O> _toState(O o) {
    return () -> o;
  }
}
