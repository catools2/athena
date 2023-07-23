package org.catools.common.tests.wait;

import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.extensions.wait.CNumberWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;

public abstract class CNumberBaseWaitTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenExclusive() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(100, 99, 101, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(100L, 99L, 101L, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(100.2D, 100.1D, 100.3D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.122),
                    new BigDecimal(100.124),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.122),
                    new BigDecimal(100.123),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchLowerBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.123),
                    new BigDecimal(100.124),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_BigDecimal_LowerBoundIsGreaterThanHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.126),
                    new BigDecimal(100.124),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(100, 99, 100, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchLowerBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(100, 100, 101, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_Int_LowerBoundIsGreaterThanHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenExclusive(100, 102, 101, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenInclusive() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100, 99, 100, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100, 100, 101, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100L, 99L, 100L, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100L, 100L, 101L, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100.2D, 100.1D, 100.2D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100.2D, 100.2D, 100.3D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.123),
                    new BigDecimal(100.124),
                    0,
                    100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.122),
                    new BigDecimal(100.123),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualBiggerThanHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.121),
                    new BigDecimal(100.122),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualLessThanLowerBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.124),
                    new BigDecimal(100.125),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualBiggerThanHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100L, 98L, 99L, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualLessThanLowerBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100L, 101L, 102L, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenInclusive_Long_LowerBoundIsGreaterThanHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitBetweenInclusive(100, 102, 101, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEquals(actual.intValue(), actual.intValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEquals(actual.longValue(), actual.longValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEquals(actual.doubleValue(), actual.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitEquals(actual, actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimalWithPrecision_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEqualsP(actual, expected, new BigDecimal(0.000009), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimal_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(101.123134);
    verify(
        number ->
            number.isTrue(CNumberWait.waitEquals(actual, expected, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDecimalWithPrecision_NotEquals() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEqualsP(1000D, 1001D, 0.5, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDouble_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(101.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEquals(actual.doubleValue(), expected.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsInt_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(101.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEquals(actual.intValue(), expected.intValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsLong_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(101.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEquals(actual.longValue(), expected.longValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(number -> number.isTrue(CNumberWait.waitEqualsP(1, 5, 6, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEqualsP(2000L, 1000L, 1000L, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitEqualsP(1000D, 1001D, 1D, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitEqualsP(1001D, 1000D, 1D, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEqualsP(actual, actual, new BigDecimal(0.0), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitEqualsP(actual, expected, new BigDecimal(0.00009), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualBiggerExpected() {
    verify(
        number -> number.isTrue(CNumberWait.waitEqualsP(10, 0, 6, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualNull() {
    verify(
        number ->
            number.isTrue(CNumberWait.waitEqualsP(null, 10, 6, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision_BothNull() {
    verify(
        number ->
            number.isTrue(CNumberWait.waitEqualsP(null, null, 6, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ExpectedNull() {
    verify(
        number ->
            number.isTrue(CNumberWait.waitEqualsP(10, null, 6, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(number -> number.isTrue(CNumberWait.waitEquals(null, 10, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(
        number -> number.isTrue(CNumberWait.waitEquals(null, null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(number -> number.isTrue(CNumberWait.waitEquals(10, null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreater() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreater(101, actual.intValue(), 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreater(101L, actual.longValue(), 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreater(100.2D, actual.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitGreater(expected, actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreaterOrEqual() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(101, actual.intValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(actual.intValue(), actual.intValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(101L, actual.longValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(actual.longValue(), actual.longValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(100.2D, actual.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(actual.doubleValue(), actual.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(expected, actual, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(actual, actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N1() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(101, actual.intValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N2() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(101L, actual.longValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N3() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(100.2D, actual.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N4() {
    BigDecimal actual = new BigDecimal(102.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreaterOrEqual(expected, actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N1() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreater(101, actual.intValue(), 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N2() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreater(101L, actual.longValue(), 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N3() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitGreater(100.2D, actual.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N4() {
    BigDecimal actual = new BigDecimal(102.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(
        number ->
            number.isTrue(CNumberWait.waitGreater(expected, actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitIsNotNull(actual.intValue(), 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitIsNotNull(actual.longValue(), 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitIsNotNull(actual.doubleValue(), 0, 100), "%s#%s", getParams()));
    verify(
        number -> number.isTrue(CNumberWait.waitIsNotNull(actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N1() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N2() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N3() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N4() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N1() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N2() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N3() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N4() {
    verify(number -> number.isTrue(CNumberWait.waitIsNotNull(null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLess() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLess(actual.intValue(), 101, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLess(actual.longValue(), 101L, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLess(actual.doubleValue(), 100.2D, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitLess(actual, expected, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLessOrEqual() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.intValue(), 101, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.intValue(), actual.intValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.longValue(), 101L, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.longValue(), actual.longValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.doubleValue(), 100.2D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.doubleValue(), actual.doubleValue(), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual, expected, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual, actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N1() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.intValue(), 91, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N2() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.longValue(), 91L, 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N3() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual.doubleValue(), 99.2D, 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N4() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLessOrEqual(actual, expected, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N1() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLess(actual.intValue(), 90, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N2() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLess(actual.longValue(), 91L, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N3() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitLess(actual.doubleValue(), 90.2D, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N4() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(
        number ->
            number.isTrue(CNumberWait.waitLess(actual, expected, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenExclusive() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100, 99, 100, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100, 100, 101, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100L, 99L, 100L, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100L, 100L, 101L, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100.2D, 100.1D, 100.2D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100.2D, 100.2D, 100.3D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.123),
                    new BigDecimal(100.124),
                    0,
                    100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.122),
                    new BigDecimal(100.123),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_BigDecimal_InBetween() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.122),
                    new BigDecimal(100.124),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_BigDecimal_LowerBiggerThanHigher() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.124),
                    new BigDecimal(100.122),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_Int_InBetween() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100, 99, 101, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_Int_LowerBiggerThanHigher() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenExclusive(100, 101, 99, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenInclusive() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenInclusive(101, 99, 100, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenInclusive(101L, 99L, 100L, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenInclusive(100.2D, 100.3D, 100.4D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenInclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.120),
                    new BigDecimal(100.121),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_BigDecimal_ActualEqualLowerBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenInclusive(
                    new BigDecimal(100.123),
                    new BigDecimal(100.123),
                    new BigDecimal(100.124),
                    0,
                    100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenInclusive_Decimal_LowerBiggerHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenInclusive(100.2D, 100.3D, 100.2D, 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_Int_ActualEqualHigherBound() {
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotBetweenInclusive(101, 100, 101, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEquals(actual.intValue(), 101, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitNotEquals(null, 101, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEquals(actual.intValue(), null, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEquals(actual.longValue(), 90L, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEquals(actual.doubleValue(), 100.2D, 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEquals(actual, expected, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsWithPrecision() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number -> number.isTrue(CNumberWait.waitNotEqualsP(1, 5, 2, 0, 100), "%s#%s", getParams()));
    verify(
        number -> number.isTrue(CNumberWait.waitNotEqualsP(5, 1, 2, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitNotEqualsP(null, 5, 2, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(CNumberWait.waitNotEqualsP(1, null, 2, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEqualsP(2000L, 1000L, 10L, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEqualsP(1000D, 1001D, 0.5, 0, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEqualsP(actual, expected, new BigDecimal(0.000001), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_BigDecimal_InRange() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEqualsP(expected, actual, new BigDecimal(0.001), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_ActualBiggerExpected() {
    verify(
        number -> number.isTrue(CNumberWait.waitNotEqualsP(5, 1, 6, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_BothNull() {
    verify(
        number ->
            number.isTrue(CNumberWait.waitNotEqualsP(null, null, 6, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_InRange() {
    verify(
        number -> number.isTrue(CNumberWait.waitNotEqualsP(1, 5, 6, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BigDecimalEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(CNumberWait.waitNotEquals(actual, actual, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(
        number ->
            number.isTrue(CNumberWait.waitNotEquals(null, null, 0, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_IntEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                CNumberWait.waitNotEquals(actual.intValue(), actual.intValue(), 0, 100),
                "%s#%s",
                getParams()));
  }

  public abstract void verify(Consumer<CBooleanVerification> action);
}
