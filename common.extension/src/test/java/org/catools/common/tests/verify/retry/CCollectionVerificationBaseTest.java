package org.catools.common.tests.verify.retry;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.hard.CCollectionVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.function.Consumer;

public abstract class CCollectionVerificationBaseTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testContains() {
    verify(collection -> collection.contains(new CList<>(1, 2, 3), 1, "%s#%s", getParams()));
    verify(collection -> collection.contains(Arrays.asList(1, 2, 3), 2, "%s#%s", getParams()));
    verify(collection -> collection.contains(new CSet<>(1, 2, 3), 3, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testContainsAll() {
    verify(
        collection ->
            collection.containsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsAll(new CList<>(1, 2, 3), new CSet<>(1, 2), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsAll(new CSet<>(1, 2, 3), Arrays.asList(2, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsAll(new CSet<>(1, 2, 3), new CSet<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsAll_N() {
    verify(
        collection ->
            collection.containsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 23), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsAll_N2() {
    verify(
        collection ->
            collection.containsAll(
                new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsAll_N3() {
    verify(
        collection -> collection.containsAll(new CList<>(), new CSet<>(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsAll_N4() {
    verify(collection -> collection.containsAll(new CList<>(), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testContainsNone() {
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), Arrays.asList(4, 5, 6), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), new CSet<>(4, 5, 6), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsNone(
                new CSet<>(1, 2, 3), Arrays.asList(4, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsNone_N() {
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), Arrays.asList(1, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsNone_N2() {
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), new CSet<>(2, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsNone_N3() {
    verify(
        collection ->
            collection.containsNone(
                new CSet<>(1, 2, 3), Arrays.asList(3, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsNone_N4() {
    verify(
        collection ->
            collection.containsNone(new CSet<>(1, 2, 3), new CSet<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContains_N() {
    verify(collection -> collection.contains(new CList<>(1, 2, 3), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContains_N2() {
    verify(collection -> collection.contains(Arrays.asList(1, 2, 3), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContains_N3() {
    verify(collection -> collection.contains(new CSet<>(1, 2, 3), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEmptyOrContains() {
    verify(collection -> collection.emptyOrContains(new CList<>(1, 2, 3), 1, "%s#%s", getParams()));
    verify(collection -> collection.emptyOrContains(new CSet<>(), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEmptyOrContains_N() {
    verify(collection -> collection.emptyOrContains(new CList<>(1, 2, 3), 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEmptyOrContains_Null() {
    verify(collection -> collection.emptyOrContains(null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEmptyOrNotContains() {
    verify(
        collection -> collection.emptyOrNotContains(new CList<>(1, 2, 3), 5, "%s#%s", getParams()));
    verify(collection -> collection.emptyOrNotContains(new CSet<>(), 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEmptyOrNotContains_N() {
    verify(
        collection -> collection.emptyOrNotContains(new CList<>(1, 2, 3), 3, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEmptyOrNotContains_Null() {
    verify(collection -> collection.emptyOrNotContains(null, 0, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals() {
    verify(
        collection ->
            collection.equals(new CList<>(1, 2, 3), Arrays.asList(1, 2, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.equals(new CList<>(1, 2, 3), new CSet<>(1, 2, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.equals(new CSet<>(1, 2, 3), Arrays.asList(1, 2, 3), "%s#%s", getParams()));
    verify(collection -> collection.equals(new CSet<>(), Arrays.asList(), "%s#%s", getParams()));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N() {
    verify(
        collection ->
            collection.equals(new CList<>(1, 2, 3), Arrays.asList(1, 3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N2() {
    verify(
        collection -> collection.equals(new CList<>(1, 2, 3), new CSet<>(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N3() {
    verify(
        collection ->
            collection.equals(new CSet<>(1, 2, 3), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_N4() {
    verify(collection -> collection.equals(new CSet<>(), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmpty() {
    verify(collection -> collection.isEmpty(new CList<>(), "%s#%s", getParams()));
    verify(collection -> collection.isEmpty(new CSet<>(), "%s#%s", getParams()));
    verify(collection -> collection.isEmpty(Arrays.asList(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmpty_N() {
    verify(collection -> collection.isEmpty(new CList<>(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmpty_N2() {
    verify(collection -> collection.isEmpty(new CSet<>(2), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmpty_N3() {
    verify(collection -> collection.isEmpty(Arrays.asList(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotEmpty() {
    verify(collection -> collection.isNotEmpty(new CList<>(1), "%s#%s", getParams()));
    verify(collection -> collection.isNotEmpty(new CSet<>(2), "%s#%s", getParams()));
    verify(collection -> collection.isNotEmpty(Arrays.asList(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotEmpty_N() {
    verify(collection -> collection.isNotEmpty(new CList<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotEmpty_N2() {
    verify(collection -> collection.isNotEmpty(Arrays.asList(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotEmpty_N3() {
    verify(collection -> collection.isNotEmpty(Arrays.asList(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotContains() {
    verify(collection -> collection.notContains(new CList<>(1, 2, 3), 4, "%s#%s", getParams()));
    verify(collection -> collection.notContains(Arrays.asList(1), 2, "%s#%s", getParams()));
    verify(
        collection -> collection.notContains(new CSet<>(), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotContainsAll() {
    verify(
        collection ->
            collection.notContainsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 4), "%s#%s", getParams()));
    verify(
        collection ->
            collection.notContainsAll(
                new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), "%s#%s", getParams()));
    verify(
        collection ->
            collection.notContainsAll(new CSet<>(), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContainsAll_N() {
    verify(
        collection ->
            collection.notContainsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 2, 3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContainsAll_N2() {
    verify(
        collection -> collection.notContainsAll(new CList<>(1, 2, 3), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContainsAll_N3() {
    verify(
        collection -> collection.notContainsAll(new CSet<>(), new CSet<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContains_N() {
    verify(collection -> collection.notContains(new CList<>(1, 2, 3), 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContains_N2() {
    verify(collection -> collection.notContains(new CSet<>(1, 2, 3), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContains_N3() {
    verify(collection -> collection.notContains(Arrays.asList(1), 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(collection -> collection.contains(new CList<>(1, 2, 3), 1, "%s#%s", getParams()));
    verify(collection -> collection.contains(Arrays.asList(1, 2, 3), 2, "%s#%s", getParams()));
    verify(collection -> collection.contains(new CSet<>(1, 2, 3), 3, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    verify(
        collection ->
            collection.containsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsAll(new CList<>(1, 2, 3), new CSet<>(1, 2), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsAll(new CSet<>(1, 2, 3), Arrays.asList(2, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsAll(new CSet<>(1, 2, 3), new CSet<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    verify(
        collection ->
            collection.containsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 23), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    verify(
        collection ->
            collection.containsAll(
                new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    verify(
        collection -> collection.containsAll(new CList<>(), new CSet<>(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    verify(collection -> collection.containsAll(new CList<>(), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), Arrays.asList(4, 5, 6), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), new CSet<>(4, 5, 6), "%s#%s", getParams()));
    verify(
        collection ->
            collection.containsNone(
                new CSet<>(1, 2, 3), Arrays.asList(4, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), Arrays.asList(1, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    verify(
        collection ->
            collection.containsNone(
                new CList<>(1, 2, 3), new CSet<>(2, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    verify(
        collection ->
            collection.containsNone(
                new CSet<>(1, 2, 3), Arrays.asList(3, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    verify(
        collection ->
            collection.containsNone(new CSet<>(1, 2, 3), new CSet<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    verify(collection -> collection.contains(new CList<>(1, 2, 3), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    verify(collection -> collection.contains(Arrays.asList(1, 2, 3), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    verify(collection -> collection.contains(new CSet<>(1, 2, 3), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    verify(collection -> collection.emptyOrContains(new CList<>(1, 2, 3), 1, "%s#%s", getParams()));
    verify(collection -> collection.emptyOrContains(new CSet<>(), 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    verify(collection -> collection.emptyOrContains(new CList<>(1, 2, 3), 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    verify(collection -> collection.emptyOrContains(null, 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    verify(
        collection -> collection.emptyOrNotContains(new CList<>(1, 2, 3), 5, "%s#%s", getParams()));
    verify(collection -> collection.emptyOrNotContains(new CSet<>(), 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    verify(
        collection -> collection.emptyOrNotContains(new CList<>(1, 2, 3), 3, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    verify(collection -> collection.emptyOrNotContains(null, 0, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(
        collection ->
            collection.equals(new CList<>(1, 2, 3), Arrays.asList(1, 2, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.equals(new CList<>(1, 2, 3), new CSet<>(1, 2, 3), "%s#%s", getParams()));
    verify(
        collection ->
            collection.equals(new CSet<>(1, 2, 3), Arrays.asList(1, 2, 3), "%s#%s", getParams()));
    verify(collection -> collection.equals(new CSet<>(), Arrays.asList(), "%s#%s", getParams()));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(
        collection ->
            collection.equals(new CList<>(1, 2, 3), Arrays.asList(1, 3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(
        collection -> collection.equals(new CList<>(1, 2, 3), new CSet<>(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(
        collection ->
            collection.equals(new CSet<>(1, 2, 3), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    verify(collection -> collection.equals(new CSet<>(), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(collection -> collection.isEmpty(new CList<>(), "%s#%s", getParams()));
    verify(collection -> collection.isEmpty(new CSet<>(), "%s#%s", getParams()));
    verify(collection -> collection.isEmpty(Arrays.asList(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    verify(collection -> collection.isEmpty(new CList<>(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    verify(collection -> collection.isEmpty(new CSet<>(2), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    verify(collection -> collection.isEmpty(Arrays.asList(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(collection -> collection.isNotEmpty(new CList<>(1), "%s#%s", getParams()));
    verify(collection -> collection.isNotEmpty(new CSet<>(2), "%s#%s", getParams()));
    verify(collection -> collection.isNotEmpty(Arrays.asList(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    verify(collection -> collection.isNotEmpty(new CList<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    verify(collection -> collection.isNotEmpty(Arrays.asList(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    verify(collection -> collection.isNotEmpty(Arrays.asList(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(collection -> collection.notContains(new CList<>(1, 2, 3), 4, "%s#%s", getParams()));
    verify(collection -> collection.notContains(Arrays.asList(1), 2, "%s#%s", getParams()));
    verify(
        collection -> collection.notContains(new CSet<>(), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    verify(
        collection ->
            collection.notContainsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 4), "%s#%s", getParams()));
    verify(
        collection ->
            collection.notContainsAll(
                new CList<>(1, 2, 3), new CSet<>(1, 2, 3, 4), "%s#%s", getParams()));
    verify(
        collection ->
            collection.notContainsAll(new CSet<>(), Arrays.asList(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    verify(
        collection ->
            collection.notContainsAll(
                new CList<>(1, 2, 3), Arrays.asList(1, 2, 3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    verify(
        collection -> collection.notContainsAll(new CList<>(1, 2, 3), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    verify(
        collection -> collection.notContainsAll(new CSet<>(), new CSet<>(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    verify(collection -> collection.notContains(new CList<>(1, 2, 3), 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    verify(collection -> collection.notContains(new CSet<>(1, 2, 3), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    verify(collection -> collection.notContains(Arrays.asList(1), 1, "%s#%s", getParams()));
  }

  public abstract void verify(Consumer<CCollectionVerification> action);
}
