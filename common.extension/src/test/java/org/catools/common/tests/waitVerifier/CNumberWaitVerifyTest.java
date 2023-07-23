package org.catools.common.tests.waitVerifier;

import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CNumberWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CNumberWaitVerifyTest extends CBaseWaitVerifyTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenExclusive() {
    verify(verifier -> toIntWaitVerify(100).verifyBetweenExclusive(verifier, 99, 101, 1));
    verify(verifier -> toLongWaitVerify(100L).verifyBetweenExclusive(verifier, 99L, 101L));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyBetweenExclusive(verifier, 100.1D, 100.3D));
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(verifier, BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.124)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchHigherBound() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(verifier, BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchLowerBound() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(verifier, BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_BigDecimal_LowerBoundIsGreaterThanHigherBound() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenExclusive(verifier, BigDecimal.valueOf(100.126), BigDecimal.valueOf(100.124), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchHigherBound() {
    verify(verifier -> toIntWaitVerify(100).verifyBetweenExclusive(verifier, 99, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchLowerBound() {
    verify(verifier -> toIntWaitVerify(100).verifyBetweenExclusive(verifier, 100, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_Int_LowerBoundIsGreaterThanHigherBound() {
    verify(verifier -> toIntWaitVerify(100).verifyBetweenExclusive(verifier, 102, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenInclusive() {
    verify(verifier -> toIntWaitVerify(100).verifyBetweenInclusive(verifier, 99, 100, 1));
    verify(verifier -> toIntWaitVerify(100).verifyBetweenInclusive(verifier, 100, 101));
    verify(verifier -> toLongWaitVerify(100L).verifyBetweenInclusive(verifier, 99L, 100L, 1));
    verify(verifier -> toLongWaitVerify(100L).verifyBetweenInclusive(verifier, 100L, 101L));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyBetweenInclusive(verifier, 100.1D, 100.2D, 1));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyBetweenInclusive(verifier, 100.2D, 100.3D));
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(verifier, BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124)));
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(verifier, BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualBiggerThanHigherBound() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(verifier, BigDecimal.valueOf(100.121), BigDecimal.valueOf(100.122), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualLessThanLowerBound() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyBetweenInclusive(verifier, BigDecimal.valueOf(100.124), BigDecimal.valueOf(100.125), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualBiggerThanHigherBound() {
    verify(verifier -> toLongWaitVerify(100L).verifyBetweenInclusive(verifier, 98L, 99L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualLessThanLowerBound() {
    verify(verifier -> toLongWaitVerify(100L).verifyBetweenInclusive(verifier, 101L, 102L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenInclusive_Long_LowerBoundIsGreaterThanHigherBound() {
    verify(verifier -> toLongWaitVerify(100L).verifyBetweenInclusive(verifier, 102L, 101L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyEquals(verifier, actual.intValue(), 1));
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyEquals(verifier, actual.longValue()));
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyEquals(verifier, actual.doubleValue()));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyEquals(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimalWithPrecision_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyEqualsP(verifier, expected, BigDecimal.valueOf(0.000009)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimal_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyEquals(verifier, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDecimalWithPrecision_NotEquals() {
    verify(verifier -> toDoubleWaitVerify(1000D).verifyEqualsP(verifier, 1001D, 0.5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDouble_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyEquals(verifier, expected.doubleValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsInt_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyEquals(verifier, expected.intValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsLong_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyEquals(verifier, expected.longValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toIntWaitVerify(1).verifyEqualsP(verifier, 5, 6, 1));
    verify(verifier -> toLongWaitVerify(2000L).verifyEqualsP(verifier, 1000L, 1000L));
    verify(verifier -> toDoubleWaitVerify(1000D).verifyEqualsP(verifier, 1001D, 1.0));
    verify(verifier -> toDoubleWaitVerify(1001D).verifyEqualsP(verifier, 1000D, 1.0));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyEqualsP(verifier, actual, BigDecimal.valueOf(0.0)));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyEqualsP(verifier, expected, BigDecimal.valueOf(0.00009)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualBiggerExpected() {
    verify(verifier -> toIntWaitVerify(10).verifyEquals(verifier, 0, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualNull() {
    verify(verifier -> toIntWaitVerify(null).verifyEquals(verifier, 10, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision_BothNull() {
    verify(verifier -> toIntWaitVerify(null).verifyEquals(verifier, null, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ExpectedNull() {
    verify(verifier -> toIntWaitVerify(10).verifyEquals(verifier, null, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(verifier -> toIntWaitVerify(null).verifyEquals(verifier, 10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(verifier -> toIntWaitVerify(null).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(verifier -> toIntWaitVerify(10).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreater() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toIntWaitVerify(101).verifyGreater(verifier, actual.intValue(), 1));
    verify(verifier -> toLongWaitVerify(101L).verifyGreater(verifier, actual.longValue()));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyGreater(verifier, actual.doubleValue()));
    verify(verifier -> toBigDecimalWaitVerify(expected).verifyGreater(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreaterOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toIntWaitVerify(101).verifyGreaterOrEqual(verifier, actual.intValue(), 1));
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyGreaterOrEqual(verifier, actual.intValue()));
    verify(verifier -> toLongWaitVerify(101L).verifyGreaterOrEqual(verifier, actual.longValue()));
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyGreaterOrEqual(verifier, actual.longValue()));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyGreaterOrEqual(verifier, actual.doubleValue()));
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyGreaterOrEqual(verifier, actual.doubleValue()));
    verify(verifier -> toBigDecimalWaitVerify(expected).verifyGreaterOrEqual(verifier, actual));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyGreaterOrEqual(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verify(verifier -> toIntWaitVerify(101).verifyGreaterOrEqual(verifier, actual.intValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verify(verifier -> toLongWaitVerify(101L).verifyGreaterOrEqual(verifier, actual.longValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyGreaterOrEqual(verifier, actual.doubleValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verify(verifier -> toBigDecimalWaitVerify(expected).verifyGreaterOrEqual(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verify(verifier -> toIntWaitVerify(101).verifyGreater(verifier, actual.intValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verify(verifier -> toLongWaitVerify(101L).verifyGreater(verifier, actual.longValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyGreater(verifier, actual.doubleValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verify(verifier -> toBigDecimalWaitVerify(expected).verifyGreater(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyIsNotNull(verifier, 1));
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyIsNotNull(verifier));
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyIsNotNull(verifier));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N1() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N2() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N3() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N4() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N1() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N2() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N3() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N4() {
    verify(verifier -> toIntWaitVerify(null).verifyIsNotNull(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLess() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyLess(verifier, 101, 1));
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyLess(verifier, 101L));
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyLess(verifier, 100.2D));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyLess(verifier, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLessOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyLessOrEqual(verifier, 101, 1));
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyLessOrEqual(verifier, actual.intValue()));
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyLessOrEqual(verifier, 101L));
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyLessOrEqual(verifier, actual.longValue()));
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyLessOrEqual(verifier, 100.2D));
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyLessOrEqual(verifier, actual.doubleValue()));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyLessOrEqual(verifier, expected));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyLessOrEqual(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyLessOrEqual(verifier, 91));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyLessOrEqual(verifier, 91L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyLessOrEqual(verifier, 99.2D));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyLessOrEqual(verifier, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyLess(verifier, 90));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyLess(verifier, 91L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyLess(verifier, 90.2D));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyLess(verifier, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenExclusive() {
    verify(verifier -> toIntWaitVerify(100).verifyNotBetweenExclusive(verifier, 99, 100, 1));
    verify(verifier -> toIntWaitVerify(100).verifyNotBetweenExclusive(verifier, 100, 101, 1));
    verify(verifier -> toLongWaitVerify(100L).verifyNotBetweenExclusive(verifier, 99L, 100L));
    verify(verifier -> toLongWaitVerify(100L).verifyNotBetweenExclusive(verifier, 100L, 101L));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyNotBetweenExclusive(verifier, 100.1D, 100.2D));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyNotBetweenExclusive(verifier, 100.2D, 100.3D));
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(verifier, BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), 0, 100));
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(verifier, BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_BigDecimal_InBetween() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(verifier, BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.124), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_BigDecimal_LowerBiggerThanHigher() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenExclusive(verifier, BigDecimal.valueOf(100.124), BigDecimal.valueOf(100.122), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_Int_InBetween() {
    verify(verifier -> toIntWaitVerify(100).verifyNotBetweenExclusive(verifier, 99, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_Int_LowerBiggerThanHigher() {
    verify(verifier -> toIntWaitVerify(100).verifyNotBetweenExclusive(verifier, 101, 99));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenInclusive() {
    verify(verifier -> toIntWaitVerify(101).verifyNotBetweenInclusive(verifier, 99, 100, 1));
    verify(verifier -> toLongWaitVerify(101L).verifyNotBetweenInclusive(verifier, 99L, 100L));
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyNotBetweenInclusive(verifier, 100.3D, 100.4D));
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenInclusive(verifier, BigDecimal.valueOf(100.120), BigDecimal.valueOf(100.121), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_BigDecimal_ActualEqualLowerBound() {
    verify(verifier -> toBigDecimalWaitVerify(BigDecimal.valueOf(100.123)).verifyNotBetweenInclusive(verifier, BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), 0, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenInclusive_Decimal_LowerBiggerHigherBound() {
    verify(verifier -> toDoubleWaitVerify(100.2D).verifyNotBetweenInclusive(verifier, 100.3D, 100.2D));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_Int_ActualEqualHigherBound() {
    verify(verifier -> toIntWaitVerify(101).verifyNotBetweenInclusive(verifier, 100, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyNotEquals(verifier, 101, 1));
    verify(verifier -> toIntWaitVerify(null).verifyNotEquals(verifier, 101));
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyNotEquals(verifier, null));
    verify(verifier -> toLongWaitVerify(actual.longValue()).verifyNotEquals(verifier, 90L));
    verify(verifier -> toDoubleWaitVerify(actual.doubleValue()).verifyNotEquals(verifier, 100.2D));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyNotEquals(verifier, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verify(verifier -> toIntWaitVerify(1).verifyNotEqualsP(verifier, 5, 2, 1));
    verify(verifier -> toIntWaitVerify(5).verifyNotEqualsP(verifier, 1, 2));
    verify(verifier -> toIntWaitVerify(null).verifyNotEqualsP(verifier, 5, 2));
    verify(verifier -> toIntWaitVerify(1).verifyNotEqualsP(verifier, null, 2));
    verify(verifier -> toLongWaitVerify(2000L).verifyNotEqualsP(verifier, 1000L, 10L));
    verify(verifier -> toDoubleWaitVerify(1000D).verifyNotEqualsP(verifier, 1001D, 0.5));
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyNotEqualsP(verifier, expected, BigDecimal.valueOf(0.000001)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_BigDecimal_InRange() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.1231);
    verify(verifier -> toBigDecimalWaitVerify(expected).verifyNotEqualsP(verifier, actual, BigDecimal.valueOf(0.001)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_ActualBiggerExpected() {
    verify(verifier -> toIntWaitVerify(5).verifyNotEqualsP(verifier, 1, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_BothNull() {
    verify(verifier -> toIntWaitVerify(null).verifyNotEqualsP(verifier, null, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_InRange() {
    verify(verifier -> toIntWaitVerify(1).verifyNotEqualsP(verifier, 5, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BigDecimalEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toBigDecimalWaitVerify(actual).verifyNotEquals(verifier, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(verifier -> toIntWaitVerify(null).verifyNotEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_IntEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verify(verifier -> toIntWaitVerify(actual.intValue()).verifyNotEquals(verifier, actual.intValue()));
  }

  private CNumberWaitVerifier<Integer> toIntWaitVerify(Integer val) {
    return () -> val;
  }

  private CNumberWaitVerifier<BigDecimal> toBigDecimalWaitVerify(BigDecimal val) {
    return () -> val;
  }

  private CNumberWaitVerifier<Double> toDoubleWaitVerify(Double val) {
    return () -> val;
  }

  private CNumberWaitVerifier<Long> toLongWaitVerify(Long val) {
    return () -> val;
  }
}
