package org.catools.common.tests.types;

import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CMapTest extends CBaseUnitTest {

  private static CMap<String, String> getStringLinkedMap1() {
    CLinkedMap<String, String> stringCLinkedMap = new CLinkedMap<>();
    stringCLinkedMap.put("C", "3");
    stringCLinkedMap.put("A", "1");
    stringCLinkedMap.put("B", "2");
    return stringCLinkedMap;
  }

  private static CMap<String, String> getStringLinkedMap2() {
    CLinkedMap<String, String> stringCLinkedMap = new CLinkedMap<>();
    stringCLinkedMap.put("A", "1");
    stringCLinkedMap.put("C", "1");
    stringCLinkedMap.put("B", "2");
    return stringCLinkedMap;
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEntrySet() {
    CVerifier verifier = new CVerifier();
    CSet<java.util.Map.Entry<String, String>> strings = getStringLinkedMap1().asSet();
    strings.verifyContains(
        new HashMap.SimpleEntry<>("A", "1"),
        "CMapTest ::> testEntrySet ::> Entry Set Contains Correct Values");
    strings.verifyContains(
        new HashMap.SimpleEntry<>("B", "2"),
        "CMapTest ::> testEntrySet ::> Entry Set Contains Correct Values");
    strings.verifyContains(
        new HashMap.SimpleEntry<>("C", "3"),
        "CMapTest ::> testEntrySet ::> Entry Set Contains Correct Values");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetAll() {
    CVerify.Collection.containsAll(
        getStringLinkedMap2().getAll((k, v) -> v.equals("1")).keySet(),
        new CList<>("A", "C"),
        "CMapTest ::> testKeySet ::> getAll");
    CVerify.Int.equals(
        new CLinkedMap<String, String>().getAll((k, v) -> v.equals("1")).size(),
        0,
        "CMapTest ::> testKeySet ::> getFirst");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetAllKeys() {
    CVerify.Collection.containsAll(
        getStringLinkedMap1().getAllKeys(k -> new CList<>("1", "2").contains(k)),
        new CList<>("A", "B"),
        "CMapTest ::> testKeySet ::> getAllKeys");
    CVerify.Collection.isEmpty(
        getStringLinkedMap1().getAllKeys(k -> false), "CMapTest ::> testKeySet ::> getAllKeys");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetAllValues() {
    CVerify.Collection.containsAll(
        getStringLinkedMap1().getAllValues(k -> new CList<>("A", "B").contains(k)),
        new CList<>("1", "2"),
        "CMapTest ::> testKeySet ::> getAllValues");
    CVerify.Collection.isEmpty(
        getStringLinkedMap1().getAllValues(k -> false), "CMapTest ::> testKeySet ::> getAllValues");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetAllValuesByKey() {
    CVerifier verifier = new CVerifier();
    CList<String> strings = getStringLinkedMap1().getAllValues(k -> !k.equalsIgnoreCase("B"));
    strings.verifySizeEquals(2, "Size Matched");
    strings.verifyEquals(new CSet<>("1", "3"), "Values Matched");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirst() {
    CVerify.String.equals(
        getStringLinkedMap2().getFirst((k, v) -> v.equals("1")).getKey(),
        "A",
        "CMapTest ::> testKeySet ::> getFirst");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstKeyByValue() {
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValue("1"),
        "A",
        "CMapTest ::> testKeySet ::> getFirstKey");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValue(v -> v.equals("1")),
        "A",
        "CMapTest ::> testKeySet ::> getFirstKey");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstKeyByValueOrElse() {
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValueOrElse("1", "Z"),
        "A",
        "CMapTest ::> testKeySet ::> testGetFirstKeyByValueOrElse");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValueOrElse("false", "Z"),
        "Z",
        "CMapTest ::> testKeySet ::> testGetFirstKeyByValueOrElse");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValueOrElse(k -> k.equals("1"), "Z"),
        "A",
        "CMapTest ::> testKeySet ::> testGetFirstKeyByValueOrElse");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValueOrElse(k -> false, "Z"),
        "Z",
        "CMapTest ::> testKeySet ::> testGetFirstKeyByValueOrElse");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstKeyByValueOrNull() {
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValueOrNull("1"),
        "A",
        "CMapTest ::> testKeySet ::> getFirstKeyByValueOrNull");
    CVerify.Object.isNull(
        getStringLinkedMap1().getFirstKeyByValueOrNull("false"),
        "CMapTest ::> testKeySet ::> getFirstKeyByValueOrNull");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstKeyByValueOrNull(k -> k.equals("1")),
        "A",
        "CMapTest ::> testKeySet ::> getFirstKeyByValueOrNull");
    CVerify.Object.isNull(
        getStringLinkedMap1().getFirstKeyByValueOrNull(k -> false),
        "CMapTest ::> testKeySet ::> getFirstKeyByValueOrNull");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = NoSuchElementException.class)
  public void testGetFirstKeyByValue_N() {
    getStringLinkedMap1().getFirstKeyByValue(v -> false);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstValueByKey() {
    CVerify.String.equals(
        getStringLinkedMap1().getFirstValueByKey("A"),
        "1",
        "CMapTest ::> testKeySet ::> getFirstValue");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstValueByKey(k -> k.equals("A")),
        "1",
        "CMapTest ::> testKeySet ::> getFirstValue");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstValueByKeyOrElse() {
    CVerify.String.equals(
        getStringLinkedMap1().getFirstValueByKeyOrElse("A", "Z"),
        "1",
        "CMapTest ::> testKeySet ::> getFirstValueByKeyOrNull");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstValueByKeyOrElse("false", "Z"),
        "Z",
        "CMapTest ::> testKeySet ::> getFirstValueByKeyOrNull");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstValueByKeyOrElse(k -> k.equals("A"), "Z"),
        "1",
        "CMapTest ::> testKeySet ::> getFirstValueByKeyOrNull");
    CVerify.String.equals(
        getStringLinkedMap1().getFirstValueByKeyOrElse(k -> false, "Z"),
        "Z",
        "CMapTest ::> testKeySet ::> getFirstValueByKeyOrNull");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstValueByKeyOrNull() {
    CVerify.String.equals(
        getStringLinkedMap1().getFirstValueByKeyOrNull(k -> k.equals("A")),
        "1",
        "CMapTest ::> testKeySet ::> getFirstValueByKeyOrNull");
    CVerify.Object.isNull(
        getStringLinkedMap1().getFirstValueByKeyOrNull(k -> false),
        "CMapTest ::> testKeySet ::> getFirstValueByKeyOrNull");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = NoSuchElementException.class)
  public void testGetFirstValueByKey_N() {
    getStringLinkedMap1().getFirstValueByKey(k -> false);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = NoSuchElementException.class)
  public void testGetFirst_N() {
    getStringLinkedMap2().getFirst((k, v) -> v.equals("Z"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetSortedMap() {
    String first =
        ((Map.Entry)
            getStringLinkedMap1()
                .getSortedMap((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
                .entrySet()
                .toArray()[0])
            .getKey()
            .toString();
    CVerify.String.equals(first, "C", "CMapTest ::> testKeySet ::> getSortedMap");
  }
}
