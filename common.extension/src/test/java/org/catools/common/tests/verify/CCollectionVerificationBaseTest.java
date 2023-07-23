package org.catools.common.tests.verify;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.hard.CCollectionVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Consumer;

public abstract class CCollectionVerificationBaseTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(collection -> collection.contains(new CList<>(1, 2, 3), 1));
    verify(collection -> collection.contains(new CList<>(1, 2, 3), 1, "CollectionExpectationTest ::> contains "));
    verify(collection -> collection.contains(List.of(1, 2, 3), 2));
    verify(collection -> collection.contains(List.of(1, 2, 3), 2, "CollectionExpectationTest ::> contains "));
    verify(collection -> collection.contains(new CSet<>(1, 2, 3), 3));
    verify(collection -> collection.contains(new CSet<>(1, 2, 3), 3, "CollectionExpectationTest ::> contains "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    verify(collection -> collection.containsAll(new CList<>(1, 2, 3), List.of(1, 3)));
    verify(collection -> collection.containsAll(new CList<>(1, 2, 3), List.of(1, 3), "CollectionExpectationTest ::> containsAll "));
    verify(collection -> collection.containsAll(new CList<>(1, 2, 3), new CSet<>(1, 2)));
    verify(collection -> collection.containsAll(new CList<>(1, 2, 3), new CSet<>(1, 2), "CollectionExpectationTest ::> containsAll "));
    verify(collection -> collection.containsAll(new CSet<>(1, 2, 3), List.of(2, 3)));
    verify(collection -> collection.containsAll(new CSet<>(1, 2, 3), List.of(2, 3), "CollectionExpectationTest ::> containsAll "));
    verify(collection -> collection.containsAll(new CSet<>(1, 2, 3), new CSet<>()));
    verify(collection -> collection.containsAll(new CSet<>(1, 2, 3), new CSet<>(), "CollectionExpectationTest ::> containsAll "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    verify(collection -> collection.containsAll(new CList<>(1, 2, 3), List.of(1, 23), "CollectionExpectationTest ::> containsAll "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    verify(collection -> collection.containsAll(new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    verify(collection -> collection.containsAll(new CList<>(), new CSet<>(1)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    verify(collection -> collection.containsAll(new CList<>(), null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    verify(collection -> collection.containsNone(new CList<>(1, 2, 3), List.of(4, 5, 6)));
    verify(collection -> collection.containsNone(new CList<>(1, 2, 3), List.of(4, 5, 6), "CollectionExpectationTest ::> containsNone "));
    verify(collection -> collection.containsNone(new CList<>(1, 2, 3), new CSet<>(4, 5, 6)));
    verify(collection -> collection.containsNone(new CList<>(1, 2, 3), new CSet<>(4, 5, 6), "CollectionExpectationTest ::> containsNone "));
    verify(collection -> collection.containsNone(new CSet<>(1, 2, 3), List.of(4, 5, 6)));
    verify(collection -> collection.containsNone(new CSet<>(1, 2, 3), List.of(4, 5, 6), "CollectionExpectationTest ::> containsNone "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    verify(collection -> collection.containsNone(new CList<>(1, 2, 3), List.of(1, 5, 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    verify(collection -> collection.containsNone(new CList<>(1, 2, 3), new CSet<>(2, 5, 6), "CollectionExpectationTest ::> containsNone "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    verify(collection -> collection.containsNone(new CSet<>(1, 2, 3), List.of(3, 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    verify(collection -> collection.containsNone(new CSet<>(1, 2, 3), new CSet<>()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    verify(collection -> collection.contains(new CList<>(1, 2, 3), 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    verify(collection -> collection.contains(List.of(1, 2, 3), 5, "CollectionExpectationTest ::> contains "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    verify(collection -> collection.contains(new CSet<>(1, 2, 3), 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    verify(collection -> collection.emptyOrContains(new CList<>(1, 2, 3), 1));
    verify(collection -> collection.emptyOrContains(new CList<>(1, 2, 3), 1, "CollectionExpectationTest ::> emptyOrContains "));
    verify(collection -> collection.emptyOrContains(new CSet<>(), 5));
    verify(collection -> collection.emptyOrContains(new CSet<>(), 5, "CollectionExpectationTest ::> emptyOrContains "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    verify(collection -> collection.emptyOrContains(new CList<>(1, 2, 3), 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    verify(collection -> collection.emptyOrContains(null, 6, "CollectionExpectationTest ::> emptyOrContains "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    verify(collection -> collection.emptyOrNotContains(new CList<>(1, 2, 3), 5));
    verify(collection -> collection.emptyOrNotContains(new CList<>(1, 2, 3), 5, "CollectionExpectationTest ::> emptyOrNotContains "));
    verify(collection -> collection.emptyOrNotContains(new CSet<>(), 1));
    verify(collection -> collection.emptyOrNotContains(new CSet<>(), 1, "CollectionExpectationTest ::> emptyOrNotContains "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    verify(collection -> collection.emptyOrNotContains(new CList<>(1, 2, 3), 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    verify(collection -> collection.emptyOrNotContains(null, 0, "CollectionExpectationTest ::> emptyOrNotContains "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(collection -> collection.equals(new CList<>(1, 2, 3), List.of(1, 2, 3)));
    verify(collection -> collection.equals(new CList<>(1, 2, 3), List.of(1, 2, 3), "CollectionExpectationTest ::> equals "));
    verify(collection -> collection.equals(new CList<>(1, 2, 3), new CSet<>(1, 2, 3)));
    verify(collection -> collection.equals(new CList<>(1, 2, 3), new CSet<>(1, 2, 3), "CollectionExpectationTest ::> equals "));
    verify(collection -> collection.equals(new CSet<>(1, 2, 3), List.of(1, 2, 3)));
    verify(collection -> collection.equals(new CSet<>(1, 2, 3), List.of(1, 2, 3), "CollectionExpectationTest ::> equals "));
    verify(collection -> collection.equals(new CSet<>(), List.of()));
    verify(collection -> collection.equals(new CSet<>(), List.of(), "CollectionExpectationTest ::> equals "));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(collection -> collection.equals(new CList<>(1, 2, 3), List.of(1, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(collection -> collection.equals(new CList<>(1, 2, 3), new CSet<>(3), "CollectionExpectationTest ::> equals "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(collection -> collection.equals(new CSet<>(1, 2, 3), List.of(1)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    verify(collection -> collection.equals(new CSet<>(), List.of(1)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(collection -> collection.isEmpty(new CList<>()));
    verify(collection -> collection.isEmpty(new CList<>(), "CollectionExpectationTest ::> isEmpty "));
    verify(collection -> collection.isEmpty(new CSet<>()));
    verify(collection -> collection.isEmpty(new CSet<>(), "CollectionExpectationTest ::> isEmpty "));
    verify(collection -> collection.isEmpty(List.of()));
    verify(collection -> collection.isEmpty(List.of(), "CollectionExpectationTest ::> isEmpty "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    verify(collection -> collection.isEmpty(new CList<>(1)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    verify(collection -> collection.isEmpty(new CSet<>(2)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    verify(collection -> collection.isEmpty(List.of(3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(collection -> collection.isNotEmpty(new CList<>(1)));
    verify(collection -> collection.isNotEmpty(new CList<>(1), "CollectionExpectationTest ::> isNotEmpty "));
    verify(collection -> collection.isNotEmpty(new CSet<>(2)));
    verify(collection -> collection.isNotEmpty(new CSet<>(2), "CollectionExpectationTest ::> isNotEmpty "));
    verify(collection -> collection.isNotEmpty(List.of(3)));
    verify(collection -> collection.isNotEmpty(List.of(3), "CollectionExpectationTest ::> isNotEmpty "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    verify(collection -> collection.isNotEmpty(new CList<>()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    verify(collection -> collection.isNotEmpty(List.of()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    verify(collection -> collection.isNotEmpty(List.of()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(collection -> collection.notContains(new CList<>(1, 2, 3), 4));
    verify(collection -> collection.notContains(new CList<>(1, 2, 3), 4, "CollectionExpectationTest ::> notContains "));
    verify(collection -> collection.notContains(List.of(1), 2));
    verify(collection -> collection.notContains(List.of(1), 2, "CollectionExpectationTest ::> notContains "));
    verify(collection -> collection.notContains(new CSet<>(), List.of(1)));
    verify(collection -> collection.notContains(new CSet<>(), List.of(1), "CollectionExpectationTest ::> notContains "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    verify(collection -> collection.notContainsAll(new CList<>(1, 2, 3), List.of(1, 4)));
    verify(collection -> collection.notContainsAll(new CList<>(1, 2, 3), List.of(1, 4), "CollectionExpectationTest ::> notContainsAll "));
    verify(collection -> collection.notContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4)));
    verify(collection -> collection.notContainsAll(new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), "CollectionExpectationTest ::> notContainsAll "));
    verify(collection -> collection.notContainsAll(new CSet<>(), List.of(1)));
    verify(collection -> collection.notContainsAll(new CSet<>(), List.of(1), "CollectionExpectationTest ::> notContainsAll "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    verify(collection -> collection.notContainsAll(new CList<>(1, 2, 3), List.of(1, 2, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    verify(collection -> collection.notContainsAll(new CList<>(1, 2, 3), null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    verify(collection -> collection.notContainsAll(new CSet<>(), new CSet<>()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    verify(collection -> collection.notContains(new CList<>(1, 2, 3), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    verify(collection -> collection.notContains(new CSet<>(1, 2, 3), null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    verify(collection -> collection.notContains(List.of(1), 1));
  }

  public abstract void verify(Consumer<CCollectionVerification> action);
}
