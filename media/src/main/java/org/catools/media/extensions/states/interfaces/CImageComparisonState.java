package org.catools.media.extensions.states.interfaces;

import boofcv.factory.template.TemplateScoreType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.collections.CList;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.io.CResource;
import org.catools.media.configs.CMediaConfigs;
import org.catools.media.exception.CUnSupportedTypeException;
import org.catools.media.utils.CBoofCVUtil;
import org.catools.media.utils.CImageComparisonUtil;
import org.catools.media.utils.CImageUtil;
import org.catools.media.utils.CTemplateMatchingUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Predicate;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;
import static org.catools.media.utils.CImageComparisonUtil.toBufferedImageList;
import static org.catools.media.utils.CImageUtil.toBufferedImage;

/**
 * CImageComparisonState is an interface to define state of BufferedImage (from one of supported sources) in relation to one or
 * more File, Resource or BufferedImage.
 */
public interface CImageComparisonState<O> extends CObjectState<O> {


  default boolean test(Predicate<O> predicate) {
    return predicate.test(_get());
  }

  /**
   * Check if actual is null
   *
   * @return execution result
   */
  @JsonIgnore
  default boolean isNull() {
    return _get() == null;
  }

  /**
   * Check if actual is NOT null
   *
   * @return execution result
   */
  @JsonIgnore
  default boolean isNotNull() {
    return _get() != null;
  }

  /**
   * Check if actual and expected are equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isEqual(final BufferedImage expected) {
    return CImageComparisonUtil.getDiffs(toBufferedImage(_get()), expected, GRAY_FLOAT_32).isEmpty();
  }


  /**
   * Check if actual and expected are equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isEqual(final File expected) {
    return isEqual(toBufferedImage(expected));
  }


  /**
   * Check if actual and expected are equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isEqual(final CResource expected) {
    return isEqual(toBufferedImage(expected));
  }

  /**
   * Check if actual and expected are not equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isNotEqual(final BufferedImage expected) {
    return CImageComparisonUtil.getDiffs(toBufferedImage(_get()), expected, GRAY_FLOAT_32).isNotEmpty();
  }

  /**
   * Check if actual and expected are not equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isNotEqual(final File expected) {
    return isNotEqual(toBufferedImage(expected));
  }

  /**
   * Check if actual and expected are not equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isNotEqual(final CResource expected) {
    return isNotEqual(toBufferedImage(expected));
  }

  /**
   * Check if actual value equals to any expected values
   *
   * @param expected values to compare, return false if no expected value provided.
   * @return execution result
   */
  default boolean equalsAny(final BufferedImage... expected) {
    if (expected == null || expected.length == 0) {
      return false;
    }

    return equalsAny(new CList<>(expected));
  }

  /**
   * Check if actual value equals to any expected values
   *
   * @param expected values to compare, return false if no expected value provided.
   * @return execution result
   */
  default boolean equalsAny(final File... expected) {
    if (expected == null || expected.length == 0) {
      return false;
    }

    return equalsAny(new CList<>(expected));
  }

  /**
   * Check if actual value equals to any expected values
   *
   * @param expected values to compare, return false if no expected value provided.
   * @return execution result
   */
  default boolean equalsAny(final CResource... expected) {
    if (expected == null || expected.length == 0) {
      return false;
    }

    return equalsAny(new CList<>(expected));
  }

