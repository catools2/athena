package org.catools.common.tests.wait;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CCollectionWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.List;

public class CCollectionWaitTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContains(new CList<>(1, 2, 3), 1, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitContains(List.of(1, 2, 3), 2, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitContains(new CSet<>(1, 2, 3), 3, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CList<>(1, 2, 3), List.of(1, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CSet<>(1, 2, 3), List.of(2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CSet<>(1, 2, 3), new CSet<>(), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CList<>(1, 2, 3), List.of(1, 23), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CList<>(), new CSet<>(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsAll(new CList<>(), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsNone(new CList<>(1, 2, 3), List.of(4, 5, 6), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsNone(new CList<>(1, 2, 3), new CSet<>(4, 5, 6), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsNone(new CSet<>(1, 2, 3), List.of(4, 5, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsNone(new CList<>(1, 2, 3), List.of(1, 5, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsNone(new CList<>(1, 2, 3), new CSet<>(2, 5, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsNone(new CSet<>(1, 2, 3), List.of(3, 6), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContainsNone(new CSet<>(1, 2, 3), new CSet<>(), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContains(new CList<>(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContains(List.of(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitContains(new CSet<>(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEmptyOrContains(new CList<>(1, 2, 3), 1, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitEmptyOrContains(new CSet<>(), 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEmptyOrContains(new CList<>(1, 2, 3), 6, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    CVerify.Bool.isTrue(CCollectionWait.waitEmptyOrContains(null, 6, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEmptyOrNotContains(new CList<>(1, 2, 3), 5, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitEmptyOrNotContains(new CSet<>(), 1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEmptyOrNotContains(new CList<>(1, 2, 3), 3, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    CVerify.Bool.isTrue(CCollectionWait.waitEmptyOrNotContains(null, 0, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CList<>(1, 2, 3), List.of(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CList<>(1, 2, 3), new CSet<>(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CSet<>(1, 2, 3), List.of(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CSet<>(), List.of(), 0, 100), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CList<>(1, 2, 3), List.of(1, 3), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CList<>(1, 2, 3), new CSet<>(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CSet<>(1, 2, 3), List.of(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitEquals(new CSet<>(), List.of(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsEmpty(new CList<>(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CCollectionWait.waitIsEmpty(new CSet<>(), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CCollectionWait.waitIsEmpty(List.of(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsEmpty(new CList<>(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsEmpty(new CSet<>(2), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsEmpty(List.of(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsNotEmpty(new CList<>(1), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CCollectionWait.waitIsNotEmpty(new CSet<>(2), 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CCollectionWait.waitIsNotEmpty(List.of(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsNotEmpty(new CList<>(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsNotEmpty(List.of(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    CVerify.Bool.isTrue(CCollectionWait.waitIsNotEmpty(List.of(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContains(new CList<>(1, 2, 3), 4, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContains(List.of(1), 2, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContains(new CSet<>(), List.of(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContainsAll(new CList<>(1, 2, 3), List.of(1, 4), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContainsAll(new CSet<>(), List.of(1), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContainsAll(new CList<>(1, 2, 3), List.of(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContainsAll(new CList<>(1, 2, 3), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContainsAll(new CSet<>(), new CSet<>(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContains(new CList<>(1, 2, 3), 1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContains(new CSet<>(1, 2, 3), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    CVerify.Bool.isTrue(
        CCollectionWait.waitNotContains(List.of(1), 1, 0, 100), "%s#%s", getParams());
  }
}
