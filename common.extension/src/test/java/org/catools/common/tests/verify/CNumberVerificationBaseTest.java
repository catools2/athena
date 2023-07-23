package org.catools.common.tests.verify;

import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.extensions.verify.hard.CNumberVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;

public abstract class CNumberVerificationBaseTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenExclusive() {
    verifyInt(number -> number.betweenExclusive(100, 99, 101));
    verifyInt(number -> number.betweenExclusive(100, 99, 101, "NumberExpectationTest ::> Int ::> betweenExclusive"));
    verifyLong(number -> number.betweenExclusive(100L, 99L, 101L));
    verifyLong(number -> number.betweenExclusive(100L, 99L, 101L, "NumberExpectationTest ::> Long ::> betweenExclusive"));
    verifyDouble(number -> number.betweenExclusive(100.2D, 100.1D, 100.3D));
    verifyDouble(number -> number.betweenExclusive(100.2D, 100.1D, 100.3D, "NumberExpectationTest ::> Double ::> betweenExclusive"));
    verifyBigDecimal(number -> number.betweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.124)));
    verifyBigDecimal(number -> number.betweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.124), "NumberExpectationTest ::> BigDecimal ::> betweenExclusive"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchHigherBound() {
    verifyBigDecimal(number -> number.betweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchLowerBound() {
    verifyBigDecimal(number -> number.betweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_BigDecimal_LowerBoundIsGreaterThanHigherBound() {
    verifyBigDecimal(number -> number.betweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.126), BigDecimal.valueOf(100.124)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchHigherBound() {
    verifyInt(number -> number.betweenExclusive(100, 99, 100));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchLowerBound() {
    verifyInt(number -> number.betweenExclusive(100, 100, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_Int_LowerBoundIsGreaterThanHigherBound() {
    verifyInt(number -> number.betweenExclusive(100, 102, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenInclusive() {
    verifyInt(number -> number.betweenInclusive(100, 99, 100));
    verifyInt(number -> number.betweenInclusive(100, 99, 100, "NumberExpectationTest ::> Int ::> betweenInclusive"));
    verifyInt(number -> number.betweenInclusive(100, 100, 101));
    verifyInt(number -> number.betweenInclusive(100, 100, 101, "NumberExpectationTest ::> Int ::> betweenInclusive"));
    verifyLong(number -> number.betweenInclusive(100L, 99L, 100L));
    verifyLong(number -> number.betweenInclusive(100L, 99L, 100L, "NumberExpectationTest ::> Long ::> betweenInclusive"));
    verifyLong(number -> number.betweenInclusive(100L, 100L, 101L));
    verifyLong(number -> number.betweenInclusive(100L, 100L, 101L, "NumberExpectationTest ::> Long ::> betweenInclusive"));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.1D, 100.2D));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.1D, 100.2D, "NumberExpectationTest ::> Double ::> betweenInclusive"));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.2D, 100.3D));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.2D, 100.3D, "NumberExpectationTest ::> Double ::> betweenInclusive"));
    verifyBigDecimal(number -> number.betweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124)));
    verifyBigDecimal(number -> number.betweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), "NumberExpectationTest ::> BigDecimal ::> betweenInclusive"));
    verifyBigDecimal(number -> number.betweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123)));
    verifyBigDecimal(number -> number.betweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), "NumberExpectationTest ::> BigDecimal ::> betweenInclusive"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualBiggerThanHigherBound() {
    verifyBigDecimal(number -> number.betweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.121), BigDecimal.valueOf(100.122)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualLessThanLowerBound() {
    verifyBigDecimal(number -> number.betweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), BigDecimal.valueOf(100.125)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualBiggerThanHigherBound() {
    verifyLong(number -> number.betweenInclusive(100L, 98L, 99L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualLessThanLowerBound() {
    verifyLong(number -> number.betweenInclusive(100L, 101L, 102L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenInclusive_Long_LowerBoundIsGreaterThanHigherBound() {
    verifyInt(number -> number.betweenInclusive(100, 102, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.equals(actual.intValue(), actual.intValue()));
    verifyInt(number -> number.equals(actual.intValue(), actual.intValue(), "NumberExpectationTest ::> Int ::> equals"));
    verifyLong(number -> number.equals(actual.longValue(), actual.longValue()));
    verifyLong(number -> number.equals(actual.longValue(), actual.longValue(), "NumberExpectationTest ::> Long ::> equals"));
    verifyDouble(number -> number.equals(actual.doubleValue(), actual.doubleValue()));
    verifyDouble(number -> number.equals(actual.doubleValue(), actual.doubleValue(), "NumberExpectationTest ::> Double ::> equals"));
    verifyBigDecimal(number -> number.equals(actual, actual));
    verifyBigDecimal(number -> number.equals(actual, actual, "NumberExpectationTest ::> BigDecimal ::> equals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimalWithPrecision_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyBigDecimal(number -> number.equalsP(actual, expected, BigDecimal.valueOf(0.000009)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimal_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyBigDecimal(number -> number.equals(actual, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDecimalWithPrecision_NotEquals() {
    verifyDouble(number -> number.equalsP(1000D, 1001D, 0.5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDouble_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyDouble(number -> number.equals(actual.doubleValue(), expected.doubleValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsInt_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyInt(number -> number.equals(actual.intValue(), expected.intValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsLong_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyLong(number -> number.equals(actual.longValue(), expected.longValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.equalsP(1, 5, 6));
    verifyInt(number -> number.equalsP(1, 5, 6, "NumberExpectationTest ::> Int ::> equals(with precision)"));
    verifyLong(number -> number.equalsP(2000L, 1000L, 1000L));
    verifyLong(number -> number.equalsP(2000L, 1000L, 1000L, "NumberExpectationTest ::> Long ::> equals(with precision)"));
    verifyDouble(number -> number.equalsP(1000D, 1001D, 1d));
    verifyDouble(number -> number.equalsP(1000D, 1001D, 1d, "NumberExpectationTest ::> Double ::> equals(with precision)"));
    verifyDouble(number -> number.equalsP(1001D, 1000D, 1d));
    verifyDouble(number -> number.equalsP(1001D, 1000D, 1d, "NumberExpectationTest ::> Double ::> equals(with precision)"));
    verifyBigDecimal(number -> number.equalsP(actual, actual, BigDecimal.valueOf(0.0)));
    verifyBigDecimal(number -> number.equalsP(actual, actual, BigDecimal.valueOf(0.0), "NumberExpectationTest ::> BigDecimal ::> equals(with precision)"));
    verifyBigDecimal(number -> number.equalsP(actual, expected, BigDecimal.valueOf(0.00009)));
    verifyBigDecimal(number -> number.equalsP(actual, expected, BigDecimal.valueOf(0.00009), "NumberExpectationTest ::> BigDecimal ::> equals(with precision)"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualBiggerExpected() {
    verifyInt(number -> number.equalsP(10, 0, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualNull() {
    verifyInt(number -> number.equalsP(null, 10, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision_BothNull() {
    verifyInt(number -> number.equalsP(null, null, 6));
    verifyInt(number -> number.equalsP(null, null, 6, "NumberExpectationTest ::> Int ::> equals(with precision)"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ExpectedNull() {
    verifyInt(number -> number.equalsP(10, null, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verifyInt(number -> number.equals(null, 10));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verifyInt(number -> number.equals(null, null));
    verifyInt(number -> number.equals(null, null, "NumberExpectationTest ::> Int ::> equals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verifyInt(number -> number.equals(10, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreater() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.greater(101, actual.intValue()));
    verifyInt(number -> number.greater(101, actual.intValue(), "NumberExpectationTest ::> Int ::> greater"));
    verifyLong(number -> number.greater(101L, actual.longValue()));
    verifyLong(number -> number.greater(101L, actual.longValue(), "NumberExpectationTest ::> Long ::> greater"));
    verifyDouble(number -> number.greater(100.2D, actual.doubleValue()));
    verifyDouble(number -> number.greater(100.2D, actual.doubleValue(), "NumberExpectationTest ::> Double ::> greater"));
    verifyBigDecimal(number -> number.greater(expected, actual));
    verifyBigDecimal(number -> number.greater(expected, actual, "NumberExpectationTest ::> BigDecimal ::> greater"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreaterOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.greaterOrEqual(101, actual.intValue()));
    verifyInt(number -> number.greaterOrEqual(101, actual.intValue(), "NumberExpectationTest ::> Int ::> greaterOrEqual ::> less"));
    verifyInt(number -> number.greaterOrEqual(actual.intValue(), actual.intValue()));
    verifyInt(number -> number.greaterOrEqual(actual.intValue(), actual.intValue(), "NumberExpectationTest ::> Int ::> greaterOrEqual ::> equal"));
    verifyLong(number -> number.greaterOrEqual(101L, actual.longValue()));
    verifyLong(number -> number.greaterOrEqual(101L, actual.longValue(), "NumberExpectationTest ::> Long ::> greaterOrEqual ::> less"));
    verifyLong(number -> number.greaterOrEqual(actual.longValue(), actual.longValue()));
    verifyLong(number -> number.greaterOrEqual(actual.longValue(), actual.longValue(), "NumberExpectationTest ::> Long ::> greaterOrEqual ::> equal"));
    verifyDouble(number -> number.greaterOrEqual(100.2D, actual.doubleValue()));
    verifyDouble(number -> number.greaterOrEqual(100.2D, actual.doubleValue(), "NumberExpectationTest ::> Double ::> greaterOrEqual ::> less"));
    verifyDouble(number -> number.greaterOrEqual(actual.doubleValue(), actual.doubleValue()));
    verifyDouble(number -> number.greaterOrEqual(actual.doubleValue(), actual.doubleValue(), "NumberExpectationTest ::> Double ::> greaterOrEqual ::> equal"));
    verifyBigDecimal(number -> number.greaterOrEqual(expected, actual));
    verifyBigDecimal(number -> number.greaterOrEqual(expected, actual, "NumberExpectationTest ::> BigDecimal ::> greaterOrEqual ::> less"));
    verifyBigDecimal(number -> number.greaterOrEqual(actual, actual));
    verifyBigDecimal(number -> number.greaterOrEqual(actual, actual, "NumberExpectationTest ::> BigDecimal ::> greaterOrEqual ::> equal"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyInt(number -> number.greaterOrEqual(101, actual.intValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyLong(number -> number.greaterOrEqual(101L, actual.longValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyDouble(number -> number.greaterOrEqual(100.2D, actual.doubleValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.greaterOrEqual(expected, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyInt(number -> number.greater(101, actual.intValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyLong(number -> number.greater(101L, actual.longValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyDouble(number -> number.greater(100.2D, actual.doubleValue()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.greater(expected, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLess() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.less(actual.intValue(), 101));
    verifyInt(number -> number.less(actual.intValue(), 101, "NumberExpectationTest ::> Int ::> less"));
    verifyLong(number -> number.less(actual.longValue(), 101L));
    verifyLong(number -> number.less(actual.longValue(), 101L, "NumberExpectationTest ::> Long ::> less"));
    verifyDouble(number -> number.less(actual.doubleValue(), 100.2D));
    verifyDouble(number -> number.less(actual.doubleValue(), 100.2D, "NumberExpectationTest ::> Double ::> less"));
    verifyBigDecimal(number -> number.less(actual, expected));
    verifyBigDecimal(number -> number.less(actual, expected, "NumberExpectationTest ::> BigDecimal ::> less"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLessOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.lessOrEqual(actual.intValue(), 101));
    verifyInt(number -> number.lessOrEqual(actual.intValue(), 101, "NumberExpectationTest ::> Int ::> lessOrEqual ::> less"));
    verifyInt(number -> number.lessOrEqual(actual.intValue(), actual.intValue()));
    verifyInt(number -> number.lessOrEqual(actual.intValue(), actual.intValue(), "NumberExpectationTest ::> Int ::> lessOrEqual ::> equal"));
    verifyLong(number -> number.lessOrEqual(actual.longValue(), 101L));
    verifyLong(number -> number.lessOrEqual(actual.longValue(), 101L, "NumberExpectationTest ::> Long ::> lessOrEqual ::> less"));
    verifyLong(number -> number.lessOrEqual(actual.longValue(), actual.longValue()));
    verifyLong(number -> number.lessOrEqual(actual.longValue(), actual.longValue(), "NumberExpectationTest ::> Long ::> lessOrEqual ::> equal"));
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), 100.2D));
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), 100.2D, "NumberExpectationTest ::> Double ::> lessOrEqual ::> less"));
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), actual.doubleValue()));
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), actual.doubleValue(), "NumberExpectationTest ::> Double ::> lessOrEqual ::> equal"));
    verifyBigDecimal(number -> number.lessOrEqual(actual, expected));
    verifyBigDecimal(number -> number.lessOrEqual(actual, expected, "NumberExpectationTest ::> BigDecimal ::> lessOrEqual ::> less"));
    verifyBigDecimal(number -> number.lessOrEqual(actual, actual));
    verifyBigDecimal(number -> number.lessOrEqual(actual, actual, "NumberExpectationTest ::> BigDecimal ::> lessOrEqual ::> equal"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.lessOrEqual(actual.intValue(), 91));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyLong(number -> number.lessOrEqual(actual.longValue(), 91L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), 99.2D));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.lessOrEqual(actual, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.less(actual.intValue(), 90));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyLong(number -> number.less(actual.longValue(), 91L));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyDouble(number -> number.less(actual.doubleValue(), 90.2D));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.less(actual, expected));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenExclusive() {
    verifyInt(number -> number.notBetweenExclusive(100, 99, 100));
    verifyInt(number -> number.notBetweenExclusive(100, 99, 100, "NumberExpectationTest ::> Int ::> notBetweenExclusive"));
    verifyInt(number -> number.notBetweenExclusive(100, 100, 101));
    verifyInt(number -> number.notBetweenExclusive(100, 100, 101, "NumberExpectationTest ::> Int ::> notBetweenExclusive"));
    verifyLong(number -> number.notBetweenExclusive(100L, 99L, 100L));
    verifyLong(number -> number.notBetweenExclusive(100L, 99L, 100L, "NumberExpectationTest ::> Long ::> notBetweenExclusive"));
    verifyLong(number -> number.notBetweenExclusive(100L, 100L, 101L));
    verifyLong(number -> number.notBetweenExclusive(100L, 100L, 101L, "NumberExpectationTest ::> Long ::> notBetweenExclusive"));
    verifyDouble(number -> number.notBetweenExclusive(100.2D, 100.1D, 100.2D));
    verifyDouble(number -> number.notBetweenExclusive(100.2D, 100.1D, 100.2D, "NumberExpectationTest ::> Double ::> notBetweenExclusive"));
    verifyDouble(number -> number.notBetweenExclusive(100.2D, 100.2D, 100.3D));
    verifyDouble(number -> number.notBetweenExclusive(100.2D, 100.2D, 100.3D, "NumberExpectationTest ::> Double ::> notBetweenExclusive"));
    verifyBigDecimal(number -> number.notBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124)));
    verifyBigDecimal(number -> number.notBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), "NumberExpectationTest ::> BigDecimal ::> notBetweenExclusive"));
    verifyBigDecimal(number -> number.notBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123)));
    verifyBigDecimal(number -> number.notBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.123), "NumberExpectationTest ::> BigDecimal ::> notBetweenExclusive"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_BigDecimal_InBetween() {
    verifyBigDecimal(number -> number.notBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.122), BigDecimal.valueOf(100.124)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_BigDecimal_LowerBiggerThanHigher() {
    verifyBigDecimal(number -> number.notBetweenExclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124), BigDecimal.valueOf(100.122)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_Int_InBetween() {
    verifyInt(number -> number.notBetweenExclusive(100, 99, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_Int_LowerBiggerThanHigher() {
    verifyInt(number -> number.notBetweenExclusive(100, 101, 99));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenInclusive() {
    verifyInt(number -> number.notBetweenInclusive(101, 99, 100));
    verifyInt(number -> number.notBetweenInclusive(101, 99, 100, "NumberExpectationTest ::> Int ::> notBetweenInclusive"));
    verifyLong(number -> number.notBetweenInclusive(101L, 99L, 100L));
    verifyLong(number -> number.notBetweenInclusive(101L, 99L, 100L, "NumberExpectationTest ::> Long ::> notBetweenInclusive"));
    verifyDouble(number -> number.notBetweenInclusive(100.2D, 100.3D, 100.4D));
    verifyDouble(number -> number.notBetweenInclusive(100.2D, 100.3D, 100.4D, "NumberExpectationTest ::> Double ::> notBetweenInclusive"));
    verifyBigDecimal(number -> number.notBetweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.120), BigDecimal.valueOf(100.121)));
    verifyBigDecimal(number -> number.notBetweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.120), BigDecimal.valueOf(100.121), "NumberExpectationTest ::> BigDecimal ::> notBetweenInclusive"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_BigDecimal_ActualEqualLowerBound() {
    verifyBigDecimal(number -> number.notBetweenInclusive(BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.123), BigDecimal.valueOf(100.124)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenInclusive_Decimal_LowerBiggerHigherBound() {
    verifyDouble(number -> number.notBetweenInclusive(100.2D, 100.3D, 100.2D));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_Int_ActualEqualHigherBound() {
    verifyInt(number -> number.notBetweenInclusive(101, 100, 101));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.notEquals(actual.intValue(), 101));
    verifyInt(number -> number.notEquals(actual.intValue(), 101, "NumberExpectationTest ::> Int ::> verifyNotEquals"));
    verifyInt(number -> number.notEquals(null, 101));
    verifyInt(number -> number.notEquals(null, 101, "NumberExpectationTest ::> Int ::> verifyNotEquals"));
    verifyInt(number -> number.notEquals(actual.intValue(), null));
    verifyInt(number -> number.notEquals(actual.intValue(), null, "NumberExpectationTest ::> Int ::> verifyNotEquals"));
    verifyLong(number -> number.notEquals(actual.longValue(), 90L));
    verifyLong(number -> number.notEquals(actual.longValue(), 90L, "NumberExpectationTest ::> Long ::> verifyNotEquals"));
    verifyDouble(number -> number.notEquals(actual.doubleValue(), 100.2D));
    verifyDouble(number -> number.notEquals(actual.doubleValue(), 100.2D, "NumberExpectationTest ::> Double ::> verifyNotEquals"));
    verifyBigDecimal(number -> number.notEquals(actual, expected));
    verifyBigDecimal(number -> number.notEquals(actual, expected, "NumberExpectationTest ::> BigDecimal ::> verifyNotEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.notEqualsP(1, 5, 2));
    verifyInt(number -> number.notEqualsP(1, 5, 2, "NumberExpectationTest ::> Int ::> verifyNotEquals(with precision)"));
    verifyInt(number -> number.notEqualsP(5, 1, 2));
    verifyInt(number -> number.notEqualsP(5, 1, 2, "NumberExpectationTest ::> Int ::> verifyNotEquals(with precision)"));
    verifyInt(number -> number.notEqualsP(null, 5, 2));
    verifyInt(number -> number.notEqualsP(null, 5, 2, "NumberExpectationTest ::> Int ::> verifyNotEquals(with precision)"));
    verifyInt(number -> number.notEqualsP(1, null, 2));
    verifyInt(number -> number.notEqualsP(1, null, 2, "NumberExpectationTest ::> Int ::> verifyNotEquals(with precision)"));
    verifyLong(number -> number.notEqualsP(2000L, 1000L, 10L));
    verifyLong(number -> number.notEqualsP(2000L, 1000L, 10L, "NumberExpectationTest ::> Long ::> verifyNotEquals(with precision)"));
    verifyDouble(number -> number.notEqualsP(1000D, 1001D, 0.5));
    verifyDouble(number -> number.notEqualsP(1000D, 1001D, 0.5, "NumberExpectationTest ::> Double ::> verifyNotEquals(with precision)"));
    verifyBigDecimal(number -> number.notEqualsP(actual, expected, BigDecimal.valueOf(0.000001)));
    verifyBigDecimal(number -> number.notEqualsP(actual, expected, BigDecimal.valueOf(0.000001), "NumberExpectationTest ::> BigDecimal ::> verifyNotEquals(with precision)"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_BigDecimal_InRange() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEqualsP(expected, actual, BigDecimal.valueOf(0.001)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_ActualBiggerExpected() {
    verifyInt(number -> number.notEqualsP(5, 1, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_BothNull() {
    verifyInt(number -> number.notEqualsP(null, null, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_InRange() {
    verifyInt(number -> number.notEqualsP(1, 5, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BigDecimalEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEquals(actual, actual));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEquals(null, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_IntEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.notEquals(actual.intValue(), actual.intValue()));
  }

  public abstract void verifyBigDecimal(Consumer<CNumberVerification<BigDecimal>> action);

  public abstract void verifyDouble(Consumer<CNumberVerification<Double>> action);

  public abstract void verifyInt(Consumer<CNumberVerification<Integer>> action);

  public abstract void verifyLong(Consumer<CNumberVerification<Long>> action);
}
