package org.catools.common.tests.verify.retry;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.verify.hard.CMapVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.function.Consumer;

public abstract class CMapVerificationBaseTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(collection -> collection.contains(toMap(1, 2, 3), "1", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    verify(collection -> collection.containsAll(toMap(1, 2, 3), toMap(1, 3), "%s#%s", getParams()));
    verify(collection -> collection.containsAll(toMap(1, 2, 3), toMap(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    verify(
        collection -> collection.containsAll(toMap(1, 2, 3), toMap(1, 23), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    verify(
        collection ->
            collection.containsAll(toMap(1, 2, 3), toMap(1, 2, 3, 4), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    verify(collection -> collection.containsAll(toMap(), toMap(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    verify(collection -> collection.containsAll(toMap(), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    verify(
        collection ->
            collection.containsNone(toMap(1, 2, 3), toMap(4, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    verify(
        collection ->
            collection.containsNone(toMap(1, 2, 3), toMap(2, 5, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    verify(
        collection -> collection.containsNone(toMap(1, 2, 3), toMap(3, 6), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    verify(collection -> collection.containsNone(toMap(1, 2, 3), toMap(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    verify(collection -> collection.contains(toMap(1, 2, 3), "5", 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    verify(collection -> collection.contains(toMap(1, 2, 3), "5", 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    verify(collection -> collection.emptyOrContains(toMap(1, 2, 3), "1", 1, "%s#%s", getParams()));
    verify(collection -> collection.emptyOrContains(toMap(), "5", 5, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    verify(collection -> collection.emptyOrContains(toMap(1, 2, 3), "6", 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_Null() {
    verify(collection -> collection.emptyOrContains(null, "6", 6, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    verify(
        collection -> collection.emptyOrNotContains(toMap(1, 2, 3), "5", 5, "%s#%s", getParams()));
    verify(collection -> collection.emptyOrNotContains(toMap(), "1", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    verify(
        collection -> collection.emptyOrNotContains(toMap(1, 2, 3), "3", 3, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    verify(collection -> collection.emptyOrNotContains(null, "0", 0, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(collection -> collection.equals(toMap(1, 2, 3), toMap(1, 2, 3), "%s#%s", getParams()));
    verify(collection -> collection.equals(toMap(), toMap(), "%s#%s", getParams()));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(collection -> collection.equals(toMap(1, 2, 3), toMap(1, 3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(collection -> collection.equals(toMap(1, 2, 3), toMap(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(collection -> collection.equals(toMap(1, 2, 3), toMap(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    verify(collection -> collection.equals(toMap(), toMap(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(collection -> collection.isEmpty(toMap(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    verify(collection -> collection.isEmpty(toMap(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    verify(collection -> collection.isEmpty(toMap(2), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    verify(collection -> collection.isEmpty(toMap(3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(collection -> collection.isNotEmpty(toMap(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    verify(collection -> collection.isNotEmpty(toMap(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    verify(collection -> collection.isNotEmpty(toMap(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    verify(collection -> collection.isNotEmpty(toMap(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(collection -> collection.notContains(toMap(1, 2, 3), "4", 2, "%s#%s", getParams()));
    verify(collection -> collection.notContains(toMap(1, 2, 3), "2", 3, "%s#%s", getParams()));
    verify(
        collection ->
            collection.notContains(toMap(1, 2, 3), Map.entry("4", 2), "%s#%s", getParams()));
    verify(
        collection ->
            collection.notContains(toMap(1, 2, 3), Map.entry("2", 3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    verify(
        collection -> collection.notContainsAll(toMap(1, 2, 3), toMap(1, 4), "%s#%s", getParams()));
    verify(
        collection ->
            collection.notContainsAll(toMap(1, 2, 3), toMap(1, 2, 3, 4), "%s#%s", getParams()));
    verify(collection -> collection.notContainsAll(toMap(), toMap(1), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    verify(
        collection ->
            collection.notContainsAll(toMap(1, 2, 3), toMap(1, 2, 3), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    verify(collection -> collection.notContainsAll(toMap(1, 2, 3), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    verify(collection -> collection.notContainsAll(toMap(), toMap(), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    verify(collection -> collection.notContains(toMap(1, 2, 3), "1", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    verify(collection -> collection.notContains(toMap(1, 2, 3), null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    verify(collection -> collection.notContains(toMap(1), toEntity(1), "%s#%s", getParams()));
  }

  public Map.Entry<String, Integer> toEntity(Integer item) {
    return Map.entry(item + "", item);
  }

  public CMap<String, Integer> toMap(Integer... list) {
    if (list == null) {
      return null;
    }
    CMap<String, Integer> map = new CHashMap<>();
    for (Integer k : list) {
      map.put(k.toString(), k);
    }
    return map;
  }

  public abstract void verify(Consumer<CMapVerification> action);
}
