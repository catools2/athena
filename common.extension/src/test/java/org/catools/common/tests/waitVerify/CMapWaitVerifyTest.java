package org.catools.common.tests.waitVerify;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.verify.interfaces.waitVerify.CMapWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Map;

public class CMapWaitVerifyTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    toWaitVerify(toMap(1, 2, 3)).verifyContains("1", 1);
    toWaitVerify(toMap(1, 2, 3)).verifyContains(toEntity(2));
    toWaitVerify(toMap(1, 2, 3)).verifyContains("3", 3);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    toWaitVerify(toMap(1, 2, 3)).verifyContainsAll(toMap(1, 3));
    toWaitVerify(toMap(1, 2, 3)).verifyContainsAll(toMap());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyContainsAll(toMap(1, 23));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    toWaitVerify(toMap(1, 2, 3)).verifyContainsAll(toMap(1, 2, 3, 4));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    toWaitVerify(toMap()).verifyContainsAll(toMap(1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    toWaitVerify(toMap()).verifyContainsAll(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    toWaitVerify(toMap(1, 2, 3)).verifyContainsNone(toMap(4, 5, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    toWaitVerify(toMap(1, 2, 3)).verifyContainsNone(toMap(3, 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    toWaitVerify(toMap(1, 2, 3)).verifyContainsNone(toMap());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyContains("5", 5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    toWaitVerify(toMap(1, 2, 3)).verifyContains("5", 5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    toWaitVerify(toMap(1, 2, 3)).verifyContains("5", 5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    toWaitVerify(toMap(1, 2, 3)).verifyEmptyOrContains("1", 1);
    toWaitVerify(toMap()).verifyEmptyOrContains("5", 5);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyEmptyOrContains("6", 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_Null() {
    toWaitVerify(null).verifyEmptyOrContains("6", 6);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    toWaitVerify(toMap(1, 2, 3)).verifyEmptyOrNotContains(toEntity(5));
    toWaitVerify(toMap()).verifyEmptyOrNotContains("1", 1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyEmptyOrNotContains("3", 3);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    toWaitVerify(null).verifyEmptyOrNotContains("0", 0);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContainsWithEntry() {
    toWaitVerify(toMap(1, 2, 3)).verifyEmptyOrContains(Map.entry("1", 1));
    toWaitVerify(toMap()).verifyEmptyOrContains(Map.entry("5", 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContainsWithEntry_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyEmptyOrContains(Map.entry("6", 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeEquals() {
    toWaitVerify(toMap(1, 2, 3)).verifySizeEquals(3);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testWaitSizeEquals_N() {
    toWaitVerify(toMap(1, 2, 3)).verifySizeEquals(2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsGreaterThan() {
    toWaitVerify(toMap(1, 2, 3)).verifySizeIsGreaterThan(2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testWaitSizeIsGreaterThan_N() {
    toWaitVerify(toMap(1, 2, 3)).verifySizeIsGreaterThan(3);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsLessThan() {
    toWaitVerify(toMap(1, 2, 3)).verifySizeIsLessThan(4);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testWaitSizeIsLessThan_N() {
    toWaitVerify(toMap(1, 2, 3)).verifySizeIsLessThan(2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    toWaitVerify(toMap(1, 2, 3)).verifyEquals(toMap(1, 2, 3));
    toWaitVerify(toMap()).verifyEquals(toMap());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyEquals(toMap(1, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    toWaitVerify(toMap(1, 2, 3)).verifyEquals(toMap(3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    toWaitVerify(toMap(1)).verifyEquals(toMap(1, 2, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    toWaitVerify(toMap(1)).verifyEquals(toMap());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    toWaitVerify(toMap()).verifyIsEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    toWaitVerify(toMap(1)).verifyIsEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    toWaitVerify(toMap(2)).verifyIsEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    toWaitVerify(toMap(3)).verifyIsEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    toWaitVerify(toMap(1)).verifyIsNotEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    toWaitVerify(toMap()).verifyIsNotEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    toWaitVerify(toMap()).verifyIsNotEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    toWaitVerify(toMap()).verifyIsNotEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    toWaitVerify(toMap(1, 2, 3)).verifyNotContains("4", 4);
    toWaitVerify(toMap(1)).verifyNotContains(toEntity(2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    toWaitVerify(toMap(1, 2, 3)).verifyNotContainsAll(toMap(1, 4));
    toWaitVerify(toMap(1, 2, 3)).verifyNotContainsAll(toMap(1, 2, 3, 4));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    toWaitVerify(toMap(1)).verifyNotContainsAll(toMap());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyNotContainsAll(toMap(1, 2, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    toWaitVerify(toMap(1, 2, 3)).verifyNotContainsAll(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    toWaitVerify(toMap(1, 2, 3)).verifyNotContains("1", 1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    toWaitVerify(toMap(1, 2, 3)).verifyNotContains(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    toWaitVerify(toMap(1)).verifyNotContains("1", 1);
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

  private CMapWaitVerify<String, Integer> toWaitVerify(Map<String, Integer> val) {
    return () -> val;
  }
}
