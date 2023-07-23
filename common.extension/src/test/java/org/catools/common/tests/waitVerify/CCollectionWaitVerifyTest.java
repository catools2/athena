package org.catools.common.tests.waitVerify;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.interfaces.waitVerify.CCollectionWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

public class CCollectionWaitVerifyTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeEquals() {
    toWaitVerify(new CList<>(1, 2, 3)).verifySizeEquals(3);
    toWaitVerify(new CSet<>(1, 2, 3)).verifySizeEquals(3);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsGreaterThan() {
    toWaitVerify(new CList<>(1, 2, 3)).verifySizeIsGreaterThan(2);
    toWaitVerify(new CSet<>(1, 2, 3)).verifySizeIsGreaterThan(2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsLessThan() {
    toWaitVerify(new CList<>(1, 2, 3)).verifySizeIsLessThan(4);
    toWaitVerify(new CSet<>(1, 2, 3)).verifySizeIsLessThan(4);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitHas() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyHas(i -> i == 2);
    toWaitVerify(List.of(1, 2, 3)).verifyHas(i -> i == 2);
    toWaitVerify(new CSet<>(1, 2, 3)).verifyHas(i -> i == 2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContains(1);
    toWaitVerify(List.of(1, 2, 3)).verifyContains(2);
    toWaitVerify(new CSet<>(1, 2, 3)).verifyContains(3);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsAll(List.of(1, 3));
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsAll(new CSet<>(1, 2));
    toWaitVerify(new CSet<>(1, 2, 3)).verifyContainsAll(List.of(2, 3));
    toWaitVerify(new CSet<>(1, 2, 3)).verifyContainsAll(new CSet<>());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsAll(List.of(1, 23));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsAll(new CSet<>(1, 2, 3, 4));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    toWaitVerify(new CList<>()).verifyContainsAll(new CSet<>(1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    toWaitVerify(new CList<>()).verifyContainsAll(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsNone(List.of(4, 5, 6));
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsNone(new CSet<>(4, 5, 6));
    toWaitVerify(new CSet<>(1, 2, 3)).verifyContainsNone(List.of(4, 5, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsNone(List.of(1, 5, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContainsNone(new CSet<>(2, 5, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    toWaitVerify(new CSet<>(1, 2, 3)).verifyContainsNone(List.of(3, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    toWaitVerify(new CSet<>(1, 2, 3)).verifyContainsNone(new CSet<>());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyContains(5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    toWaitVerify(List.of(1, 2, 3)).verifyContains(5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    toWaitVerify(new CSet<>(1, 2, 3)).verifyContains(5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyEmptyOrContains(1);
    toWaitVerify(new CSet<>()).verifyEmptyOrContains(5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyEmptyOrContains(6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    toWaitVerify(null).verifyEmptyOrContains(6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyEmptyOrNotContains(5);
    toWaitVerify(new CSet<>()).verifyEmptyOrNotContains(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyEmptyOrNotContains(3);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    toWaitVerify(null).verifyEmptyOrNotContains(0);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyEquals(List.of(1, 2, 3));
    toWaitVerify(new CList<>(1, 2, 3)).verifyEquals(new CSet<>(1, 2, 3));
    toWaitVerify(new CSet<>(1, 2, 3)).verifyEquals(List.of(1, 2, 3));
    toWaitVerify(new CSet<>()).verifyEquals(List.of());
  }  // Negative

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyEquals(List.of(1, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyEquals(new CSet<>(3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    toWaitVerify(List.of(1)).verifyEquals(new CSet<>(1, 2, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    toWaitVerify(List.of(1)).verifyEquals(new CSet<>());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    toWaitVerify(new CList<>()).verifyIsEmpty();
    toWaitVerify(new CSet<>()).verifyIsEmpty();
    toWaitVerify(List.of()).verifyIsEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    toWaitVerify(new CList<>(1)).verifyIsEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    toWaitVerify(new CSet<>(2)).verifyIsEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    toWaitVerify(List.of(3)).verifyIsEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    toWaitVerify(new CList<>(1)).verifyIsNotEmpty();
    toWaitVerify(new CSet<>(2)).verifyIsNotEmpty();
    toWaitVerify(List.of(3)).verifyIsNotEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    toWaitVerify(new CList<>()).verifyIsNotEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    toWaitVerify(List.of()).verifyIsNotEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    toWaitVerify(List.of()).verifyIsNotEmpty();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyNotContains(4);
    toWaitVerify(List.of(1)).verifyNotContains(2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyNotContainsAll(List.of(1, 4));
    toWaitVerify(new CList<>(1, 2, 3)).verifyNotContainsAll(new CSet<>(1, 2, 3, 4));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyNotContainsAll(List.of(1, 2, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyNotContainsAll(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    toWaitVerify(new CSet<>()).verifyNotContainsAll(new CSet<>());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    toWaitVerify(new CList<>(1, 2, 3)).verifyNotContains(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    toWaitVerify(new CSet<>(1, 2, 3)).verifyNotContains(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    toWaitVerify(List.of(1)).verifyNotContains(1);
  }

  private <O> CCollectionWaitVerify<O, Collection<O>> toWaitVerify(Collection<O> val) {
    return () -> val;
  }
}
