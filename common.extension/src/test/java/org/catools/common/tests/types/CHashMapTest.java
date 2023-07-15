package org.catools.common.tests.types;

import org.catools.common.collections.CHashMap;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CHashMapTest extends BaseCMapTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void cLinkedMapTest1() {
    CHashMap<String, String> map = CHashMap.of(getStringLinkedMap1());
    map.verifyEquals(getStringLinkedMap1(), "Constructor works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void cLinkedMapTest2() {
    CHashMap<String, String> map = CHashMap.of(3);
    map.putAll(getStringLinkedMap1());
    map.verifyEquals(getStringLinkedMap1(), "Constructor works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void cLinkedMapTest3() {
    CHashMap<String, String> map = CHashMap.of(3, 1);
    map.putAll(getStringLinkedMap1());
    map.verifyEquals(getStringLinkedMap1(), "Constructor works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClone() {
    CHashMap<String, String> map = getStringLinkedMap1();
    CHashMap<String, String> map2 = (CHashMap<String, String>) map.clone();
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

  @Override
  protected CHashMap<String, String> getStringLinkedMap1() {
    CHashMap<String, String> stringCHashMap = new CHashMap<>();
    stringCHashMap.put("C", "3");
    stringCHashMap.put("A", "1");
    stringCHashMap.put("B", "2");
    return stringCHashMap;
  }
}
