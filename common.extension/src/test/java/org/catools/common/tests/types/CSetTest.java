package org.catools.common.tests.types;

import org.catools.common.collections.CSet;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CSetTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAdd() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.add("D");
    CVerify.Collection.equals(strings, CSet.of("A", "B", "C", "D"), "CSetTest ::> add");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddAll() {
    CSet<String> strings = CSet.of("C");
    strings.addAll(CSet.of("B", "A"));
    CVerify.Collection.equals(strings, CSet.of("A", "B", "C"), "CSetTest ::> addAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClear() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.clear();
    CVerify.Collection.isEmpty(strings, "CSetTest ::> clear");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.Bool.isTrue(strings.contains("A"), "CSetTest ::> contains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.Bool.isTrue(strings.containsAll(strings), "CSetTest ::> containsAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.Bool.isTrue(
        strings.containsAll(CSet.of("A", "Z")), "CSetTest ::> Negative  containsAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.Bool.isTrue(strings.contains("Z"), "CSetTest ::> Negative  contains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testForEach() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CSet<String> strings2 = CSet.of();
    strings.forEach(s -> strings2.add(s));
    CVerify.Collection.equals(strings, strings2, "CSetTest ::> forEach");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetItems() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.Collection.equals(strings._get(), strings, "CSetTest ::> getValue");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerify.Bool.isTrue(CSet.of().isEmpty(), "CSetTest ::> isEmpty");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    CVerify.Bool.isTrue(CSet.of("A").isEmpty(), "CSetTest ::> Negative  isEmpty");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIterator() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.String.equals(strings.iterator().next(), "A", "CSetTest ::> equals");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testParallelStream() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.String.equals(strings.parallelStream().findFirst().get(), "A", "CSetTest ::> stream");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.remove("A");
    CVerify.Collection.equals(strings, CSet.of("B", "C"), "CSetTest ::> remove");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveAll() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.removeAll(CSet.of("B", "A"));
    CVerify.Collection.equals(strings, CSet.of("C"), "CSetTest ::> removeAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveAll_N() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.removeAll(CSet.of("X", "B"));
    CVerify.Collection.equals(strings, CSet.of("A", "C"), "CSetTest ::> Negative  removeAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIf() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.removeIf(s -> s.equals("C"));
    CVerify.Collection.equals(strings, CSet.of("A", "B"), "CSetTest ::> removeIf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIf_N() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.removeIf(s -> s.equals("Z"));
    CVerify.Collection.equals(strings, CSet.of("A", "B", "C"), "CSetTest ::> Negative  removeIf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove_N() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.remove("Z");
    CVerify.Collection.equals(strings, CSet.of("A", "B", "C"), "CSetTest ::> remove");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetainAll() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.retainAll(CSet.of("B", "A"));
    CVerify.Collection.equals(strings, CSet.of("A", "B"), "CSetTest ::> retainAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetainAll_N() {
    CSet<String> strings = CSet.of("A", "B", "C");
    strings.retainAll(CSet.of("Z", "A"));
    CVerify.Collection.equals(strings, CSet.of("A"), "CSetTest ::> Negative  retainAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSet2() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CSet<String> strings2 = CSet.of(strings.stream());
    CVerify.Collection.equals(strings, strings2, "CSetTest ::> set");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSize() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.Int.equals(strings.size(), 3, "CSetTest ::> size");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSpliterator() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.Long.equals(strings.spliterator().estimateSize(), 3L, "CSetTest ::> spliterator");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSpliterator_N() {
    CSet<String> strings = CSet.of();
    CVerify.Long.equals(
        strings.spliterator().estimateSize(), 0L, "CSetTest ::> Negative  spliterator");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStream() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.String.equals(strings.stream().findFirst().get(), "A", "CSetTest ::> stream");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToArray() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.String.equals(strings.toArray()[0].toString(), "A", "CSetTest ::> toArray");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToArray1() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.String.equals(strings.toArray(new String[3])[0], "A", "CSetTest ::> toArray");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToString() {
    CSet<String> strings = CSet.of("A", "B", "C");
    CVerify.String.equals(strings.toString(), "A, B, C", "CSetTest ::> toString");
  }
}
