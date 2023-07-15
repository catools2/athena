package org.catools.common.faker;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.faker.enums.CFakerCountryCode3;
import org.catools.common.faker.etl.CFakerResourceManager;
import org.catools.common.faker.exception.CFakerCountryNotFoundException;
import org.catools.common.faker.model.CRandomAddress;
import org.catools.common.faker.model.CRandomCompany;
import org.catools.common.faker.model.CRandomCountry;
import org.catools.common.faker.model.CRandomName;
import org.catools.common.faker.provider.CFakerCountryProvider;
import org.catools.common.faker.util.CLoremIpsum;
import org.catools.common.utils.CIterableUtil;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a tool for facker generator functions which allows to generated facker values for issue
 * data.
 */
public class CRandom {
  private static final SecureRandom RANDOM = new SecureRandom();
  private static final Map<CFakerCountryCode3, CFakerCountryProvider> COUNTRIES = new HashMap<>();

  /**
   * Random generator functions to generate facker Integer values.
   */
  public static class Int {
    /**
     * Returns a facker integer within the specified range.
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the facker integer
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive}
     */
    public static int next(final int startInclusive, final int endExclusive) {
      if (startInclusive > endExclusive) {
        throw new CInvalidRangeException("Start value must be smaller or equal to end value.");
      }
      if (startInclusive == endExclusive) {
        return startInclusive;
      }
      return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    /**
     * Returns a facker int within 0 - Integer.MAX_VALUE
     *
     * @return the facker integer
     * @see #next(int, int)
     */
    public static int next() {
      return next(0, Integer.MAX_VALUE);
    }
  }

  /**
   * Random generator functions to generate facker Long values.
   */
  public static class Long {
    /**
     * Returns a facker long within the specified range.
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the facker long
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive}
     */
    public static long next(final long startInclusive, final long endExclusive) {
      if (startInclusive > endExclusive) {
        throw new CInvalidRangeException("Start value must be smaller or equal to end value.");
      }
      if (startInclusive == endExclusive) {
        return startInclusive;
      }

      return BigDecimal.next(
              java.math.BigDecimal.valueOf(startInclusive),
              java.math.BigDecimal.valueOf(endExclusive))
          .longValue();
    }

    /**
     * Returns a facker long within 0 - Long.MAX_VALUE
     *
     * @return the facker long
     * @see #next(long, long)
     */
    public static long next() {
      return next(0, java.lang.Long.MAX_VALUE);
    }
  }

  /**
   * Random generator functions to generate facker Double values.
   */
  public static class Double {
    /**
     * Returns a facker double within the specified range.
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the facker double
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive}
     */
    public static double next(final double startInclusive, final double endInclusive) {
      if (startInclusive > endInclusive) {
        throw new CInvalidRangeException("Start value must be smaller or equal to end value.");
      }
      if (startInclusive == endInclusive) {
        return startInclusive;
      }

      return startInclusive +
          java.math.BigDecimal.valueOf(endInclusive)
              .subtract(java.math.BigDecimal.valueOf(startInclusive))
              .multiply(java.math.BigDecimal.valueOf((RANDOM.nextInt(8) + 1) / 10.0d))
              .doubleValue();
    }

    /**
     * Returns a facker double within 0 - Double.MAX_VALUE
     *
     * @return the facker double
     * @see #next(double, double)
     */
    public static double next() {
      return next(0, java.lang.Double.MAX_VALUE);
    }
  }

  /**
   * Random generator functions to generate facker Float values.
   */
  public static class Float {
    /**
     * Returns a facker float within the specified range.
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the facker float
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive}
     */
    public static float next(final float startInclusive, final float endInclusive) {
      if (startInclusive > endInclusive) {
        throw new CInvalidRangeException("Start value must be smaller or equal to end value.");
      }
      if (startInclusive == endInclusive) {
        return startInclusive;
      }

      return startInclusive +
          java.math.BigDecimal.valueOf(endInclusive)
              .subtract(java.math.BigDecimal.valueOf(startInclusive))
              .multiply(java.math.BigDecimal.valueOf((RANDOM.nextInt(8) + 1) / 10.0f))
              .floatValue();
    }