  /**
   * Check if actual value equals to any expected values.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  default boolean equalsAny(final Iterable expected) {
    for (var exp : expected) {
      if (exp == null) {
        continue;
      }
      if (exp instanceof BufferedImage bufferedImage) {
        if (isEqual(bufferedImage)) {
          return true;
        }
      } else if (exp instanceof File file) {
        if (isEqual(file)) {
          return true;
        }
      } else if (exp instanceof CResource resource) {
        if (isEqual(resource)) {
          return true;
        }
      } else {
        throw new CUnSupportedTypeException(exp.getClass() + " not supported");
      }
    }
    return false;
  }

  /**
   * Check if actual value not equals to any of expected values, return false if no expected value
   * provided.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean equalsNone(final BufferedImage... expected) {
    if (expected == null || expected.length == 0) {
      return false;
    }

    return equalsNone(new CList<>(expected));
  }

  /**
   * Check if actual value not equals to any of expected values, return false if no expected value
   * provided.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean equalsNone(final File... expected) {
    if (expected == null || expected.length == 0) {
      return false;
    }

    return equalsNone(new CList<>(expected));
  }

  /**
   * Check if actual value not equals to any of expected values, return false if no expected value
   * provided.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean equalsNone(final CResource... expected) {
    if (expected == null || expected.length == 0) {
      return false;
    }

    return equalsNone(new CList<>(expected));
  }

  /**
   * Check
   * that actual value equals to none of expected values.
   *
   * @param expected values to compare, return true if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  default boolean equalsNone(final Iterable expected) {
    for (var exp : expected) {
      if (exp == null) {
        continue;
      }
      if (exp instanceof BufferedImage bufferedImage) {
        if (isEqual(bufferedImage)) {
          return false;
        }
      } else if (exp instanceof File file) {
        if (isEqual(file)) {
          return false;
        }
      } else if (exp instanceof CResource resource) {
        if (isEqual(resource)) {
          return false;
        }
      } else {
        throw new CUnSupportedTypeException(exp.getClass() + " not supported");
      }
    }
    return true;
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean contains(final BufferedImage expected) {
    return contains(expected, TemplateScoreType.NCC);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean contains(final File expected) {
    return contains(expected, TemplateScoreType.NCC);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean contains(final CResource expected) {
    return contains(expected, TemplateScoreType.NCC);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean contains(final BufferedImage expected, TemplateScoreType scoreType) {
    return contains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean contains(final File expected, TemplateScoreType scoreType) {
    return contains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean contains(final CResource expected, TemplateScoreType scoreType) {
    return contains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    return contains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final File expected, TemplateScoreType scoreType, float matchPrecision) {
    return contains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    return contains(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return CTemplateMatchingUtil.findMatches(
        CBoofCVUtil.toGrayF32(toBufferedImage(_get())),
        CBoofCVUtil.toGrayF32(expected),
        mask == null ? null : CBoofCVUtil.toGrayF32(mask),
        scoreType,
        1,
        matchPrecision).isNotEmpty();
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return contains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return contains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return contains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return contains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return contains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision) {
    return contains(toBufferedImage(expected), scoreType, mask, matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision) {
    return contains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean contains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision) {
    return contains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean notContains(final BufferedImage expected) {
    return notContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean notContains(final File expected) {
    return notContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean notContains(final CResource expected) {
    return notContains(expected, TemplateScoreType.NCC);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean notContains(final BufferedImage expected, TemplateScoreType scoreType) {
    return notContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean notContains(final File expected, TemplateScoreType scoreType) {
    return notContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected  value to compare
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean notContains(final CResource expected, TemplateScoreType scoreType) {
    return notContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean notContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision) {
    return notContains(expected, scoreType, null, matchPrecision);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean notContains(final File expected, TemplateScoreType scoreType, float matchPrecision) {
    return notContains(expected, scoreType, null, matchPrecision);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean notContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision) {
    return notContains(expected, scoreType, null, matchPrecision);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean notContains(final BufferedImage expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    return CTemplateMatchingUtil.findMatches(
        CBoofCVUtil.toGrayF32(toBufferedImage(_get())),
        CBoofCVUtil.toGrayF32(expected),
        mask == null ? null : CBoofCVUtil.toGrayF32(mask),
        scoreType,
        1,
        matchPrecision).isEmpty();
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean notContains(final File expected, TemplateScoreType scoreType, File mask, float matchPrecision) {
    return notContains(toBufferedImage(expected),
        scoreType,
        mask == null ? null : toBufferedImage(mask),
        matchPrecision);
  }

  /**
   * Check if actual not contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean notContains(final CResource expected, TemplateScoreType scoreType, CResource mask, float matchPrecision) {
    return notContains(toBufferedImage(expected),
        scoreType,
        mask == null ? null : toBufferedImage(mask),
        matchPrecision);
  }


  /**
   * Check that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  default boolean containsAny(final Iterable<?> expected) {
    return containsAny(expected, TemplateScoreType.NCC);
  }

  /**
   * Check that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return false if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean containsAny(final Iterable<?> expected, TemplateScoreType scoreType) {
    return containsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean containsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    return containsAny(expected, scoreType, (BufferedImage) null, matchPrecision);
  }

  /**
   * Check that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean containsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    for (var exp : expected) {
      if (exp == null) {
        continue;
      }
      if (exp instanceof BufferedImage bufferedImage) {
        if (contains(bufferedImage, scoreType, mask, matchPrecision)) {
          return true;
        }
      } else if (exp instanceof File file) {
        if (contains(file, scoreType, mask, matchPrecision)) {
          return true;
        }
      } else if (exp instanceof CResource resource) {
        if (contains(resource, scoreType, mask, matchPrecision)) {
          return true;
        }
      } else {
        throw new CUnSupportedTypeException(exp.getClass() + " not supported");
      }
    }
    return false;
  }

  /**
   * Check that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean containsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision) {
    return containsAny(expected, scoreType, toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean containsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision) {
    return containsAny(expected, scoreType, toBufferedImage(mask), matchPrecision);
  }

  /**
   * Check that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected values to compare, return true if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  default boolean containsNone(final Iterable<?> expected) {
    return containsNone(expected, TemplateScoreType.NCC);
  }

  /**
   * Check that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected  values to compare, return true if no value provided. all {@code null} value in
   *                  expected list ignores.
   * @param scoreType formula to score matches in a template
   * @return execution result
   */
  default boolean containsNone(final Iterable<?> expected, TemplateScoreType scoreType) {
    return containsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision());
  }

  /**
   * Check that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean containsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision) {
    return containsNone(expected, scoreType, null, matchPrecision);
  }

  /**
   * Check that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @return execution result
   */
  default boolean containsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision) {
    for (var exp : expected) {
      if (exp == null) {
        continue;
      }
      if (exp instanceof BufferedImage bufferedImage) {
        if (contains(bufferedImage, scoreType, mask, matchPrecision)) {
          return false;
        }
      } else if (exp instanceof File file) {
        if (contains(file, scoreType, mask, matchPrecision)) {
          return false;
        }
      } else if (exp instanceof CResource resource) {
        if (contains(resource, scoreType, mask, matchPrecision)) {
          return false;
        }
      } else {
        throw new CUnSupportedTypeException(exp.getClass() + " not supported");
      }
    }
    return true;
  }


  default void generateDiffForAllExpected(String diffFileNamePrefix, Iterable<?> expectedList) {
    int counter = 0;
    BufferedImage actual = toBufferedImage(_get());
    for (BufferedImage exp : toBufferedImageList(expectedList)) {
      CImageUtil.generateDiffFile(actual, exp, diffFileNamePrefix + "_" + ++counter, GRAY_FLOAT_32);
    }
  }
}
