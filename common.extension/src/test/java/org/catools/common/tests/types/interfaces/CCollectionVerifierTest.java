package org.catools.common.tests.types.interfaces;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CCollectionVerifierTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyHas() {
    new CList<>("A", "B", "D", "C").verifyHas(s -> s.equals("D"), "CCollectionTest ::> verifyContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyHas1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.has(new CList<>("A", "B", "D", "C"), s -> s.equals("D"), "CCollectionTest ::> verifyContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyHas1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.has(new CList<>("A", "B", "D", "C"), s -> s.equals("x"), "CCollectionTest ::> verifyContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContains() {
    new CList<>("A", "B", "D", "C").verifyContains("D", "CCollectionTest ::> verifyContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContains1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.contains(new CList<>("A", "B", "D", "C"), "D", "CCollectionTest ::> verifyContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContains1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.contains(new CList<>("A", "B", "D", "C"), "X", "CCollectionTest ::> verifyContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsAll() {
    new CList<>("A", "B", "D", "C").verifyContainsAll(new CSet<>("A", "B", "B"), "CCollectionTest ::> verifyContainsAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsAll1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.containsAll(new CSet<>("A", "B", "D", "C"), new CList<>("A", "B", "B"), "CCollectionTest ::> verifyContainsAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsAll1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.containsAll(new CSet<>("A", "B", "D", "C"), new CList<>("A", "C", "X"), "CCollectionTest ::> verifyContainsAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsAll_N() {
    new CList<>("A", "B", "D", "C").verifyContainsAll(new CSet<>("A", "B", "X"), "CCollectionTest ::> verifyContainsAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsNone() {
    new CList<>("A", "B", "D", "C").verifyContainsNone(new CSet<>("Z", "X"), "CCollectionTest ::> verifyContainsNone");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyContainsNone1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.containsNone(new CList<>("A", "B", "D", "C"), new CSet<>("Z", "X"), "CCollectionTest ::> verifyContainsNone");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsNone1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.containsNone(new CList<>("A", "B", "D", "C"), new CSet<>("C", "B"), "CCollectionTest ::> verifyContainsNone");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContainsNone_N() {
    new CList<>("A", "B", "D", "C").verifyContainsNone(new CSet<>("A", "B"), "CCollectionTest ::> verifyContainsNone");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyContains_N() {
    new CList<>("A", "B", "D", "C").verifyContains("X", "CCollectionTest ::> verifyContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrContains() {
    new CList<>("A", "B", "D", "C").verifyEmptyOrContains("C", "CCollectionTest ::> verifyEmptyOrContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrContains1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.emptyOrContains(new CList<>("A", "B", "D", "C"), "D", "CCollectionTest ::> verifyEmptyOrContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEmptyOrContains1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.emptyOrContains(new CList<>("A", "B", "D", "C"), "X", "CCollectionTest ::> verifyEmptyOrContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEmptyOrContains2_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.emptyOrContains(new CList<>("A", "B", "D", "C"), null, "CCollectionTest ::> verifyEmptyOrContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEmptyOrContains_N() {
    new CList<>("A", "B", "D", "C").verifyEmptyOrContains("X", "CCollectionTest ::> verifyEmptyOrContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrNotContains() {
    new CList<>("A", "B", "D", "C").verifyEmptyOrNotContains("X", "CCollectionTest ::> verifyEmptyOrNotContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEmptyOrNotContains1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.emptyOrNotContains(new CList<>("A", "B", "D", "C"), "Y", "CCollectionTest ::> verifyEmptyOrNotContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEmptyOrNotContains1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.emptyOrNotContains(new CList<>("A", "B", "D", "C"), "C", "CCollectionTest ::> verifyEmptyOrNotContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEmptyOrNotContains_N() {
    new CList<>("A", "B", "D", "C").verifyEmptyOrNotContains("D", "CCollectionTest ::> verifyEmptyOrNotContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEquals() {
    new CList<>("A", "B", "D", "C").verifyEquals(new CSet<>("A", "B", "C", "D"), "CCollectionTest ::> verifyEquals");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyEquals1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.equals(new CList<>("A", "B", "D", "C"), new CSet<>("A", "B", "C", "D"), "CCollectionTest ::> verifyEquals");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEquals1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.equals(new CList<>("A", "B", "D", "C"), new CSet<>("A", "B", "X", "D"), "CCollectionTest ::> verifyEquals");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyEquals_N() {
    new CList<>("A", "B", "D", "C").verifyEquals(new CSet<>("A", "B", "X", "D"), "CCollectionTest ::> verifyEquals");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsEmpty() {
    new CList<>().verifyIsEmpty("CCollectionTest ::> verifyIsEmpty");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsEmpty1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.isEmpty(new CList<>(), "CCollectionTest ::> verifyIsEmpty");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyIsEmpty1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.isEmpty(new CList<>(1), "CCollectionTest ::> verifyIsEmpty");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyIsEmpty_N() {
    new CList<>(1).verifyIsEmpty("CCollectionTest ::> verifyIsEmpty");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsNotEmpty() {
    new CList<>("A", "B", "D", "C").verifyIsNotEmpty("CCollectionTest ::> verifyIsNotEmpty");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyIsNotEmpty1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.isNotEmpty(new CList<>("A", "B", "D", "C"), "CCollectionTest ::> verifyIsNotEmpty");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyIsNotEmpty1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.isNotEmpty(new CList<>(), "CCollectionTest ::> verifyIsNotEmpty");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyIsNotEmpty_N() {
    new CList<>().verifyIsNotEmpty("CCollectionTest ::> verifyIsNotEmpty");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContains() {
    new CList<>("A", "B", "D", "C").verifyNotContains("Z", "CCollectionTest ::> verifyNotContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContains1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.notContains(new CList<>("A", "B", "D", "C"), "X", "CCollectionTest ::> verifyNotContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyNotContains1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.notContains(new CList<>("A", "B", "D", "C"), "C", "CCollectionTest ::> verifyNotContains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContainsAll() {
    new CList<>("A", "B", "D", "C").verifyNotContainsAll(new CSet<>("A", "B", "X"), "CCollectionTest ::> verifyNotContainsAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifyNotContainsAll1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.notContainsAll(new CSet<>("A", "B", "D", "C"), new CList<>("A", "B", "X"), "CCollectionTest ::> verifyNotContainsAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyNotContainsAll1_N() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.notContainsAll(new CSet<>("A", "B", "D", "C"), new CList<>("A", "B", "C"), "CCollectionTest ::> verifyNotContainsAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyNotContainsAll_N() {
    new CList<>("A", "B", "D", "C").verifyNotContainsAll(new CSet<>("A", "B", "C"), "CCollectionTest ::> verifyNotContainsAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testVerifyNotContains_N() {
    new CList<>("A", "B", "D", "C").verifyNotContains("C", "CCollectionTest ::> verifyNotContains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeEquals() {
    new CList<>("A", "B", "D", "C").verifySizeEquals(4, "CCollectionTest ::> verifySizeEquals");
    new CSet<>("A", "B", "B", "C").verifySizeEquals(3, "CCollectionTest ::> verifySizeEquals");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeEquals1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.verifySizeEquals(new CList<>("A", "B", "D", "C"), 4, "CCollectionTest ::> verifySizeEquals");
    verifier.Collection.verifySizeEquals(new CSet<>("A", "B", "B", "C"), 3, "CCollectionTest ::> verifySizeEquals");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsGreaterThan() {
    new CList<>("A", "B", "D", "C").verifySizeIsGreaterThan(3, "CCollectionTest ::> verifySizeIsGreaterThan");
    new CSet<>("A", "B", "B", "C").verifySizeIsGreaterThan(2, "CCollectionTest ::> verifySizeIsGreaterThan");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsGreaterThan1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.verifySizeIsGreaterThan(new CList<>("A", "B", "D", "C"), 3, "CCollectionTest ::> verifySizeIsGreaterThan");
    verifier.Collection.verifySizeIsGreaterThan(new CSet<>("A", "B", "B", "C"), 2, "CCollectionTest ::> verifySizeIsGreaterThan");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsLessThan() {
    new CList<>("A", "B", "D", "C").verifySizeIsLessThan(5, "CCollectionTest ::> verifySizeIsLessThan");
    new CSet<>("A", "B", "B", "C").verifySizeIsLessThan(4, "CCollectionTest ::> verifySizeIsLessThan");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testVerifySizeIsLessThan1() {
    CVerifier verifier = new CVerifier();
    verifier.Collection.verifySizeIsLessThan(new CList<>("A", "B", "D", "C"), 5, "CCollectionTest ::> verifySizeIsLessThan");
    verifier.Collection.verifySizeIsLessThan(new CSet<>("A", "B", "B", "C"), 4, "CCollectionTest ::> verifySizeIsLessThan");
    verifier.verify();
  }
}