    /**
     * Returns a facker float within 0 - Float.MAX_VALUE
     *
     * @return the facker float
     * @see #next()
     */
    public static float next() {
      return next(0, java.lang.Float.MAX_VALUE);
    }
  }

  /**
   * Random generator functions to generate facker BigDecimal values.
   */
  public static class BigDecimal {
    /**
     * Returns a facker BigDecimal within the specified range.
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the facker BigDecimal
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive}
     */
    public static java.math.BigDecimal next(
        final java.math.BigDecimal startInclusive, final java.math.BigDecimal endInclusive) {
      if (startInclusive.doubleValue() > endInclusive.doubleValue()) {
        throw new CInvalidRangeException("Start value must be smaller or equal to end value.");
      }

      if (startInclusive.equals(endInclusive)) {
        return startInclusive;
      }

      double rnadomDouble = RANDOM.nextDouble();
      return java.math.BigDecimal.valueOf(
          startInclusive.doubleValue()
              + ((endInclusive.subtract(startInclusive)).doubleValue() * rnadomDouble));
    }

    /**
     * Returns a facker BigDecimal within 0 - Double.MAX_VALUE
     *
     * @return the facker BigDecimal
     * @see #next()
     */
    public static java.math.BigDecimal next() {
      return next(java.math.BigDecimal.ZERO, java.math.BigDecimal.valueOf(java.lang.Double.MAX_VALUE));
    }
  }

  /**
   * Random generator functions to generate CLoremIpsum style random strings.
   */
  public static class String {
    /**
     * Generate a random numeric value with defined length:
     *
     * @param length length of string, should be positive and greater than 0
     * @return random numeric String with defined length
     */
    public static java.lang.String randomNumeric(int length) {
      if (length < 1) {
        throw new CInvalidRangeException(
            "The length value should be greater than 0. length:" + length);
      }

      return RANDOM.nextInt(10) + RandomStringUtils.randomNumeric(length).substring(1);
    }

    /**
     * Generate a random numeric value with in defined range:
     *
     * @param minLengthInclusive lower bound of string, should be positive and greater than 0
     * @param maxLengthExclusive higher bound of string, should be greater or equal to
     *                           minLengthInclusive
     * @return random numeric String with in defined range
     */
    public static java.lang.String randomNumeric(int minLengthInclusive, int maxLengthExclusive) {
      if (minLengthInclusive < 1) {
        throw new CInvalidRangeException(
            "The minLengthInclusive value should be greater than 0. minLengthInclusive:"
                + minLengthInclusive);
      }

      if (maxLengthExclusive < minLengthInclusive) {
        throw new CInvalidRangeException(
            "The maxLengthExclusive should be equal or greater than minLengthInclusive. maxLengthExclusive:"
                + maxLengthExclusive
                + ", minLengthInclusive:"
                + minLengthInclusive);
      }

      return RANDOM.nextInt(10)
          + RandomStringUtils.randomNumeric(minLengthInclusive, maxLengthExclusive).substring(1);
    }

    /**
     * Generate sequence of random generated statements using {@see #nextParagraph(int, int, int,
     *int, int, int)}} separated by space(" ") using following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = 15 Char
     *   <li>maxStatementLength = 30 Char
     *   <li>minParagraphLength = 100 Char
     *   <li>maxParagraphLength = 500 Char
     * </ul>
     *
     * @return random String with length in defined range
     * @see #nextParagraph()
     * @see #nextParagraph(int, int)
     * @see #nextParagraph(int, int, int, int, int, int)
     */
    public static java.lang.String nextParagraph() {
      return nextParagraph(3, 10, 15, 30, 100, 500);
    }

