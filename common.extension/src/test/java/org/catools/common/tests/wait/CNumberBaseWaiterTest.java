package org.catools.common.tests.wait;

import org.catools.common.exception.CInvalidRangeException;
import org.catools.common.extensions.verify.hard.CBooleanVerification;
import org.catools.common.extensions.wait.interfaces.CNumberWaiter;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;

public abstract class CNumberBaseWaiterTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenExclusive() {
    verify(
        number -> number.isTrue(toWaiter(100).waitBetweenExclusive(99, 101), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(100L).waitBetweenExclusive(99L, 101L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitBetweenExclusive(100.1D, 100.3D), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenExclusive(new BigDecimal(100.122), new BigDecimal(100.124), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchHigherBound() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenExclusive(new BigDecimal(100.122), new BigDecimal(100.123), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_BigDecimal_ActualMatchLowerBound() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenExclusive(new BigDecimal(100.123), new BigDecimal(100.124), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_BigDecimal_LowerBoundIsGreaterThanHigherBound() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenExclusive(new BigDecimal(100.126), new BigDecimal(100.124), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchHigherBound() {
    verify(
        number -> number.isTrue(toWaiter(100).waitBetweenExclusive(99, 100), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenExclusive_Int_ActualMatchLowerBound() {
    verify(
        number ->
            number.isTrue(toWaiter(100).waitBetweenExclusive(100, 101), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenExclusive_Int_LowerBoundIsGreaterThanHigherBound() {
    verify(
        number ->
            number.isTrue(toWaiter(100).waitBetweenExclusive(102, 101), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testBetweenInclusive() {
    verify(
        number -> number.isTrue(toWaiter(100).waitBetweenInclusive(99, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(100).waitBetweenInclusive(100, 101), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(100L).waitBetweenInclusive(99L, 100L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(100L).waitBetweenInclusive(100L, 101L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitBetweenInclusive(100.1D, 100.2D), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitBetweenInclusive(100.2D, 100.3D), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenInclusive(new BigDecimal(100.123), new BigDecimal(100.124), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenInclusive(new BigDecimal(100.122), new BigDecimal(100.123), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualBiggerThanHigherBound() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenInclusive(new BigDecimal(100.121), new BigDecimal(100.122), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_BigDecimal_ActualLessThanLowerBound() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitBetweenInclusive(new BigDecimal(100.124), new BigDecimal(100.125), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualBiggerThanHigherBound() {
    verify(
        number ->
            number.isTrue(toWaiter(100L).waitBetweenInclusive(98L, 99L), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testBetweenInclusive_Long_ActualLessThanLowerBound() {
    verify(
        number ->
            number.isTrue(toWaiter(100L).waitBetweenInclusive(101L, 102L), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testBetweenInclusive_Long_LowerBoundIsGreaterThanHigherBound() {
    verify(
        number ->
            number.isTrue(toWaiter(100L).waitBetweenInclusive(102L, 101L), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.intValue()).waitEquals(actual.intValue()), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.longValue()).waitEquals(actual.longValue()), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.doubleValue()).waitEquals(actual.doubleValue()),
                "%s#%s",
                getParams()));
    verify(number -> number.isTrue(toWaiter(actual).waitEquals(actual), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimalWithPrecision_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(toWaiter(actual).waitEqualsP(expected, 0.000009), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsBigDecimal_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(101.123134);
    verify(number -> number.isTrue(toWaiter(actual).waitEquals(expected), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDecimalWithPrecision_NotEquals() {
    verify(number -> number.isTrue(toWaiter(1000D).waitEqualsP(1001D, 0.5), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsDouble_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(101.123134);
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.doubleValue()).waitEquals(expected.doubleValue()),
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
                toWaiter(actual.intValue()).waitEquals(expected.intValue()), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsLong_NotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(101.123134);
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.longValue()).waitEquals(expected.longValue()),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(number -> number.isTrue(toWaiter(1).waitEqualsP(5, 6), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(2000L).waitEqualsP(1000L, 1000), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(1000D).waitEqualsP(1001D, 1), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(1001D).waitEqualsP(1000D, 1), "%s#%s", getParams()));
    verify(
        number -> number.isTrue(toWaiter(actual).waitEqualsP(actual, 0.0), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(actual).waitEqualsP(expected, 0.00009), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualBiggerExpected() {
    verify(number -> number.isTrue(toWaiter(10).waitEquals(0, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ActualNull() {
    verify(number -> number.isTrue(toWaiter(null).waitEquals(10, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsWithPrecision_BothNull() {
    verify(number -> number.isTrue(toWaiter(null).waitEquals(null, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsWithPrecision_ExpectedNull() {
    verify(number -> number.isTrue(toWaiter(10).waitEquals(null, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ActualNull() {
    verify(number -> number.isTrue(toWaiter(null).waitEquals(10), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals_BothNull() {
    verify(number -> number.isTrue(toWaiter(null).waitEquals(null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_ExpectedNull() {
    verify(number -> number.isTrue(toWaiter(10).waitEquals(null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreater() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(toWaiter(101).waitGreater(actual.intValue()), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(101L).waitGreater(actual.longValue()), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitGreater(actual.doubleValue()), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(expected).waitGreater(actual), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGreaterOrEqual() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(
                toWaiter(101).waitGreaterOrEqual(actual.intValue()), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.intValue()).waitGreaterOrEqual(actual.intValue()),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(101L).waitGreaterOrEqual(actual.longValue()), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.longValue()).waitGreaterOrEqual(actual.longValue()),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitGreaterOrEqual(actual.doubleValue()), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.doubleValue()).waitGreaterOrEqual(actual.doubleValue()),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(expected).waitGreaterOrEqual(actual), "%s#%s", getParams()));
    verify(
        number -> number.isTrue(toWaiter(actual).waitGreaterOrEqual(actual), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N1() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                toWaiter(101).waitGreaterOrEqual(actual.intValue()), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N2() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                toWaiter(101L).waitGreaterOrEqual(actual.longValue()), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N3() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitGreaterOrEqual(actual.doubleValue()), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreaterOrEqual_N4() {
    BigDecimal actual = new BigDecimal(102.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(
        number ->
            number.isTrue(toWaiter(expected).waitGreaterOrEqual(actual), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N1() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(toWaiter(101).waitGreater(actual.intValue()), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N2() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(toWaiter(101L).waitGreater(actual.longValue()), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N3() {
    BigDecimal actual = new BigDecimal(102.1231);
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitGreater(actual.doubleValue()), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGreater_N4() {
    BigDecimal actual = new BigDecimal(102.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(number -> number.isTrue(toWaiter(expected).waitGreater(actual), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNull() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(toWaiter(actual.intValue()).waitIsNotNull(1), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(actual.longValue()).waitIsNotNull(1), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(actual.doubleValue()).waitIsNotNull(1), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(actual).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N1() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N2() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N3() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNull_N4() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N1() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N2() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N3() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNull_N4() {
    verify(number -> number.isTrue(toWaiter(null).waitIsNotNull(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLess() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number -> number.isTrue(toWaiter(actual.intValue()).waitLess(101), "%s#%s", getParams()));
    verify(
        number -> number.isTrue(toWaiter(actual.longValue()).waitLess(101L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(actual.doubleValue()).waitLess(100.2D), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(actual).waitLess(expected), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLessOrEqual() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(toWaiter(actual.intValue()).waitLessOrEqual(101), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.intValue()).waitLessOrEqual(actual.intValue()),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.longValue()).waitLessOrEqual(101L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.longValue()).waitLessOrEqual(actual.longValue()),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.doubleValue()).waitLessOrEqual(100.2D), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.doubleValue()).waitLessOrEqual(actual.doubleValue()),
                "%s#%s",
                getParams()));
    verify(
        number -> number.isTrue(toWaiter(actual).waitLessOrEqual(expected), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(actual).waitLessOrEqual(actual), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N1() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(toWaiter(actual.intValue()).waitLessOrEqual(91), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N2() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(toWaiter(actual.longValue()).waitLessOrEqual(91L), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N3() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.doubleValue()).waitLessOrEqual(99.2D), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLessOrEqual_N4() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(
        number -> number.isTrue(toWaiter(actual).waitLessOrEqual(expected), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N1() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(number -> number.isTrue(toWaiter(actual.intValue()).waitLess(90), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N2() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number -> number.isTrue(toWaiter(actual.longValue()).waitLess(91L), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N3() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(toWaiter(actual.doubleValue()).waitLess(90.2D), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLess_N4() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(99.123134);
    verify(number -> number.isTrue(toWaiter(actual).waitLess(expected), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenExclusive() {
    verify(
        number ->
            number.isTrue(toWaiter(100).waitNotBetweenExclusive(99, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(100).waitNotBetweenExclusive(100, 101), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(100L).waitNotBetweenExclusive(99L, 100L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100L).waitNotBetweenExclusive(100L, 101L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitNotBetweenExclusive(100.1D, 100.2D), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitNotBetweenExclusive(100.2D, 100.3D), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitNotBetweenExclusive(
                        new BigDecimal(100.123), new BigDecimal(100.124), 0, 100),
                "%s#%s",
                getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitNotBetweenExclusive(
                        new BigDecimal(100.122), new BigDecimal(100.123), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_BigDecimal_InBetween() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitNotBetweenExclusive(
                        new BigDecimal(100.122), new BigDecimal(100.124), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_BigDecimal_LowerBiggerThanHigher() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitNotBetweenExclusive(
                        new BigDecimal(100.124), new BigDecimal(100.122), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenExclusive_Int_InBetween() {
    verify(
        number ->
            number.isTrue(toWaiter(100).waitNotBetweenExclusive(99, 101), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenExclusive_Int_LowerBiggerThanHigher() {
    verify(
        number ->
            number.isTrue(toWaiter(100).waitNotBetweenExclusive(101, 99), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotBetweenInclusive() {
    verify(
        number ->
            number.isTrue(toWaiter(101).waitNotBetweenInclusive(99, 100), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(101L).waitNotBetweenInclusive(99L, 100L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitNotBetweenInclusive(100.3D, 100.4D), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitNotBetweenInclusive(
                        new BigDecimal(100.120), new BigDecimal(100.121), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_BigDecimal_ActualEqualLowerBound() {
    verify(
        number ->
            number.isTrue(
                toWaiter(new BigDecimal(100.123))
                    .waitNotBetweenInclusive(
                        new BigDecimal(100.123), new BigDecimal(100.124), 0, 100),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = CInvalidRangeException.class)
  public void testNotBetweenInclusive_Decimal_LowerBiggerHigherBound() {
    verify(
        number ->
            number.isTrue(
                toWaiter(100.2D).waitNotBetweenInclusive(100.3D, 100.2D), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotBetweenInclusive_Int_ActualEqualHigherBound() {
    verify(
        number ->
            number.isTrue(toWaiter(101).waitNotBetweenInclusive(100, 101), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(
        number ->
            number.isTrue(toWaiter(actual.intValue()).waitNotEquals(101), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(null).waitNotEquals(101), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(actual.intValue()).waitNotEquals(null), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(toWaiter(actual.longValue()).waitNotEquals(90L), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.doubleValue()).waitNotEquals(100.2D), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(actual).waitNotEquals(expected), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsWithPrecision() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.123134);
    verify(number -> number.isTrue(toWaiter(1).waitNotEqualsP(5, 2), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(5).waitNotEqualsP(1, 2), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(null).waitNotEqualsP(5, 2), "%s#%s", getParams()));
    verify(number -> number.isTrue(toWaiter(1).waitNotEqualsP(null, 2), "%s#%s", getParams()));
    verify(
        number -> number.isTrue(toWaiter(2000L).waitNotEqualsP(1000L, 10), "%s#%s", getParams()));
    verify(
        number -> number.isTrue(toWaiter(1000D).waitNotEqualsP(1001D, 0.5), "%s#%s", getParams()));
    verify(
        number ->
            number.isTrue(
                toWaiter(actual).waitNotEqualsP(expected, 0.000001), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_BigDecimal_InRange() {
    BigDecimal actual = new BigDecimal(100.1231);
    BigDecimal expected = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(toWaiter(expected).waitNotEqualsP(actual, 0.001), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_ActualBiggerExpected() {
    verify(number -> number.isTrue(toWaiter(5).waitNotEqualsP(1, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_BothNull() {
    verify(number -> number.isTrue(toWaiter(null).waitNotEqualsP(null, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsWithPrecision_Int_InRange() {
    verify(number -> number.isTrue(toWaiter(1).waitNotEqualsP(5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BigDecimalEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(number -> number.isTrue(toWaiter(actual).waitNotEquals(actual), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_BothNull() {
    verify(number -> number.isTrue(toWaiter(null).waitNotEquals(null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEquals_IntEquals() {
    BigDecimal actual = new BigDecimal(100.1231);
    verify(
        number ->
            number.isTrue(
                toWaiter(actual.intValue()).waitNotEquals(actual.intValue()),
                "%s#%s",
                getParams()));
  }

  private CNumberWaiter toWaiter(Number val) {
    return () -> val;
  }

  public abstract void verify(Consumer<CBooleanVerification> action);
}
