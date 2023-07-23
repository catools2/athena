package org.catools.common.tests.waitVerifier;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CCollectionWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

public class CCollectionWaitVerifyTest extends CBaseWaitVerifyTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeEquals() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifySizeEquals(verifier, 3));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifySizeEquals(verifier, 3, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsGreaterThan() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifySizeIsGreaterThan(verifier, 2, 1));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifySizeIsGreaterThan(verifier, 2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsLessThan() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifySizeIsLessThan(verifier, 4, 1));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifySizeIsLessThan(verifier, 4));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitHas() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyHas(verifier, i -> i == 2, 1));
    verify(verifier -> toWaitVerifier(List.of(1, 2, 3)).verifyHas(verifier, i -> i == 2));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyHas(verifier, i -> i == 2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContains(verifier, 1, 1));
    verify(verifier -> toWaitVerifier(List.of(1, 2, 3)).verifyContains(verifier, 2));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyContains(verifier, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsAll(verifier, List.of(1, 3), 1));
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsAll(verifier, new CSet<>(1, 2)));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyContainsAll(verifier, List.of(2, 3)));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyContainsAll(verifier, new CSet<>()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsAll(verifier, List.of(1, 23)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsAll(verifier, new CSet<>(1, 2, 3, 4)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    verify(verifier -> toWaitVerifier(new CList<>()).verifyContainsAll(verifier, new CSet<>(1)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    verify(verifier -> toWaitVerifier(new CList<>()).verifyContainsAll(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsNone(verifier, List.of(4, 5, 6), 1));
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsNone(verifier, new CSet<>(4, 5, 6)));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyContainsNone(verifier, List.of(4, 5, 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsNone(verifier, List.of(1, 5, 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContainsNone(verifier, new CSet<>(2, 5, 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyContainsNone(verifier, List.of(3, 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyContainsNone(verifier, new CSet<>()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyContains(verifier, 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    verify(verifier -> toWaitVerifier(List.of(1, 2, 3)).verifyContains(verifier, 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyContains(verifier, 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEmptyOrContains(verifier, 1, 1));
    verify(verifier -> toWaitVerifier(new CSet<>()).verifyEmptyOrContains(verifier, 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEmptyOrContains(verifier, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    verify(verifier -> toWaitVerifier(null).verifyEmptyOrContains(verifier, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEmptyOrNotContains(verifier, 5));
    verify(verifier -> toWaitVerifier(new CSet<>()).verifyEmptyOrNotContains(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEmptyOrNotContains(verifier, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    verify(verifier -> toWaitVerifier(null).verifyEmptyOrNotContains(verifier, 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEquals(verifier, List.of(1, 2, 3), 1));
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEquals(verifier, new CSet<>(1, 2, 3)));
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyEquals(verifier, List.of(1, 2, 3)));
    verify(verifier -> toWaitVerifier(new CSet<>()).verifyEquals(verifier, List.of()));
  }  // Negative

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEquals(verifier, List.of(1, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyEquals(verifier, new CSet<>(3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(verifier -> toWaitVerifier(List.of(1)).verifyEquals(verifier, new CSet<>(1, 2, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    verify(verifier -> toWaitVerifier(List.of(1)).verifyEquals(verifier, new CSet<>()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(verifier -> toWaitVerifier(new CList<>()).verifyIsEmpty(verifier, 1));
    verify(verifier -> toWaitVerifier(new CSet<>()).verifyIsEmpty(verifier));
    verify(verifier -> toWaitVerifier(List.of()).verifyIsEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1)).verifyIsEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    verify(verifier -> toWaitVerifier(new CSet<>(2)).verifyIsEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    verify(verifier -> toWaitVerifier(List.of(3)).verifyIsEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(verifier -> toWaitVerifier(new CList<>(1)).verifyIsNotEmpty(verifier, 1));
    verify(verifier -> toWaitVerifier(new CSet<>(2)).verifyIsNotEmpty(verifier));
    verify(verifier -> toWaitVerifier(List.of(3)).verifyIsNotEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    verify(verifier -> toWaitVerifier(new CList<>()).verifyIsNotEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    verify(verifier -> toWaitVerifier(List.of()).verifyIsNotEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    verify(verifier -> toWaitVerifier(List.of()).verifyIsNotEmpty(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyNotContains(verifier, 4, 1));
    verify(verifier -> toWaitVerifier(List.of(1)).verifyNotContains(verifier, 2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyNotContainsAll(verifier, List.of(1, 4), 1));
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyNotContainsAll(verifier, new CSet<>(1, 2, 3, 4)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyNotContainsAll(verifier, List.of(1, 2, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyNotContainsAll(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    verify(verifier -> toWaitVerifier(new CSet<>()).verifyNotContainsAll(verifier, new CSet<>()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    verify(verifier -> toWaitVerifier(new CList<>(1, 2, 3)).verifyNotContains(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    verify(verifier -> toWaitVerifier(new CSet<>(1, 2, 3)).verifyNotContains(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    verify(verifier -> toWaitVerifier(List.of(1)).verifyNotContains(verifier, 1));
  }

  private <O> CCollectionWaitVerifier<O, Collection<O>> toWaitVerifier(Collection<O> val) {
    return () -> val;
  }
}