    /**
     * Generate sequence of random generated statements using {@see #nextParagraph(int, int, int,
     *int, int, int)}} separated by space(" ") using following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = 15 Char
     *   <li>maxStatementLength = 30 Char
     *   <li>minStatementLength = {@code minParagraphLength} parameter for minimum length of
     *       paragraph to be generated inclusive
     *   <li>maxStatementLength = {@code maxParagraphLength} parameter for maximum length of
     *       paragraph to be generated inclusive
     * </ul>
     *
     * @param minParagraphLength minimum length of paragraph to be generated inclusive (cannot be
     *                           negative)
     * @param maxParagraphLength maximum length of paragraph to be generated inclusive
     * @return random String with length in defined range
     * @see #nextParagraph()
     * @see #nextParagraph(int, int)
     * @see #nextParagraph(int, int, int, int, int, int)
     */
    public static java.lang.String nextParagraph(int minParagraphLength, int maxParagraphLength) {
      return nextParagraph(3, 10, 15, 30, minParagraphLength, maxParagraphLength);
    }

    /**
     * Generate sequence of random generated statements with the word length in range between
     * minWordLength and maxWordLength, the statement length in range between minStatementLength and
     * maxStatementLength. The total paragraph length will be in range between minParagraphLength
     * and maxParagraphLength
     *
     * @param minWordLength      minimum length of string to be generated inclusive (cannot be negative)
     * @param maxWordLength      maximum length of string to be generated inclusive
     * @param minStatementLength minimum length of statement to be generated inclusive (cannot be
     *                           negative)
     * @param maxStatementLength maximum length of statement to be generated inclusive
     * @param minParagraphLength minimum length of paragraph to be generated inclusive (cannot be
     *                           negative)
     * @param maxParagraphLength maximum length of paragraph to be generated inclusive
     * @return random String with length in defined range
     * @see #nextParagraph()
     * @see #nextParagraph(int, int)
     * @see #nextParagraph(int, int, int, int, int, int)
     */
    public static java.lang.String nextParagraph(
        int minWordLength,
        int maxWordLength,
        int minStatementLength,
        int maxStatementLength,
        int minParagraphLength,
        int maxParagraphLength) {
      return CLoremIpsum.getParagraph(
          minWordLength,
          maxWordLength,
          minStatementLength,
          maxStatementLength,
          minParagraphLength,
          maxParagraphLength);
    }

    /**
     * For {@code count} times, repeat generating a sequence of random statements using {@link
     * #nextParagraph(int, int, int, int, int, int)}} separated by space(" ") using following
     * parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = 15 Char
     *   <li>maxStatementLength = 30 Char
     *   <li>minParagraphLength = 100 Char
     *   <li>maxParagraphLength = 500 Char
     * </ul>
     *
     * @param count number of words which should be generated
     * @return list of generated words
     * @see #nextParagraph()
     * @see #nextParagraph(int, int)
     * @see #nextParagraph(int, int, int, int, int, int)
     */
    public static java.lang.String nextParagraphs(int count) {
      return nextParagraphs(3, 10, 15, 30, 100, 500, count);
    }

    /**
     * For {@code count} times, repeat generating a sequence of random statements using {@link
     * #nextParagraph(int, int, int, int, int, int)}} separated by space(" ") using following
     * parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = 15 Char
     *   <li>maxStatementLength = 30 Char
     *   <li>minStatementLength = {@code minParagraphLength} parameter for minimum length of
     *       paragraph to be generated inclusive
     *   <li>maxStatementLength = {@code maxParagraphLength} parameter for maximum length of
     *       paragraph to be generated inclusive
     * </ul>
     *
     * @param minParagraphLength minimum length of paragraph to be generated inclusive (cannot be
     *                           negative)
     * @param maxParagraphLength maximum length of paragraph to be generated inclusive
     * @param count              number of words which should be generated
     * @return list of generated words
     * @see #nextParagraph()
     * @see #nextParagraph(int, int)
     * @see #nextParagraph(int, int, int, int, int, int)
     */
    public static java.lang.String nextParagraphs(
        int minParagraphLength, int maxParagraphLength, int count) {
      return nextParagraphs(3, 10, 15, 30, minParagraphLength, maxParagraphLength, count);
    }

