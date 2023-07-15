package org.catools.common.tests.verify.wait;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.interfaces.CCollectionWaiter;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

public class CCollectionWaiterTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1, 2, 3)).waitContains(1), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(Arrays.asList(1, 2, 3)).waitContains(2), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(new CSet<>(1, 2, 3)).waitContains(3), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsAll(Arrays.asList(1, 3)), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsAll(new CSet<>(1, 2)), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CSet<>(1, 2, 3)).waitContainsAll(Arrays.asList(2, 3)), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CSet<>(1, 2, 3)).waitContainsAll(new CSet<>()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsAll(Arrays.asList(1, 23)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsAll(new CSet<>(1, 2, 3, 4)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>()).waitContainsAll(new CSet<>(1)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    CVerify.Bool.isTrue(toWaiter(new CList<>()).waitContainsAll(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsNone(Arrays.asList(4, 5, 6)),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsNone(new CSet<>(4, 5, 6)), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CSet<>(1, 2, 3)).waitContainsNone(Arrays.asList(4, 5, 6)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsNone(Arrays.asList(1, 5, 6)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitContainsNone(new CSet<>(2, 5, 6)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    CVerify.Bool.isTrue(
        toWaiter(new CSet<>(1, 2, 3)).waitContainsNone(Arrays.asList(3, 6)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    CVerify.Bool.isTrue(
        toWaiter(new CSet<>(1, 2, 3)).waitContainsNone(new CSet<>()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1, 2, 3)).waitContains(5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    CVerify.Bool.isTrue(toWaiter(Arrays.asList(1, 2, 3)).waitContains(5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    CVerify.Bool.isTrue(toWaiter(new CSet<>(1, 2, 3)).waitContains(5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1, 2, 3)).waitEmptyOrContains(1), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(new CSet<>()).waitEmptyOrContains(5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1, 2, 3)).waitEmptyOrContains(6), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    CVerify.Bool.isTrue(toWaiter(null).waitEmptyOrContains(6), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitEmptyOrNotContains(5), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(new CSet<>()).waitEmptyOrNotContains(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitEmptyOrNotContains(3), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    CVerify.Bool.isTrue(toWaiter(null).waitEmptyOrNotContains(0), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitEquals(Arrays.asList(1, 2, 3)), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitEquals(new CSet<>(1, 2, 3)), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CSet<>(1, 2, 3)).waitEquals(Arrays.asList(1, 2, 3)), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(new CSet<>()).waitEquals(Arrays.asList()), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitEquals(Arrays.asList(1, 3)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitEquals(new CSet<>(3)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    CVerify.Bool.isTrue(
        toWaiter(Arrays.asList(1)).waitEquals(new CSet<>(1, 2, 3)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    CVerify.Bool.isTrue(toWaiter(Arrays.asList(1)).waitEquals(new CSet<>()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerify.Bool.isTrue(toWaiter(new CList<>()).waitIsEmpty(1), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(new CSet<>()).waitIsEmpty(1), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(Arrays.asList()).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1)).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    CVerify.Bool.isTrue(toWaiter(new CSet<>(2)).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    CVerify.Bool.isTrue(toWaiter(Arrays.asList(3)).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1)).waitIsNotEmpty(1), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(new CSet<>(2)).waitIsNotEmpty(1), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(Arrays.asList(3)).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    CVerify.Bool.isTrue(toWaiter(new CList<>()).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    CVerify.Bool.isTrue(toWaiter(Arrays.asList()).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    CVerify.Bool.isTrue(toWaiter(Arrays.asList()).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1, 2, 3)).waitNotContains(4), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter(Arrays.asList(1)).waitNotContains(2), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter(Arrays.asList(1)).waitNotContains(new CSet<>()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitNotContainsAll(Arrays.asList(1, 4)),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitNotContainsAll(new CSet<>(1, 2, 3, 4)),
        "%s#%s",
        getParams());
    CVerify.Bool.isFalse(
        toWaiter(Arrays.asList(1)).waitNotContainsAll(new CSet<>()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitNotContainsAll(Arrays.asList(1, 2, 3)),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    CVerify.Bool.isTrue(
        toWaiter(new CList<>(1, 2, 3)).waitNotContainsAll(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    CVerify.Bool.isTrue(
        toWaiter(new CSet<>()).waitNotContainsAll(new CSet<>()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    CVerify.Bool.isTrue(toWaiter(new CList<>(1, 2, 3)).waitNotContains(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    CVerify.Bool.isTrue(toWaiter(new CSet<>(1, 2, 3)).waitNotContains(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    CVerify.Bool.isTrue(toWaiter(Arrays.asList(1)).waitNotContains(1), "%s#%s", getParams());
  }

  private CCollectionWaiter toWaiter(Collection val) {
    return () -> val;
  }
}
