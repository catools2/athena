package org.catools.common.tests.types.interfaces;

import com.google.common.collect.ImmutableMap;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CMapVerifierTest extends CBaseUnitTest {

  private static CMap<String, String> getStringLinkedMap1() {
    CLinkedMap<String, String> stringCLinkedMap = new CLinkedMap<>();
    stringCLinkedMap.put("C", "3");
    stringCLinkedMap.put("A", "1");
    stringCLinkedMap.put("B", "2");
    return stringCLinkedMap;
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContains() {
    getStringLinkedMap1().verifyContains("A", "1", "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsAll() {
    getStringLinkedMap1().verifyContainsAll(getStringLinkedMap1()._get(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsAll_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.containsAll(getStringLinkedMap1(), getStringLinkedMap1()._get(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsAll_CVerifier_NotAll() {
    CMap<String, String> stringLinkedMap1 = getStringLinkedMap1();
    stringLinkedMap1._get().remove("A");
    CVerifier verifier = new CVerifier();
    verifier.Map.containsAll(stringLinkedMap1, getStringLinkedMap1()._get(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsAll_NotAll() {
    CMap<String, String> stringLinkedMap1 = getStringLinkedMap1();
    stringLinkedMap1._get().remove("A");
    stringLinkedMap1.verifyContainsAll(getStringLinkedMap1()._get(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsNone() {
    getStringLinkedMap1().verifyContainsNone(ImmutableMap.of("Z", "2", "Y", "5"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsNone_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.containsNone(getStringLinkedMap1(), ImmutableMap.of("Z", "2", "Y", "5"), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsNone_CVerifier_Contains_One() {
    CVerifier verifier = new CVerifier();
    verifier.Map.containsNone(getStringLinkedMap1(), ImmutableMap.of("A", "1", "Y", "5"), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsNone_N() {
    getStringLinkedMap1().verifyContainsNone(ImmutableMap.of("A", "1", "Y", "5"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContains_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.contains(getStringLinkedMap1(), "A", "1", "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContains_CVerifier_NotContains() {
    CVerifier verifier = new CVerifier();
    verifier.Map.contains(getStringLinkedMap1(), "A", "2", "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContains_N() {
    getStringLinkedMap1().verifyContains("A", "2", "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrContains2() {
    getStringLinkedMap1().verifyEmptyOrContains("A", "1", "%s#%s", getParams());
    new CHashMap<String, String>().verifyEmptyOrContains("A", "2", "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrContains_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.emptyOrContains(getStringLinkedMap1(), "A", "1", "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrNotContains() {
    getStringLinkedMap1().verifyEmptyOrNotContains("A", "2", "%s#%s", getParams());
    new CHashMap<String, String>().verifyEmptyOrNotContains("A", "1", "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrNotContains_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.emptyOrNotContains(getStringLinkedMap1(), "A", "2", "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEmptyOrNotContains_CVerifier_Contains() {
    CVerifier verifier = new CVerifier();
    verifier.Map.emptyOrNotContains(getStringLinkedMap1(), "A", "1", "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEmptyOrNotContains_Contains() {
    getStringLinkedMap1().verifyEmptyOrNotContains("A", "1", "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEquals1() {
    CMap<String, String> stringLinkedMap1 = getStringLinkedMap1();
    stringLinkedMap1.verifyEquals(stringLinkedMap1._get(), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEquals_CVerifier() {
    CVerifier verifier = new CVerifier();
    CMap<String, String> stringLinkedMap1 = getStringLinkedMap1();
    verifier.Map.equals(stringLinkedMap1, stringLinkedMap1._get(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsEmpty() {
    new CLinkedMap<String, String>().verifyIsEmpty("%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsEmpty_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.isEmpty(new CLinkedMap<String, String>(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyIsEmpty_NotEmpty() {
    CVerifier verifier = new CVerifier();
    verifier.Map.isEmpty(getStringLinkedMap1(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsNotEmpty() {
    getStringLinkedMap1().verifyIsNotEmpty("%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsNotEmpty_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.isNotEmpty(getStringLinkedMap1(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyIsNotEmpty_CVerifier_Empty() {
    CVerifier verifier = new CVerifier();
    verifier.Map.isNotEmpty(new CLinkedMap<String, String>(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContains() {
    getStringLinkedMap1().verifyNotContains("A", "2", "%s#%s", getParams());
    getStringLinkedMap1().verifyNotContains("Z", "1", "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContainsAll() {
    getStringLinkedMap1().verifyNotContainsAll(ImmutableMap.of("A", "2"), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContainsAll_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.notContainsAll(getStringLinkedMap1(), ImmutableMap.of("A", "2", "B", "1"), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyNotContainsAll_CVerifier_ContainsAll() {
    CVerifier verifier = new CVerifier();
    verifier.Map.notContainsAll(getStringLinkedMap1(), getStringLinkedMap1()._get(), "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContainsKey() {
    getStringLinkedMap1().verifyNotContains("Z", "1", "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContainsKey_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.notContains(getStringLinkedMap1(), "Z", "1", "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContains_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.notContains(getStringLinkedMap1(), "A", "2", "%s#%s", getParams());
    verifier.Map.notContains(getStringLinkedMap1(), "Z", "1", "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeEquals() {
    getStringLinkedMap1().verifySizeEquals(3, "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeEquals_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.sizeEquals(getStringLinkedMap1(), 3, "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifySizeEquals_CVerifier_N() {
    CVerifier verifier = new CVerifier();
    verifier.Map.sizeEquals(getStringLinkedMap1(), 1, "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifySizeEquals_N() {
    getStringLinkedMap1().verifySizeEquals(1, "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsGreaterThan() {
    getStringLinkedMap1().verifySizeIsGreaterThan(2, "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsGreaterThan1() {
    CVerifier verifier = new CVerifier();
    verifier.Map.sizeIsGreaterThan(getStringLinkedMap1(), 2, "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifySizeIsGreaterThan1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Map.sizeIsGreaterThan(getStringLinkedMap1(), 10, "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifySizeIsGreaterThan_N() {
    getStringLinkedMap1().verifySizeIsGreaterThan(10, "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsLessThan() {
    getStringLinkedMap1().verifySizeIsLessThan(4, "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsLessThan_CVerifier() {
    CVerifier verifier = new CVerifier();
    verifier.Map.sizeIsLessThan(getStringLinkedMap1(), 4, "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifySizeIsLessThan_CVerifier_N() {
    CVerifier verifier = new CVerifier();
    verifier.Map.sizeIsLessThan(getStringLinkedMap1(), 3, "%s#%s", getParams());
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifySizeIsLessThan_N() {
    getStringLinkedMap1().verifySizeIsLessThan(3, "%s#%s", getParams());
  }
}