    /**
     * For {@code count} times, repeat generating a sequence of random statements with the word
     * length in range between minWordLength and maxWordLength, the statement length in range
     * between minStatementLength and maxStatementLength. The total paragraph length will be in
     * range between minParagraphLength and maxParagraphLength
     *
     * @param minWordLength      minimum length of string to be generated inclusive (cannot be negative)
     * @param maxWordLength      maximum length of string to be generated inclusive
     * @param minStatementLength minimum length of statement to be generated inclusive (cannot be
     *                           negative)
     * @param maxStatementLength maximum length of statement to be generated inclusive
     * @param minParagraphLength minimum length of paragraph to be generated inclusive (cannot be
     *                           negative)
     * @param maxParagraphLength maximum length of paragraph to be generated inclusive
     * @param count              number of words which should be generated
     * @return list of generated words
     * @see #nextParagraph()
     * @see #nextParagraph(int, int)
     * @see #nextParagraph(int, int, int, int, int, int)
     */
    public static java.lang.String nextParagraphs(
        int minWordLength,
        int maxWordLength,
        int minStatementLength,
        int maxStatementLength,
        int minParagraphLength,
        int maxParagraphLength,
        int count) {
      return CLoremIpsum.getParagraphs(
          minWordLength,
          maxWordLength,
          minStatementLength,
          maxStatementLength,
          minParagraphLength,
          maxParagraphLength,
          count);
    }

    /**
     * Generate sequence of random generated string using {@see #nextStatement(int, int, int,
     *int)}} using following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = 15 Char
     *   <li>maxStatementLength = 30 Char
     * </ul>
     *
     * @return random String with length in default range
     * @see #nextStatement(int, int)
     * @see #nextStatement(int, int, int, int)
     */
    public static java.lang.String nextStatement() {
      return nextStatement(3, 10, 15, 30);
    }

    /**
     * Generate sequence of random generated string using {@see #nextStatement(int, int, int,
     *int)}} using following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = {@code minStatementLength} parameter for minimum length of
     *       statement to be generated inclusive
     *   <li>maxStatementLength = {@code maxStatementLength} parameter for maximum length of
     *       statement to be generated inclusive
     * </ul>
     *
     * @param minStatementLength minimum length of statement to be generated inclusive (cannot be
     *                           negative)
     * @param maxStatementLength maximum length of statement to be generated inclusive
     * @return random String with length in range between minStatementLength and maxStatementLength
     * @see #nextStatement()
     * @see #nextStatement(int, int, int, int)
     */
    public static java.lang.String nextStatement(int minStatementLength, int maxStatementLength) {
      return nextStatement(3, 10, minStatementLength, maxStatementLength);
    }

    /**
     * Generate sequence of random generated string using {@see #nextWord(int, int)} using
     * parameter minWordLength and maxWordLength separated by space (" "). The length of final
     * statement will be in range between minStatementLength and maxStatementLength.
     *
     * @param minWordLength      minimum length of string to be generated inclusive (cannot be negative)
     * @param maxWordLength      maximum length of string to be generated inclusive
     * @param minStatementLength minimum length of statement to be generated inclusive (cannot be
     *                           negative)
     * @param maxStatementLength maximum length of statement to be generated inclusive
     * @return random String with length in defined range
     * @see #nextStatement()
     * @see #nextStatement(int, int)
     */
    public static java.lang.String nextStatement(
        int minWordLength, int maxWordLength, int minStatementLength, int maxStatementLength) {
      return CLoremIpsum.getStatement(
          minWordLength, maxWordLength, minStatementLength, maxStatementLength);
    }

