package org.catools.common.tests.verify.wait;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CCollectionWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Arrays;

public class CCollectionWaitTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitContains(new CList<>(1, 2, 3), 1, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContains(Arrays.asList(1, 2, 3), 2, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContains(new CSet<>(1, 2, 3), 3, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CList<>(1, 2, 3), Arrays.asList(1, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CSet<>(1, 2, 3), Arrays.asList(2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CSet<>(1, 2, 3), new CSet<>(), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CList<>(1, 2, 3), Arrays.asList(1, 23), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CList<>(), new CSet<>(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsAll(new CList<>(), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsNone(new CList<>(1, 2, 3), Arrays.asList(4, 5, 6), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContainsNone(new CList<>(1, 2, 3), new CSet<>(4, 5, 6), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContainsNone(new CSet<>(1, 2, 3), Arrays.asList(4, 5, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsNone(new CList<>(1, 2, 3), Arrays.asList(1, 5, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsNone(new CList<>(1, 2, 3), new CSet<>(2, 5, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsNone(new CSet<>(1, 2, 3), Arrays.asList(3, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsNone(new CSet<>(1, 2, 3), new CSet<>(), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitContains(new CList<>(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    CVerify.Bool.isTrue(
        toWaiter().waitContains(Arrays.asList(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    CVerify.Bool.isTrue(
        toWaiter().waitContains(new CSet<>(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitEmptyOrContains(new CList<>(1, 2, 3), 1, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEmptyOrContains(new CSet<>(), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitEmptyOrContains(new CList<>(1, 2, 3), 6, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    CVerify.Bool.isTrue(toWaiter().waitEmptyOrContains(null, 6, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitEmptyOrNotContains(new CList<>(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEmptyOrNotContains(new CSet<>(), 1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitEmptyOrNotContains(new CList<>(1, 2, 3), 3, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    CVerify.Bool.isTrue(toWaiter().waitEmptyOrNotContains(null, 0, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CList<>(1, 2, 3), Arrays.asList(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CList<>(1, 2, 3), new CSet<>(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CSet<>(1, 2, 3), Arrays.asList(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CSet<>(), Arrays.asList(), 0, 100), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CList<>(1, 2, 3), Arrays.asList(1, 3), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CList<>(1, 2, 3), new CSet<>(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CSet<>(1, 2, 3), Arrays.asList(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    CVerify.Bool.isTrue(
        toWaiter().waitEquals(new CSet<>(), Arrays.asList(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty(new CList<>(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty(new CSet<>(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty(Arrays.asList(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty(new CList<>(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty(new CSet<>(2), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty(Arrays.asList(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(new CList<>(1), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(new CSet<>(2), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(Arrays.asList(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(new CList<>(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(Arrays.asList(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(Arrays.asList(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains(new CList<>(1, 2, 3), 4, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains(Arrays.asList(1), 2, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains(new CSet<>(), Arrays.asList(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsAll(new CList<>(1, 2, 3), Arrays.asList(1, 4), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsAll(new CSet<>(), Arrays.asList(1), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsAll(new CList<>(1, 2, 3), Arrays.asList(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsAll(new CList<>(1, 2, 3), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsAll(new CSet<>(), new CSet<>(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains(new CList<>(1, 2, 3), 1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains(new CSet<>(1, 2, 3), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains(Arrays.asList(1), 1, 0, 100), "%s#%s", getParams());
  }

  private CCollectionWait toWaiter() {
    return new CCollectionWait();
  }
}
