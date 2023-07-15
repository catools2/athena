package org.catools.common.tests.verify.retry;

import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.extensions.verify.hard.CNumberVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;

public abstract class NumberVerificationBaseTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testBetweenExclusive() {
    verifyInt(number -> number.betweenExclusive(100, 99, 101, "%s#%s", getParams()));
    verifyLong(number -> number.betweenExclusive(100L, 99L, 101L, "%s#%s", getParams()));
    verifyDouble(number -> number.betweenExclusive(100.2D, 100.1D, 100.3D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenExclusive_BigDecimal_ActualMatchHigherBound() {
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.123),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenExclusive_BigDecimal_ActualMatchLowerBound() {
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void WithInterval_testBetweenExclusive_BigDecimal_LowerBoundIsGreaterThanHigherBound() {
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.126),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenExclusive_Int_ActualMatchHigherBound() {
    verifyInt(number -> number.betweenExclusive(100, 99, 100, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenExclusive_Int_ActualMatchLowerBound() {
    verifyInt(number -> number.betweenExclusive(100, 100, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void WithInterval_testBetweenExclusive_Int_LowerBoundIsGreaterThanHigherBound() {
    verifyInt(number -> number.betweenExclusive(100, 102, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testBetweenInclusive() {
    verifyInt(number -> number.betweenInclusive(100, 99, 100, "%s#%s", getParams()));
    verifyInt(number -> number.betweenInclusive(100, 100, 101, "%s#%s", getParams()));
    verifyLong(number -> number.betweenInclusive(100L, 99L, 100L, "%s#%s", getParams()));
    verifyLong(number -> number.betweenInclusive(100L, 100L, 101L, "%s#%s", getParams()));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.1D, 100.2D, "%s#%s", getParams()));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.2D, 100.3D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.123),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenInclusive_BigDecimal_ActualBiggerThanHigherBound() {
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.121),
                BigDecimal.valueOf(100.122),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenInclusive_BigDecimal_ActualLessThanLowerBound() {
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                BigDecimal.valueOf(100.125),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenInclusive_Long_ActualBiggerThanHigherBound() {
    verifyLong(number -> number.betweenInclusive(100L, 98L, 99L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testBetweenInclusive_Long_ActualLessThanLowerBound() {
    verifyLong(number -> number.betweenInclusive(100L, 101L, 102L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void WithInterval_testBetweenInclusive_Long_LowerBoundIsGreaterThanHigherBound() {
    verifyInt(number -> number.betweenInclusive(100, 102, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.equals(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
    verifyLong(
        number -> number.equals(actual.longValue(), actual.longValue(), "%s#%s", getParams()));
    verifyDouble(
        number -> number.equals(actual.doubleValue(), actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.equals(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsBigDecimalWithPrecision_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyBigDecimal(number -> number.equalsP(actual, expected, BigDecimal.valueOf(0.000009), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsBigDecimal_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyBigDecimal(number -> number.equals(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsDecimalWithPrecision_NotEquals() {
    verifyDouble(number -> number.equalsP(1000D, 1001D, 0.5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsDouble_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyDouble(
        number ->
            number.equals(actual.doubleValue(), expected.doubleValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsInt_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyInt(
        number -> number.equals(actual.intValue(), expected.intValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsLong_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyLong(
        number -> number.equals(actual.longValue(), expected.longValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.equalsP(1, 5, 6, "%s#%s", getParams()));
    verifyLong(number -> number.equalsP(2000L, 1000L, 1000L, "%s#%s", getParams()));
    verifyDouble(number -> number.equalsP(1000D, 1001D, 1D, "%s#%s", getParams()));
    verifyDouble(number -> number.equalsP(1001D, 1000D, 1D, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.equalsP(actual, actual, BigDecimal.valueOf(0.0), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.equalsP(actual, expected, BigDecimal.valueOf(0.00009), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsWithPrecision_ActualBiggerExpected() {
    verifyInt(number -> number.equalsP(10, 0, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsWithPrecision_ActualNull() {
    verifyInt(number -> number.equalsP(null, 10, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsWithPrecision_BothNull() {
    verifyInt(number -> number.equalsP(null, null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsWithPrecision_ExpectedNull() {
    verifyInt(number -> number.equalsP(10, null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_ActualNull() {
    verifyInt(number -> number.equals(null, 10, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals_BothNull() {
    verifyInt(number -> number.equals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_ExpectedNull() {
    verifyInt(number -> number.equals(10, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testGreater() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.greater(101, actual.intValue(), "%s#%s", getParams()));
    verifyLong(number -> number.greater(101L, actual.longValue(), "%s#%s", getParams()));
    verifyDouble(number -> number.greater(100.2D, actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.greater(expected, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testGreaterOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.greaterOrEqual(101, actual.intValue(), "%s#%s", getParams()));
    verifyInt(
        number ->
            number.greaterOrEqual(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
    verifyLong(number -> number.greaterOrEqual(101L, actual.longValue(), "%s#%s", getParams()));
    verifyLong(
        number ->
            number.greaterOrEqual(actual.longValue(), actual.longValue(), "%s#%s", getParams()));
    verifyDouble(
        number -> number.greaterOrEqual(100.2D, actual.doubleValue(), "%s#%s", getParams()));
    verifyDouble(
        number ->
            number.greaterOrEqual(
                actual.doubleValue(), actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.greaterOrEqual(expected, actual, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.greaterOrEqual(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreaterOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyInt(number -> number.greaterOrEqual(101, actual.intValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreaterOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyLong(number -> number.greaterOrEqual(101L, actual.longValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreaterOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyDouble(
        number -> number.greaterOrEqual(100.2D, actual.doubleValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreaterOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.greaterOrEqual(expected, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreater_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyInt(number -> number.greater(101, actual.intValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreater_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyLong(number -> number.greater(101L, actual.longValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreater_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyDouble(number -> number.greater(100.2D, actual.doubleValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testGreater_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.greater(expected, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLess() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.less(actual.intValue(), 101, "%s#%s", getParams()));
    verifyLong(number -> number.less(actual.longValue(), 101L, "%s#%s", getParams()));
    verifyDouble(number -> number.less(actual.doubleValue(), 100.2D, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.less(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLessOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.lessOrEqual(actual.intValue(), 101, "%s#%s", getParams()));
    verifyInt(
        number -> number.lessOrEqual(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
    verifyLong(number -> number.lessOrEqual(actual.longValue(), 101L, "%s#%s", getParams()));
    verifyLong(
        number -> number.lessOrEqual(actual.longValue(), actual.longValue(), "%s#%s", getParams()));
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), 100.2D, "%s#%s", getParams()));
    verifyDouble(
        number ->
            number.lessOrEqual(actual.doubleValue(), actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.lessOrEqual(actual, expected, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.lessOrEqual(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLessOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.lessOrEqual(actual.intValue(), 91, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLessOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyLong(number -> number.lessOrEqual(actual.longValue(), 91L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLessOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), 99.2D, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLessOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.lessOrEqual(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLess_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.less(actual.intValue(), 90, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLess_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyLong(number -> number.less(actual.longValue(), 91L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLess_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyDouble(number -> number.less(actual.doubleValue(), 90.2D, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLess_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.less(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotBetweenExclusive() {
    verifyInt(number -> number.notBetweenExclusive(100, 99, 100, "%s#%s", getParams()));
    verifyInt(number -> number.notBetweenExclusive(100, 100, 101, "%s#%s", getParams()));
    verifyLong(number -> number.notBetweenExclusive(100L, 99L, 100L, "%s#%s", getParams()));
    verifyLong(number -> number.notBetweenExclusive(100L, 100L, 101L, "%s#%s", getParams()));
    verifyDouble(
        number -> number.notBetweenExclusive(100.2D, 100.1D, 100.2D, "%s#%s", getParams()));
    verifyDouble(
        number -> number.notBetweenExclusive(100.2D, 100.2D, 100.3D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.123),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotBetweenExclusive_BigDecimal_InBetween() {
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void WithInterval_testNotBetweenExclusive_BigDecimal_LowerBiggerThanHigher() {
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                BigDecimal.valueOf(100.122),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotBetweenExclusive_Int_InBetween() {
    verifyInt(number -> number.notBetweenExclusive(100, 99, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void WithInterval_testNotBetweenExclusive_Int_LowerBiggerThanHigher() {
    verifyInt(number -> number.notBetweenExclusive(100, 101, 99, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotBetweenInclusive() {
    verifyInt(number -> number.notBetweenInclusive(101, 99, 100, "%s#%s", getParams()));
    verifyLong(number -> number.notBetweenInclusive(101L, 99L, 100L, "%s#%s", getParams()));
    verifyDouble(
        number -> number.notBetweenInclusive(100.2D, 100.3D, 100.4D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.notBetweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.120),
                BigDecimal.valueOf(100.121),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotBetweenInclusive_BigDecimal_ActualEqualLowerBound() {
    verifyBigDecimal(
        number ->
            number.notBetweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void WithInterval_testNotBetweenInclusive_Decimal_LowerBiggerHigherBound() {
    verifyDouble(
        number -> number.notBetweenInclusive(100.2D, 100.3D, 100.2D, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotBetweenInclusive_Int_ActualEqualHigherBound() {
    verifyInt(number -> number.notBetweenInclusive(101, 100, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.notEquals(actual.intValue(), 101, "%s#%s", getParams()));
    verifyInt(number -> number.notEquals(null, 101, "%s#%s", getParams()));
    verifyInt(number -> number.notEquals(actual.intValue(), null, "%s#%s", getParams()));
    verifyLong(number -> number.notEquals(actual.longValue(), 90L, "%s#%s", getParams()));
    verifyDouble(number -> number.notEquals(actual.doubleValue(), 100.2D, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.notEquals(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.notEqualsP(1, 5, 2, "%s#%s", getParams()));
    verifyInt(number -> number.notEqualsP(5, 1, 2, "%s#%s", getParams()));
    verifyInt(number -> number.notEqualsP(null, 5, 2, "%s#%s", getParams()));
    verifyInt(number -> number.notEqualsP(1, null, 2, "%s#%s", getParams()));
    verifyLong(number -> number.notEqualsP(2000L, 1000L, 10L, "%s#%s", getParams()));
    verifyDouble(number -> number.notEqualsP(1000D, 1001D, 0.5, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.notEqualsP(actual, expected, BigDecimal.valueOf(0.000001), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualsWithPrecision_BigDecimal_InRange() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEqualsP(expected, actual, BigDecimal.valueOf(0.001), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualsWithPrecision_Int_ActualBiggerExpected() {
    verifyInt(number -> number.notEqualsP(5, 1, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualsWithPrecision_Int_BothNull() {
    verifyInt(number -> number.notEqualsP(null, null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualsWithPrecision_Int_InRange() {
    verifyInt(number -> number.notEqualsP(1, 5, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_BigDecimalEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEquals(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_BothNull() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEquals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEquals_IntEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(
        number -> number.notEquals(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenExclusive() {
    verifyInt(number -> number.betweenExclusive(99, 1, 101, "%s#%s", getParams()));
    verifyLong(number -> number.betweenExclusive(100L, 99L, 101L, "%s#%s", getParams()));
    verifyDouble(number -> number.betweenExclusive(100.2D, 100.1D, 100.3D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchHigherBound() {
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.123),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchLowerBound() {
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_BigDecimal_LowerBoundIsGreaterThanHigherBound() {
    verifyBigDecimal(
        number ->
            number.betweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.126),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchHigherBound() {
    verifyInt(number -> number.betweenExclusive(99, 1, 99, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchLowerBound() {
    verifyInt(number -> number.betweenExclusive(100, 100, 102, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_Int_LowerBoundIsGreaterThanHigherBound() {
    verifyInt(number -> number.betweenExclusive(102, 101, 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenInclusive() {
    verifyInt(number -> number.betweenInclusive(99, 1, 100, "%s#%s", getParams()));
    verifyInt(number -> number.betweenInclusive(100, 100, 101, "%s#%s", getParams()));
    verifyLong(number -> number.betweenInclusive(100L, 99L, 100L, "%s#%s", getParams()));
    verifyLong(number -> number.betweenInclusive(100L, 100L, 101L, "%s#%s", getParams()));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.1D, 100.2D, "%s#%s", getParams()));
    verifyDouble(number -> number.betweenInclusive(100.2D, 100.2D, 100.3D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.123),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualBiggerThanHigherBound() {
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.121),
                BigDecimal.valueOf(100.122),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualLessThanLowerBound() {
    verifyBigDecimal(
        number ->
            number.betweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                BigDecimal.valueOf(100.125),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualBiggerThanHigherBound() {
    verifyLong(number -> number.betweenInclusive(100L, 98L, 99L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualLessThanLowerBound() {
    verifyLong(number -> number.betweenInclusive(100L, 101L, 102L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenInclusive_Long_LowerBoundIsGreaterThanHigherBound() {
    verifyInt(number -> number.betweenInclusive(100, 102, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.equals(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
    verifyLong(
        number -> number.equals(actual.longValue(), actual.longValue(), "%s#%s", getParams()));
    verifyDouble(
        number -> number.equals(actual.doubleValue(), actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.equals(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimalWithPrecision_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyBigDecimal(number -> number.equalsP(actual, expected, BigDecimal.valueOf(0.000009), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimal_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyBigDecimal(number -> number.equals(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDecimalWithPrecision_NotEquals() {
    verifyDouble(number -> number.equalsP(1000D, 1001D, 0.5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDouble_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyDouble(
        number ->
            number.equals(actual.doubleValue(), expected.doubleValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsInt_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyInt(
        number -> number.equals(actual.intValue(), expected.intValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsLong_NotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(101.123134);
    verifyLong(
        number -> number.equals(actual.longValue(), expected.longValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.equalsP(1, 5, 6, "%s#%s", getParams()));
    verifyLong(number -> number.equalsP(2000L, 1000L, 1000L, "%s#%s", getParams()));
    verifyDouble(number -> number.equalsP(1000D, 1001D, 1d, "%s#%s", getParams()));
    verifyDouble(number -> number.equalsP(1001D, 1000D, 1d, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.equalsP(actual, actual, BigDecimal.valueOf(0.0), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.equalsP(actual, expected, BigDecimal.valueOf(0.00009), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualBiggerExpected() {
    verifyInt(number -> number.equalsP(10, 0, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualNull() {
    verifyInt(number -> number.equalsP(null, 10, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision_BothNull() {
    verifyInt(number -> number.equalsP(null, null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ExpectedNull() {
    verifyInt(number -> number.equalsP(10, null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verifyInt(number -> number.equals(null, 10, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verifyInt(number -> number.equals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verifyInt(number -> number.equals(10, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreater() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.greater(101, actual.intValue(), "%s#%s", getParams()));
    verifyLong(number -> number.greater(101L, actual.longValue(), "%s#%s", getParams()));
    verifyDouble(number -> number.greater(100.2D, actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.greater(expected, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreaterOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.greaterOrEqual(101, actual.intValue(), "%s#%s", getParams()));
    verifyInt(
        number ->
            number.greaterOrEqual(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
    verifyLong(number -> number.greaterOrEqual(101L, actual.longValue(), "%s#%s", getParams()));
    verifyLong(
        number ->
            number.greaterOrEqual(actual.longValue(), actual.longValue(), "%s#%s", getParams()));
    verifyDouble(
        number -> number.greaterOrEqual(100.2D, actual.doubleValue(), "%s#%s", getParams()));
    verifyDouble(
        number ->
            number.greaterOrEqual(
                actual.doubleValue(), actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.greaterOrEqual(expected, actual, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.greaterOrEqual(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyInt(number -> number.greaterOrEqual(101, actual.intValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyLong(number -> number.greaterOrEqual(101L, actual.longValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyDouble(
        number -> number.greaterOrEqual(100.2D, actual.doubleValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.greaterOrEqual(expected, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N1() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyInt(number -> number.greater(101, actual.intValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N2() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyLong(number -> number.greater(101L, actual.longValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N3() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    verifyDouble(number -> number.greater(100.2D, actual.doubleValue(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N4() {
    BigDecimal actual = BigDecimal.valueOf(102.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.greater(expected, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLess() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.less(actual.intValue(), 101, "%s#%s", getParams()));
    verifyLong(number -> number.less(actual.longValue(), 101L, "%s#%s", getParams()));
    verifyDouble(number -> number.less(actual.doubleValue(), 100.2D, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.less(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLessOrEqual() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.lessOrEqual(actual.intValue(), 101, "%s#%s", getParams()));
    verifyInt(
        number -> number.lessOrEqual(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
    verifyLong(number -> number.lessOrEqual(actual.longValue(), 101L, "%s#%s", getParams()));
    verifyLong(
        number -> number.lessOrEqual(actual.longValue(), actual.longValue(), "%s#%s", getParams()));
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), 100.2D, "%s#%s", getParams()));
    verifyDouble(
        number ->
            number.lessOrEqual(actual.doubleValue(), actual.doubleValue(), "%s#%s", getParams()));
    verifyBigDecimal(number -> number.lessOrEqual(actual, expected, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.lessOrEqual(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.lessOrEqual(actual.intValue(), 91, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyLong(number -> number.lessOrEqual(actual.longValue(), 91L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyDouble(number -> number.lessOrEqual(actual.doubleValue(), 99.2D, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.lessOrEqual(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N1() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(number -> number.less(actual.intValue(), 90, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N2() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyLong(number -> number.less(actual.longValue(), 91L, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N3() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyDouble(number -> number.less(actual.doubleValue(), 90.2D, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N4() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(99.123134);
    verifyBigDecimal(number -> number.less(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenExclusive() {
    verifyInt(number -> number.notBetweenExclusive(99, 1, 99, "%s#%s", getParams()));
    verifyInt(number -> number.notBetweenExclusive(101, 0, 100, "%s#%s", getParams()));
    verifyLong(number -> number.notBetweenExclusive(100L, 99L, 100L, "%s#%s", getParams()));
    verifyLong(number -> number.notBetweenExclusive(100L, 100L, 101L, "%s#%s", getParams()));
    verifyDouble(
        number -> number.notBetweenExclusive(100.2D, 100.1D, 100.2D, "%s#%s", getParams()));
    verifyDouble(
        number -> number.notBetweenExclusive(100.2D, 100.2D, 100.3D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.123),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_BigDecimal_InBetween() {
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.122),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_BigDecimal_LowerBiggerThanHigher() {
    verifyBigDecimal(
        number ->
            number.notBetweenExclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                BigDecimal.valueOf(100.122),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_Int_InBetween() {
    verifyInt(number -> number.notBetweenExclusive(99, 98, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_Int_LowerBiggerThanHigher() {
    verifyInt(number -> number.notBetweenExclusive(101, 99, 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenInclusive() {
    verifyInt(number -> number.notBetweenInclusive(101, 99, 100, "%s#%s", getParams()));
    verifyLong(number -> number.notBetweenInclusive(101L, 99L, 100L, "%s#%s", getParams()));
    verifyDouble(
        number -> number.notBetweenInclusive(100.2D, 100.3D, 100.4D, "%s#%s", getParams()));
    verifyBigDecimal(
        number ->
            number.notBetweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.120),
                BigDecimal.valueOf(100.121),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_BigDecimal_ActualEqualLowerBound() {
    verifyBigDecimal(
        number ->
            number.notBetweenInclusive(
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.123),
                BigDecimal.valueOf(100.124),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenInclusive_Decimal_LowerBiggerHigherBound() {
    verifyDouble(
        number -> number.notBetweenInclusive(100.2D, 100.3D, 100.2D, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_Int_ActualEqualHigherBound() {
    verifyInt(number -> number.notBetweenInclusive(101, 100, 101, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.notEquals(actual.intValue(), 101, "%s#%s", getParams()));
    verifyInt(number -> number.notEquals(null, 101, "%s#%s", getParams()));
    verifyInt(number -> number.notEquals(actual.intValue(), null, "%s#%s", getParams()));
    verifyLong(number -> number.notEquals(actual.longValue(), 90L, "%s#%s", getParams()));
    verifyDouble(number -> number.notEquals(actual.doubleValue(), 100.2D, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.notEquals(actual, expected, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsWithPrecision() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.123134);
    verifyInt(number -> number.notEqualsP(1, 5, 2, "%s#%s", getParams()));
    verifyInt(number -> number.notEqualsP(5, 1, 2, "%s#%s", getParams()));
    verifyInt(number -> number.notEqualsP(null, 5, 2, "%s#%s", getParams()));
    verifyInt(number -> number.notEqualsP(1, null, 2, "%s#%s", getParams()));
    verifyLong(number -> number.notEqualsP(2000L, 1000L, 10L, "%s#%s", getParams()));
    verifyDouble(number -> number.notEqualsP(1000D, 1001D, 0.5, "%s#%s", getParams()));
    verifyBigDecimal(number -> number.notEqualsP(actual, expected, BigDecimal.valueOf(0.000001), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_BigDecimal_InRange() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    BigDecimal expected = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEqualsP(expected, actual, BigDecimal.valueOf(0.001), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_ActualBiggerExpected() {
    verifyInt(number -> number.notEqualsP(5, 1, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_BothNull() {
    verifyInt(number -> number.notEqualsP(null, null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_InRange() {
    verifyInt(number -> number.notEqualsP(1, 5, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BigDecimalEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEquals(actual, actual, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyBigDecimal(number -> number.notEquals(null, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_IntEquals() {
    BigDecimal actual = BigDecimal.valueOf(100.1231);
    verifyInt(
        number -> number.notEquals(actual.intValue(), actual.intValue(), "%s#%s", getParams()));
  }

  public abstract void verifyBigDecimal(Consumer<CNumberVerification<BigDecimal>> action);

  public abstract void verifyDouble(Consumer<CNumberVerification<Double>> action);

  public abstract void verifyInt(Consumer<CNumberVerification<Integer>> action);

  public abstract void verifyLong(Consumer<CNumberVerification<Long>> action);
}
