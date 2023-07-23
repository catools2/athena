package org.catools.common.tests.waitVerify;

import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.extensions.verify.interfaces.waitVerify.CNumberWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class CNumberWaitVerifyTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenExclusive() {
    toIntWaitVerify(100).verifyBetweenExclusive(99, 101);
    toLongWaitVerify(100L).verifyBetweenExclusive(99L, 101L);
    toDoubleWaitVerify(100.2D).verifyBetweenExclusive(100.1D, 100.3D);
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.124), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchHigherBound() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchLowerBound() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_BigDecimal_LowerBoundIsGreaterThanHigherBound() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(BigDecimal.valueOf(100.126), BigDecimal.valueOf(100.124), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchHigherBound() {
    toIntWaitVerify(100).verifyBetweenExclusive(99, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchLowerBound() {
    toIntWaitVerify(100).verifyBetweenExclusive(100, 101);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_Int_LowerBoundIsGreaterThanHigherBound() {
    toIntWaitVerify(100).verifyBetweenExclusive(102, 101);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenInclusive() {
    toIntWaitVerify(100).verifyBetweenInclusive(99, 100);
    toIntWaitVerify(100).verifyBetweenInclusive(100, 101);
    toLongWaitVerify(100L).verifyBetweenInclusive(99L, 100L);
    toLongWaitVerify(100L).verifyBetweenInclusive(100L, 101L);
    toDoubleWaitVerify(100.2D).verifyBetweenInclusive(100.1D, 100.2D);
    toDoubleWaitVerify(100.2D).verifyBetweenInclusive(100.2D, 100.3D);
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), 0, 100);
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualBiggerThanHigherBound() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(BigDecimal.valueOf(100.121), BigDecimal.valueOf(100.122), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualLessThanLowerBound() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(BigDecimal.valueOf(100.124), BigDecimal.valueOf(100.125), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualBiggerThanHigherBound() {
    toLongWaitVerify(100L).verifyBetweenInclusive(98L, 99L);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualLessThanLowerBound() {
    toLongWaitVerify(100L).verifyBetweenInclusive(101L, 102L);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenInclusive_Long_LowerBoundIsGreaterThanHigherBound() {
    toLongWaitVerify(100L).verifyBetweenInclusive(102L, 101L);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toIntWaitVerify(actual.intValue()).verifyEquals(actual.intValue());
    toLongWaitVerify(actual.longValue()).verifyEquals(actual.longValue());
    toDoubleWaitVerify(actual.doubleValue()).verifyEquals(actual.doubleValue());
    toBigDecimalWaitVerify(actual).verifyEquals(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimalWithPrecision_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toBigDecimalWaitVerify(actual).verifyEqualsP(expected, BigDecimal.valueOf(0.000009));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimal_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    toBigDecimalWaitVerify(actual).verifyEquals(expected);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDecimalWithPrecision_NotEquals() {
    toDoubleWaitVerify(1000D).verifyEqualsP(1001D, 0.5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDouble_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    toDoubleWaitVerify(actual.doubleValue()).verifyEquals(expected.doubleValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsInt_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    toIntWaitVerify(actual.intValue()).verifyEquals(expected.intValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsLong_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    toLongWaitVerify(actual.longValue()).verifyEquals(expected.longValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toIntWaitVerify(1).verifyEqualsP(5, 6);
    toLongWaitVerify(2000L).verifyEqualsP(1000L, 1000L);
    toDoubleWaitVerify(1000D).verifyEqualsP(1001D, 1.0);
    toDoubleWaitVerify(1001D).verifyEqualsP(1000D, 1.0);
    toBigDecimalWaitVerify(actual).verifyEqualsP(actual, BigDecimal.valueOf(0.0));
    toBigDecimalWaitVerify(actual).verifyEqualsP(expected, BigDecimal.valueOf(0.00009));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualBiggerExpected() {
    toIntWaitVerify(10).verifyEquals(0, 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualNull() {
    toIntWaitVerify(null).verifyEquals(10, 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision_BothNull() {
    toIntWaitVerify(null).verifyEquals(null, 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ExpectedNull() {
    toIntWaitVerify(10).verifyEquals(null, 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    toIntWaitVerify(null).verifyEquals(10);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    toIntWaitVerify(null).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    toIntWaitVerify(10).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreater() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toIntWaitVerify(101).verifyGreater(actual.intValue());
    toLongWaitVerify(101L).verifyGreater(actual.longValue());
    toDoubleWaitVerify(100.2D).verifyGreater(actual.doubleValue());
    toBigDecimalWaitVerify(expected).verifyGreater(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreaterOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toIntWaitVerify(101).verifyGreaterOrEqual(actual.intValue());
    toIntWaitVerify(actual.intValue()).verifyGreaterOrEqual(actual.intValue());
    toLongWaitVerify(101L).verifyGreaterOrEqual(actual.longValue());
    toLongWaitVerify(actual.longValue()).verifyGreaterOrEqual(actual.longValue());
    toDoubleWaitVerify(100.2D).verifyGreaterOrEqual(actual.doubleValue());
    toDoubleWaitVerify(actual.doubleValue()).verifyGreaterOrEqual(actual.doubleValue());
    toBigDecimalWaitVerify(expected).verifyGreaterOrEqual(actual);
    toBigDecimalWaitVerify(actual).verifyGreaterOrEqual(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    toIntWaitVerify(101).verifyGreaterOrEqual(actual.intValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    toLongWaitVerify(101L).verifyGreaterOrEqual(actual.longValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    toDoubleWaitVerify(100.2D).verifyGreaterOrEqual(actual.doubleValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    toBigDecimalWaitVerify(expected).verifyGreaterOrEqual(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    toIntWaitVerify(101).verifyGreater(actual.intValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    toLongWaitVerify(101L).verifyGreater(actual.longValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    toDoubleWaitVerify(100.2D).verifyGreater(actual.doubleValue());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    toBigDecimalWaitVerify(expected).verifyGreater(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toIntWaitVerify(actual.intValue()).verifyIsNotNull(1);
    toLongWaitVerify(actual.longValue()).verifyIsNotNull(1);
    toDoubleWaitVerify(actual.doubleValue()).verifyIsNotNull(1);
    toBigDecimalWaitVerify(actual).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N1() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N2() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N3() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N4() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N1() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N2() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N3() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N4() {
    toIntWaitVerify(null).verifyIsNotNull(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLess() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toIntWaitVerify(actual.intValue()).verifyLess(101);
    toLongWaitVerify(actual.longValue()).verifyLess(101L);
    toDoubleWaitVerify(actual.doubleValue()).verifyLess(100.2D);
    toBigDecimalWaitVerify(actual).verifyLess(expected);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLessOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toIntWaitVerify(actual.intValue()).verifyLessOrEqual(101);
    toIntWaitVerify(actual.intValue()).verifyLessOrEqual(actual.intValue());
    toLongWaitVerify(actual.longValue()).verifyLessOrEqual(101L);
    toLongWaitVerify(actual.longValue()).verifyLessOrEqual(actual.longValue());
    toDoubleWaitVerify(actual.doubleValue()).verifyLessOrEqual(100.2D);
    toDoubleWaitVerify(actual.doubleValue()).verifyLessOrEqual(actual.doubleValue());
    toBigDecimalWaitVerify(actual).verifyLessOrEqual(expected);
    toBigDecimalWaitVerify(actual).verifyLessOrEqual(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toIntWaitVerify(actual.intValue()).verifyLessOrEqual(91);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toLongWaitVerify(actual.longValue()).verifyLessOrEqual(91L);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toDoubleWaitVerify(actual.doubleValue()).verifyLessOrEqual(99.2D);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    toBigDecimalWaitVerify(actual).verifyLessOrEqual(expected);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toIntWaitVerify(actual.intValue()).verifyLess(90);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toLongWaitVerify(actual.longValue()).verifyLess(91L);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toDoubleWaitVerify(actual.doubleValue()).verifyLess(90.2D);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    toBigDecimalWaitVerify(actual).verifyLess(expected);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenExclusive() {
    toIntWaitVerify(100).verifyNotBetweenExclusive(99, 100);
    toIntWaitVerify(100).verifyNotBetweenExclusive(100, 101);
    toLongWaitVerify(100L).verifyNotBetweenExclusive(99L, 100L);
    toLongWaitVerify(100L).verifyNotBetweenExclusive(100L, 101L);
    toDoubleWaitVerify(100.2D).verifyNotBetweenExclusive(100.1D, 100.2D);
    toDoubleWaitVerify(100.2D).verifyNotBetweenExclusive(100.2D, 100.3D);
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), 0, 100);
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_BigDecimal_InBetween() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.124), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_BigDecimal_LowerBiggerThanHigher() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(BigDecimal.valueOf(100.124), BigDecimal.valueOf(100.122), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_Int_InBetween() {
    toIntWaitVerify(100).verifyNotBetweenExclusive(99, 101);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_Int_LowerBiggerThanHigher() {
    toIntWaitVerify(100).verifyNotBetweenExclusive(101, 99);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenInclusive() {
    toIntWaitVerify(101).verifyNotBetweenInclusive(99, 100);
    toLongWaitVerify(101L).verifyNotBetweenInclusive(99L, 100L);
    toDoubleWaitVerify(100.2D).verifyNotBetweenInclusive(100.3D, 100.4D);
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenInclusive(BigDecimal.valueOf(100.120), BigDecimal.valueOf(100.121), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_BigDecimal_ActualEqualLowerBound() {
    toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), 0, 100);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenInclusive_Decimal_LowerBiggerHigherBound() {
    toDoubleWaitVerify(100.2D).verifyNotBetweenInclusive(100.3D, 100.2D);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_Int_ActualEqualHigherBound() {
    toIntWaitVerify(101).verifyNotBetweenInclusive(100, 101);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toIntWaitVerify(actual.intValue()).verifyNotEquals(101);
    toIntWaitVerify(null).verifyNotEquals(101);
    toIntWaitVerify(actual.intValue()).verifyNotEquals(null);
    toLongWaitVerify(actual.longValue()).verifyNotEquals(90L);
    toDoubleWaitVerify(actual.doubleValue()).verifyNotEquals(100.2D);
    toBigDecimalWaitVerify(actual).verifyNotEquals(expected);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    toIntWaitVerify(1).verifyNotEqualsP(5, 2);
    toIntWaitVerify(5).verifyNotEqualsP(1, 2);
    toIntWaitVerify(null).verifyNotEqualsP(5, 2);
    toIntWaitVerify(1).verifyNotEqualsP(null, 2);
    toLongWaitVerify(2000L).verifyNotEqualsP(1000L, 10L);
    toDoubleWaitVerify(1000D).verifyNotEqualsP(1001D, 0.5);
    toBigDecimalWaitVerify(actual).verifyNotEqualsP(expected, BigDecimal.valueOf(0.000001));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_BigDecimal_InRange() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.1231);
    toBigDecimalWaitVerify(expected).verifyNotEqualsP(actual, BigDecimal.valueOf(0.001));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_ActualBiggerExpected() {
    toIntWaitVerify(5).verifyNotEqualsP(1, 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_BothNull() {
    toIntWaitVerify(null).verifyNotEqualsP(null, 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_InRange() {
    toIntWaitVerify(1).verifyNotEqualsP(5, 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BigDecimalEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toBigDecimalWaitVerify(actual).verifyNotEquals(actual);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    toIntWaitVerify(null).verifyNotEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_IntEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    toIntWaitVerify(actual.intValue()).verifyNotEquals(actual.intValue());
  }

  private CNumberWaitVerify<Integer> toIntWaitVerify(Integer val) {
    return () -> val;
  }

  private CNumberWaitVerify<BigDecimal> toBigDecimalWaitVerify(BigDecimal val) {
    return () -> val;
  }

  private CNumberWaitVerify<Double> toDoubleWaitVerify(Double val) {
    return () -> val;
  }

  private CNumberWaitVerify<Long> toLongWaitVerify(Long val) {
    return () -> val;
  }

  public void verify(Consumer<CBooleanVerification> action) {
    action.accept(CVerify.Bool);
  }
}
