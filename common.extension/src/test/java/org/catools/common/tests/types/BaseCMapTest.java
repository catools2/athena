package org.catools.common.tests.types;

import com.google.common.collect.ImmutableMap;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCMapTest<T extends CMap<String, String>> extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClear() {
    T map = getStringLinkedMap1();
    map.clear();
    map.verifyIsEmpty("CLinkedMapTest1 ::> testClear ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompute() {
    T map = getStringLinkedMap1();
    map.compute("A", (k, v) -> k + v);
    CVerify.Int.equals(map.size(), 3, "compute map did not change the size");
    CVerify.String.equals(map.get("A"), "A1", "compute map change the matched entity");
    CVerify.String.equals(map.get("B"), "2", "compute map did not changed the matched entity");
    CVerify.String.equals(map.get("C"), "3", "compute map did not changed the matched entity");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testComputeIfAbsent() {
    T map = getStringLinkedMap1();
    map.computeIfAbsent("Z", s -> s);
    CVerify.Int.equals(map.size(), 4, "computeIfAbsent record from map change the size");
    CVerify.String.equals(map.get("Z"), "Z", "computeIfAbsent record from map worked");
    map.computeIfAbsent("Z", s -> "X");
    CVerify.Int.equals(map.size(), 4, "computeIfAbsent record from map change the size");
    CVerify.String.equals(map.get("Z"), "Z", "computeIfAbsent record from map did noy change anything");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testComputeIfPresent() {
    T map = getStringLinkedMap1();
    map.computeIfPresent("Z", (k, v) -> k);
    CVerify.Int.equals(map.size(), 3, "computeIfAbsent record from map change the size");
    CVerify.Bool.isFalse(map.containsKey("Z"), "computeIfAbsent did not do anything");
    map.computeIfPresent("A", (k, v) -> "X");
    CVerify.Int.equals(map.size(), 3, "computeIfAbsent record from map change the size");
    CVerify.String.equals(map.get("A"), "X", "computeIfAbsent record from map did noy change anything");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsKey() {
    T map = getStringLinkedMap1();
    CVerify.Bool.isTrue(map.containsKey("A"), "containsKey return correct value");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsValue() {
    CVerifier verifier = new CVerifier();
    T map = getStringLinkedMap1();
    verifier.Int.equals(map.size(), map.asSet().size(), "CLinkedMapTest1 ::> testContainsValue ::> Size Matched");
    map.values().forEach(entry -> {
      verifier.Bool.isTrue(map.containsValue(entry), "CLinkedMapTest1 ::> testContainsValue ::> Contains element");
    });
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEntrySet() {
    CVerifier verifier = new CVerifier();
    CSet<Map.Entry<String, String>> strings = getStringLinkedMap1().asSet();
    verifier.Collection.contains(strings, new HashMap.SimpleEntry<>("A", "1"), "CLinkedMapTest1 ::> testEntrySet ::> Entry Set Contains Correct Values");

    verifier.Collection.contains(strings, new HashMap.SimpleEntry<>("B", "2"), "CLinkedMapTest1 ::> testEntrySet ::> Entry Set Contains Correct Values");

    verifier.Collection.contains(strings, new HashMap.SimpleEntry<>("C", "3"), "CLinkedMapTest1 ::> testEntrySet ::> Entry Set Contains Correct Values");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testForEach() {
    T map = getStringLinkedMap1();
    CHashMap<String, String> map2 = new CHashMap<>();
    map.forEach((k, v) -> {
      map2.put(k, v);
    });
    map.verifyEquals(map2, "CLinkedMapTest1 ::> testForEach ::> Size Matched");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGet() {
    CVerifier verifier = new CVerifier();
    T map = getStringLinkedMap1();
    map.keySet().forEach(k -> {
      verifier.Map.contains(map, k, map.get(k), "CLinkedMapTest1 ::> testGet ::> Contains element");
    });
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetOrDefault() {
    CVerifier verifier = new CVerifier();
    T map = getStringLinkedMap1();
    map.keySet().forEach(k -> {
      verifier.Map.contains(map, k, map.getOrDefault(k, "Z"), "CLinkedMapTest1 ::> testGetOrDefault ::> Contains element");
    });
    verifier.Map.contains(map, "A", map.getOrDefault("Z", "1"), "CLinkedMapTest1 ::> testGetOrDefault ::> Contains element");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    T map = getStringLinkedMap1();
    CVerify.Bool.isFalse(map.isEmpty(), "IsEmpty return correct value");
    map.clear();
    CVerify.Bool.isTrue(map.isEmpty(), "IsEmpty return correct value");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMerge() {
    T map = getStringLinkedMap1();
    map.merge("A", "1", (k, v) -> k + v);
    CVerify.Int.equals(map.size(), 3, "merge map did not change the size");
    CVerify.String.equals(map.get("A"), "11", "merge map change the matched entity");
    CVerify.String.equals(map.get("B"), "2", "merge map did not changed the matched entity");
    CVerify.String.equals(map.get("C"), "3", "merge map did not changed the matched entity");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testPut() {
    T map = getStringLinkedMap1();
    map.put("Z", "32");
    CVerify.Int.equals(map.size(), 4, "put changed map size");
    CVerify.Bool.isTrue(map.containsKey("Z"), "put add record to map");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testPutAll() {
    T map = getStringLinkedMap1();
    map.putAll(ImmutableMap.of("X", "30", "Z", "32"));
    CVerify.Int.equals(map.size(), 5, "putAll changed map size");
    CVerify.Bool.isTrue(map.containsKey("Z"), "putAll add record to map");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testPutIfAbsent() {
    T map = getStringLinkedMap1();
    map.putIfAbsent("A", "Z");
    CVerify.Int.equals(map.size(), 3, "map size not changed");
    CVerify.String.equals(map.get("A"), "1", "record value not changed");
    map.putIfAbsent("Z", "2");
    CVerify.Int.equals(map.size(), 4, "map size changed");
    CVerify.String.equals(map.get("Z"), "2", "record value changed");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove() {
    T map = getStringLinkedMap1();
    map.remove("A");
    CVerify.Int.equals(map.size(), 2, "remove changed map size");
    CVerify.Bool.isFalse(map.containsKey("A"), "remove record from map");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove1() {
    T map = getStringLinkedMap1();
    map.remove("A", "1");
    CVerify.Int.equals(map.size(), 2, "remove changed map size");
    CVerify.Bool.isFalse(map.containsKey("A"), "remove record from map");
    map.remove("B", "1");
    CVerify.Int.equals(map.size(), 2, "map size changed");
    CVerify.String.equals(map.get("B"), "2", "record value changed");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplace() {
    T map = getStringLinkedMap1();
    map.replace("A", "1", "2");
    CVerify.Int.equals(map.size(), 3, "replace record from map change the size");
    CVerify.String.equals(map.get("A"), "2", "replace record from map worked");
    map.replace("A", "1", "3");
    CVerify.Int.equals(map.size(), 3, "replace record from map did not change the size");
    CVerify.String.equals(map.get("A"), "2", "replace record from map did not do anything");
    map.replace("Z", "1", "3");
    CVerify.Int.equals(map.size(), 3, "map size not changed");
    CVerify.Bool.isFalse(map.containsKey("Z"), "replace record from map did not do anything");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplace1() {
    T map = getStringLinkedMap1();
    map.replace("A", "2");
    CVerify.Int.equals(map.size(), 3, "replace record from map change the size");
    CVerify.String.equals(map.get("A"), "2", "replace record from map worked");
    map.replace("Z", "1");
    CVerify.Int.equals(map.size(), 3, "map size not changed");
    CVerify.Bool.isFalse(map.containsKey("Z"), "replace record from map did not do anything");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceAll() {
    T map = getStringLinkedMap1();
    map.replaceAll((s, s2) -> s.equals("A") ? "Z" : s2);
    map.verifyContains("B", "2", "B not changed");
    map.verifyContains("C", "3", "C not changed");
    map.verifyContains("A", "Z", "A changed");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSize() {
    T map = getStringLinkedMap1();
    CVerify.Int.equals(map.size(), 3, "Size return correct value");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToString() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(getStringLinkedMap1().toString().replaceAll(System.lineSeparator(), ""), "{  \"A\" : \"1\",  \"B\" : \"2\",  \"C\" : \"3\"}", "CMapTest ::> testToString ");
    verifier.verify();
  }

  protected abstract T getStringLinkedMap1();
}
