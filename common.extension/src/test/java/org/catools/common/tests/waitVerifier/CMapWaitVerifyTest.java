package org.catools.common.tests.waitVerifier;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CMapWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Map;

public class CMapWaitVerifyTest extends CBaseWaitVerifyTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContains(verifier, "1", 1, 1));
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContains(verifier, toEntity(2)));
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContains(verifier, "3", 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsAll(verifier, toMap(1, 3), 1));
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsAll(verifier, toMap()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsAll(verifier, toMap(1, 23)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsAll(verifier, toMap(1, 2, 3, 4)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    verify(verifier -> toWaitVerifier(toMap()).verifyContainsAll(verifier, toMap(1)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    verify(verifier -> toWaitVerifier(toMap()).verifyContainsAll(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsNone(verifier, toMap(4, 5, 6)));
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsNone(verifier, toMap(4, 5, 6), 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsNone(verifier, toMap(3, 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContainsNone(verifier, toMap()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContains(verifier, "5", 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContains(verifier, "5", 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyContains(verifier, "5", 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEmptyOrContains(verifier, "1", 1, 1));
    verify(verifier -> toWaitVerifier(toMap()).verifyEmptyOrContains(verifier, "5", 5));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEmptyOrContains(verifier, "6", 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_Null() {
    verify(verifier -> toWaitVerifier(null).verifyEmptyOrContains(verifier, "6", 6));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEmptyOrNotContains(verifier, toEntity(5), 1));
    verify(verifier -> toWaitVerifier(toMap()).verifyEmptyOrNotContains(verifier, "1", 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEmptyOrNotContains(verifier, "3", 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    verify(verifier -> toWaitVerifier(null).verifyEmptyOrNotContains(verifier, "0", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContainsWithEntry() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEmptyOrContains(verifier, Map.entry("1", 1), 1));
    verify(verifier -> toWaitVerifier(toMap()).verifyEmptyOrContains(verifier, Map.entry("5", 5)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContainsWithEntry_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEmptyOrContains(verifier, Map.entry("6", 6)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeEquals() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifySizeEquals(verifier, 3, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testWaitSizeEquals_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifySizeEquals(verifier, 2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsGreaterThan() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifySizeIsGreaterThan(verifier, 2, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testWaitSizeIsGreaterThan_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifySizeIsGreaterThan(verifier, 3));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitSizeIsLessThan() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifySizeIsLessThan(verifier, 4, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testWaitSizeIsLessThan_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifySizeIsLessThan(verifier, 2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEquals(verifier, toMap(1, 2, 3), 1));
    verify(verifier -> toWaitVerifier(toMap()).verifyEquals(verifier, toMap()));
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEquals(verifier, toMap(1, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyEquals(verifier, toMap(3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify(verifier -> toWaitVerifier(toMap(1)).verifyEquals(verifier, toMap(1, 2, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    verify(verifier -> toWaitVerifier(toMap(1)).verifyEquals(verifier, toMap()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(verifier -> toWaitVerifier(toMap()).verifyIsEmpty(verifier));
    verify(verifier -> toWaitVerifier(toMap()).verifyIsEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    verify(verifier -> toWaitVerifier(toMap(1)).verifyIsEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    verify(verifier -> toWaitVerifier(toMap(2)).verifyIsEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    verify(verifier -> toWaitVerifier(toMap(3)).verifyIsEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(verifier -> toWaitVerifier(toMap(1)).verifyIsNotEmpty(verifier));
    verify(verifier -> toWaitVerifier(toMap(1)).verifyIsNotEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    verify(verifier -> toWaitVerifier(toMap()).verifyIsNotEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    verify(verifier -> toWaitVerifier(toMap()).verifyIsNotEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    verify(verifier -> toWaitVerifier(toMap()).verifyIsNotEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyNotContains(verifier, "4", 4, 1));
    verify(verifier -> toWaitVerifier(toMap(1)).verifyNotContains(verifier, toEntity(2)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyNotContainsAll(verifier, toMap(1, 4), 1));
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyNotContainsAll(verifier, toMap(1, 2, 3, 4)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    verify(verifier -> toWaitVerifier(toMap(1)).verifyNotContainsAll(verifier, toMap()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyNotContainsAll(verifier, toMap(1, 2, 3)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyNotContainsAll(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyNotContains(verifier, "1", 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    verify(verifier -> toWaitVerifier(toMap(1, 2, 3)).verifyNotContains(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    verify(verifier -> toWaitVerifier(toMap(1)).verifyNotContains(verifier, "1", 1));
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

  private CMapWaitVerifier<String, Integer> toWaitVerifier(Map<String, Integer> val) {
    return () -> val;
  }
}
