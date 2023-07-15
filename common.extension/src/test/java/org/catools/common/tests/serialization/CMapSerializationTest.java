package org.catools.common.tests.serialization;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CJsonUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CMapSerializationTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMap() {
    Map<String, String> a = new HashMap<>();
    a.put("A", "FA");
    a.put("B", "FB");
    a.put("C", "FC");
    CHashMap<String, String> read = CJsonUtil.read(CJsonUtil.toString(a), CHashMap.class);
    read.verifyContainsAll(a, "(De)serialization worked with CMap.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testHashMap() {
    CHashMap<String, String> a = new CHashMap<>();
    a.put("A", "FA");
    a.put("B", "FB");
    a.put("C", "FC");
    CHashMap<String, String> read = CJsonUtil.read(CJsonUtil.toString(a), CHashMap.class);
    read.verifyContainsAll(a, "(De)serialization worked with CMap.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCLinkedMap() {
    CLinkedMap<String, String> a = new CLinkedMap<>();
    a.put("A", "FA");
    a.put("B", "FB");
    a.put("C", "FC");
    CLinkedMap<String, String> read = CJsonUtil.read(CJsonUtil.toString(a), CLinkedMap.class);
    read.verifyContainsAll(a, "(De)serialization worked with CMap.");
  }
}