    /**
     * For {@code count} times, repeat generating a sequence of randomstring using {@link
     * #nextStatement(int, int, int, int)}} using following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = 15 Char
     *   <li>maxStatementLength = 30 Char
     * </ul>
     *
     * @param count number of words which should be generated
     * @return list of generated words
     * @see #nextStatement(int, int)
     * @see #nextStatement(int, int, int, int)
     */
    public static java.lang.String nextStatements(int count) {
      return nextStatements(3, 10, 15, 30, count);
    }

    /**
     * For {@code count} times, repeat generating a sequence of randomstring using {@link
     * #nextStatement(int, int, int, int)}} using following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     *   <li>minStatementLength = {@code minStatementLength} parameter for minimum length of
     *       statement to be generated inclusive
     *   <li>maxStatementLength = {@code maxStatementLength} parameter for maximum length of
     *       statement to be generated inclusive
     * </ul>
     *
     * @param minStatementLength minimum length of statement to be generated inclusive (cannot be
     *                           negative)
     * @param maxStatementLength maximum length of statement to be generated inclusive
     * @param count              number of words which should be generated
     * @return list of generated words
     * @see #nextStatement()
     * @see #nextStatement(int, int, int, int)
     */
    public static java.lang.String nextStatements(
        int minStatementLength, int maxStatementLength, int count) {
      return nextStatements(3, 10, minStatementLength, maxStatementLength, count);
    }

    /**
     * For {@code count} times, repeat generating a sequence of randomstring using {@link
     * #nextWord(int, int)} using parameter minWordLength and maxWordLength separated by space ("
     * "). The length of final statement will be in range between minStatementLength and
     * maxStatementLength.
     *
     * @param minWordLength      minimum length of string to be generated inclusive (cannot be negative)
     * @param maxWordLength      maximum length of string to be generated inclusive
     * @param minStatementLength minimum length of statement to be generated inclusive (cannot be
     *                           negative)
     * @param maxStatementLength maximum length of statement to be generated inclusive
     * @param count              number of words which should be generated
     * @return list of generated words
     * @see #nextStatement()
     * @see #nextStatement(int, int)
     */
    public static java.lang.String nextStatements(
        int minWordLength,
        int maxWordLength,
        int minStatementLength,
        int maxStatementLength,
        int count) {
      return CLoremIpsum.getStatements(
          minWordLength, maxWordLength, minStatementLength, maxStatementLength, count);
    }

    /**
     * Generate sequence of random generated string using {@see #nextWord(int, int)} using
     * following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     * </ul>
     *
     * @return random String with length in default range
     * @see #nextWord(int, int)
     */
    public static java.lang.String nextWord() {
      return nextWord(3, 10);
    }

    /**
     * Generate next random generated string with the length in range between minWordLength and
     * maxWordLength
     *
     * @param minWordLength minimum length of string to be generated inclusive (cannot be negative)
     * @param maxWordLength maximum length of string to be generated inclusive
     * @return random String with length in range between minWordLength and maxWordLength
     * @see #nextWord()
     */
    public static java.lang.String nextWord(int minWordLength, int maxWordLength) {
      return CLoremIpsum.getWord(minWordLength, maxWordLength);
    }

    /**
     * For {@code count} times, repeat generating a sequence of randomstring using {@link
     * #nextWord(int, int)} using following parameters:
     *
     * <ul>
     *   <li>minWordLength = 3 Char
     *   <li>maxWordLength = 10 Char
     * </ul>
     *
     * @param count number of words which should be generated
     * @return list of generated words
     * @see #nextWord(int, int)
     * @see #nextWords(int, int, int)
     */
    public static java.lang.String nextWords(int count) {
      return nextWords(3, 10, count);
    }

