package org.catools.common.tests.types;

import org.catools.common.collections.CLinkedMap;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Map;

public class CLinkedMapTest extends BaseCMapTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void cLinkedMapTest1() {
    CLinkedMap<String, String> map = CLinkedMap.of(getStringLinkedMap1());
    map.verifyEquals(getStringLinkedMap1(), "Constructor works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void cLinkedMapTest2() {
    CLinkedMap<String, String> map = CLinkedMap.of(3);
    map.putAll(getStringLinkedMap1());
    map.verifyEquals(getStringLinkedMap1(), "Constructor works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void cLinkedMapTest3() {
    CLinkedMap<String, String> map = CLinkedMap.of(3, 1);
    map.putAll(getStringLinkedMap1());
    map.verifyEquals(getStringLinkedMap1(), "Constructor works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void cLinkedMapTest4() {
    CLinkedMap<String, String> map = CLinkedMap.of(3, 1, true);
    map.putAll(getStringLinkedMap1());
    map.verifyEquals(getStringLinkedMap1(), "Constructor works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClone() {
    CLinkedMap<String, String> map = getStringLinkedMap1();
    CLinkedMap<String, String> map2 = (CLinkedMap<String, String>) map.clone();
    map2.remove("A");
    CVerify.Int.equals(map.size(), 3, "original map did not changed");
    CVerify.String.equals(map.get("A"), "1", "original map did not changed");
    CVerify.String.equals(map.get("B"), "2", "original map did not changed");
    CVerify.String.equals(map.get("C"), "3", "original map did not changed");

    CVerify.Int.equals(map2.size(), 2, "cloned map changed");
    CVerify.Bool.isFalse(map2.containsKey("A"), "cloned map does not have A entity");
    CVerify.String.equals(map2.get("B"), "2", "cloned map did not changed untouched record changed");
    CVerify.String.equals(map2.get("C"), "3", "cloned map did not changed untouched record changed");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSortByKey() {
    CLinkedMap<String, String> stringStringCLinkedMap =
        getStringLinkedMap1().getSortedMap(Comparator.comparing(Map.Entry::getKey));
    CVerify.String.equals(
        stringStringCLinkedMap.asSet().getFirst().getKey(),
        "A",
        "CLinkedMapTest1 ::> testEntrySet ::> sortByKey");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSortByValue() {
    CLinkedMap<String, String> stringStringCLinkedMap =
        getStringLinkedMap1().getSortedMap(Comparator.comparing(Map.Entry::getValue));
    CVerify.String.equals(
        stringStringCLinkedMap.asSet().getFirst().getValue(),
        "1",
        "CLinkedMapTest1 ::> testEntrySet ::> sortByValue");
  }

  @Override
  protected CLinkedMap<String, String> getStringLinkedMap1() {
    CLinkedMap<String, String> stringCLinkedMap = new CLinkedMap<>();
    stringCLinkedMap.put("C", "3");
    stringCLinkedMap.put("A", "1");
    stringCLinkedMap.put("B", "2");
    return stringCLinkedMap;
  }
}
