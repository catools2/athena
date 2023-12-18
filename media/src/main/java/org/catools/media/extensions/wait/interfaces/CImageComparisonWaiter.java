package org.catools.media.extensions.wait.interfaces;

import boofcv.factory.template.TemplateScoreType;
import org.catools.common.extensions.wait.interfaces.CBaseWaiter;
import org.catools.common.io.CResource;
import org.catools.media.configs.CMediaConfigs;
import org.catools.media.extensions.states.interfaces.CImageComparisonState;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.catools.media.utils.CImageUtil.toBufferedImage;

/**
 * CImageFileWaiter is an interface for O waiter related methods.
 */
public interface CImageComparisonWaiter<O> extends CBaseWaiter<O> {

  /**
   * Wait that actual and expected are equal
   *
   * @param expected value to compare
   */
  default boolean waitEquals(final BufferedImage expected) {
    return waitEquals(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitEquals(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isEqual(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual and expected are equal
   *
   * @param expected value to compare
   */
  default boolean waitEquals(final File expected) {
    return waitEquals(CImageUtil.readImageOrNull(expected), getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitEquals(final File expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitEquals(CImageUtil.readImageOrNull(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual and expected are equal
   *
   * @param expected value to compare
   */
  default boolean waitEquals(final CResource expected) {
    return waitEquals(CImageUtil.readImageOrNull(expected), getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitEquals(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitEquals(CImageUtil.readImageOrNull(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual and expected are equal
   *
   * @param expected value to compare
   */
  default boolean waitNotEquals(final BufferedImage expected) {
    return waitNotEquals(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitNotEquals(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).isNotEqual(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual and expected are not equal
   *
   * @param expected value to compare
   */
  default boolean waitNotEquals(final File expected) {
    return waitNotEquals(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitNotEquals(final File expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotEquals(toBufferedImage(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual and expected are not equal
   *
   * @param expected value to compare
   */
  default boolean waitNotEquals(final CResource expected) {
    return waitNotEquals(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitNotEquals(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotEquals(toBufferedImage(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual equals to any expected value
   *
   * @param expected value to compare
   */
  default boolean waitEqualsAny(final Iterable<?> expected) {
    return waitEqualsAny(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual equals to any expected value
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitEqualsAny(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).equalsAny(expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual equals to none of expected value
   *
   * @param expected value to compare
   */
  default boolean waitEqualsNone(final Iterable<?> expected) {
    return waitEqualsNone(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual equals to none of expected value
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default boolean waitEqualsNone(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).equalsNone(expected), waitInSeconds, intervalInMilliSeconds);
  }


  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected) {
    return waitContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean waitContains(final File expected) {
    return waitContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final File expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean waitContains(final CResource expected) {
    return waitContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType) {
    return waitContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType) {
    return waitContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType) {
    return waitContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return waitContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).contains(expected, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return waitContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return waitContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return waitContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return waitContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return waitContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return waitContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return waitContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return waitContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected) {
    return waitNotContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean waitNotContains(final File expected) {
    return waitNotContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final File expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected) {
    return waitNotContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType) {
    return waitNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType) {
    return waitNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType) {
    return waitNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitNotContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitNotContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitNotContains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return waitNotContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).notContains(expected, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return waitNotContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return waitNotContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return waitNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return waitNotContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return waitNotContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return waitNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return waitNotContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return waitNotContains(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }


  /**
   * Wait that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected) {
    return waitContainsAny(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsAny(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return false if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType) {
    return waitContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    return waitContainsAny(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).containsAny(expected, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision) {
    return waitContainsAny(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision) {
    return waitContainsAny(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return true if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected) {
    return waitContainsNone(expected, TemplateScoreType.NCC);
  }

  /**
   * Wait that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsNone(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return true if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected, TemplateScoreType scoreType) {
    return waitContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Wait that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    return waitContainsNone(expected, scoreType, null, matchPrecision);
  }

  /**
   * Wait that actual image does not contain any of expected images.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return waitContainsNone(expected, scoreType, null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    return waitContainsNone(expected, scoreType, mask, matchPrecision, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait that actual image does not contain any of expected images.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return execution result
   */
  default boolean waitContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).containsNone(expected, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds);
  }

  private CImageComparisonState<O> toState(O e) {
    return () -> e;
  }
}