    /**
     * For {@code count} times, repeat generating a sequence of randomstring with the length in
     * range between minWordLength and maxWordLength
     *
     * @param minWordLength minimum length of string to be generated inclusive (cannot be negative)
     * @param maxWordLength maximum length of string to be generated inclusive
     * @param count         number of words which should be generated
     * @return list of generated words
     * @see #nextWord(int, int)
     * @see #nextWords(int)
     */
    public static java.lang.String nextWords(int minWordLength, int maxWordLength, int count) {
      return CLoremIpsum.getWords(minWordLength, maxWordLength, count);
    }
  }

  /**
   * Random generator functions to generate Random Phone Number.
   */
  public static class PhoneNumber {
    public static java.lang.String nextNumber(java.lang.String countryCode) {
      java.lang.String prefix;
      int numberLength = 7;
      switch (countryCode) {
        case "US" -> prefix = "603";
        case "CA" -> prefix = "204";
        case "GB" -> prefix = "345";
        default -> {
          java.lang.String numb =
              java.lang.String.valueOf(
                  PhoneNumberUtil.getInstance().getExampleNumber(countryCode).getNationalNumber());
          prefix = numb.substring(0, 4);
          numberLength = numb.length() - 4;
        }
      }
      return prefix + RandomStringUtils.random(numberLength, "2345678");
    }
  }

  /**
   * Random generator functions to generate Person Name.
   */
  public static class PersonName {
    public static CRandomName next() {
      return next(CIterableUtil.getRandom(CFakerCountryCode3.values()).name());
    }

    public static CRandomName next(java.lang.String countryCode3) {
      return nextCountryProvider(countryCode3).getNameProvider().getAny();
    }

    public static CRandomName nextMale() {
      return nextMale(CIterableUtil.getRandom(CFakerCountryCode3.values()).name());
    }

    public static CRandomName nextMale(java.lang.String countryCode3) {
      return nextCountryProvider(countryCode3).getNameProvider().getAnyMale();
    }

    public static CRandomName nextFemale() {
      return nextFemale(CIterableUtil.getRandom(CFakerCountryCode3.values()).name());
    }

    public static CRandomName nextFemale(java.lang.String countryCode3) {
      return nextCountryProvider(countryCode3).getNameProvider().getAnyFemale();
    }
  }

  /**
   * Random generator functions to generate address.
   */
  public static class Address {
    public static CRandomAddress next() {
      return next(CIterableUtil.getRandom(CFakerCountryCode3.values()).name());
    }

    public static CRandomAddress next(java.lang.String countryCode3) {
      return nextCountryProvider(countryCode3).getAddressProvider().getAny();
    }
  }

  /**
   * Random generator functions to generate company.
   */
  public static class Company {
    public static CRandomCompany next() {
      return next(CIterableUtil.getRandom(CFakerCountryCode3.values()).name());
    }

    public static CRandomCompany next(java.lang.String countryCode3) {
      return nextCountryProvider(countryCode3).getCompanyProvider().getAny();
    }
  }

  /**
   * Random generator functions to get random company.
   */
  public static class Country {
    public static CRandomCountry next() {
      return next(CIterableUtil.getRandom(CFakerCountryCode3.values()).name());
    }

    public static synchronized CRandomCountry next(java.lang.String countryCode3) {
      return nextCountryProvider(countryCode3).getCountry();
    }
  }

  private static synchronized CFakerCountryProvider nextCountryProvider(
      java.lang.String countryCode3) {
    boolean countryExists =
        Arrays.stream(CFakerCountryCode3.values()).anyMatch(e -> e.name().equalsIgnoreCase(countryCode3));
    if (!countryExists) {
      throw new CFakerCountryNotFoundException(countryCode3);
    }
    CFakerCountryCode3 countryCode = CFakerCountryCode3.valueOf(countryCode3.toUpperCase());
    if (COUNTRIES.isEmpty()) {
      COUNTRIES.put(countryCode, CFakerResourceManager.getCountry(countryCode3));
    }
    return COUNTRIES.get(countryCode);
  }
}
