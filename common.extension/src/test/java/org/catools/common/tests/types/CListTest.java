package org.catools.common.tests.types;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

public class CListTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAdd1() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.add("D");
    CVerify.String.equals(strings.toString(), "A, B, C, D", "CListTest ::> add");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAdd2() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.add(2, "D");
    CVerify.String.equals(strings.toString(), "A, B, D, C", "CListTest ::> add");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddAll1() {
    CList<String> strings = CList.of("C");
    strings.addAll(CList.of("B", "A"));
    CVerify.String.equals(strings.toString(), "C, B, A", "CListTest ::> addAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddAll2() {
    CList<String> strings = CList.of("C");
    strings.addAll(0, CList.of("B", "A"));
    CVerify.String.equals(strings.toString(), "B, A, C", "CListTest ::> addAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClear() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.clear();
    CVerify.Collection.isEmpty(strings, "CListTest ::> clear");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.Bool.isTrue(strings.contains("A"), "CListTest ::> contains");
    CVerify.Bool.isFalse(strings.contains(null), "CListTest ::> contains");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.Bool.isTrue(strings.containsAll(strings), "CListTest ::> containsAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testForEach() {
    CList<String> strings = CList.of("A", "B", "C");
    CList<String> strings2 = CList.of();
    strings.forEach(s -> strings2.add(s));
    CVerify.Collection.equals(strings, strings2, "CListTest ::> forEach");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGet() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.get(0), "A", "CListTest ::> get");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetItems() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.Collection.equals(strings._get(), strings, "CListTest ::> getValue");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIndexOf() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.Int.equals(strings.indexOf("A"), 0, "CListTest ::> indexOf");
    CVerify.Int.equals(strings.indexOf("Z"), -1, "CListTest ::> indexOf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerify.Bool.isTrue(CList.of().isEmpty(), "CListTest ::> isEmpty");
    CVerify.Bool.isFalse(CList.of("A").isEmpty(), "CListTest ::> isEmpty");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIterator() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.iterator().next(), "A", "CListTest ::> equals");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testJoin() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        CList.of("A", "B", "B", "C").join(1, 3), "BB", "CCollectionTest ::> join");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testJoin1() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        CList.of("A", "B", "B", "C").join(0, 2, "*"), "A*B", "CCollectionTest ::> join");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLastIndexOf() {
    CList<String> strings = CList.of("A", "B", "C", "A");
    CVerify.Int.equals(strings.lastIndexOf("A"), 3, "CListTest ::> lastIndexOf");
    CVerify.Int.equals(strings.lastIndexOf("Z"), -1, "CListTest ::> lastIndexOf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testListIterator() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.listIterator().next(), "A", "CListTest ::> listIterator");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testListIterator1() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.listIterator(1).next(), "B", "CListTest ::> listIterator");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testParallelStream() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.parallelStream().findFirst().get(), "A", "CListTest ::> stream");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove1() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.remove("A");
    CVerify.Collection.equals(strings, CList.of("B", "C"), "CListTest ::> remove");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove2() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.remove(0);
    CVerify.Collection.equals(strings, CList.of("B", "C"), "CListTest ::> remove");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveAll() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.removeAll(CList.of("B", "A"));
    CVerify.Collection.equals(strings, CList.of("C"), "CListTest ::> removeAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveAll_N() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.removeAll(CList.of("Z", "A"));
    CVerify.Collection.equals(strings, CList.of("B", "C"), "CListTest ::> Negative  removeAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIf() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.removeIf(s -> s.equals("C"));
    CVerify.Collection.equals(strings, CList.of("A", "B"), "CListTest ::> removeIf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIf_N() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.removeIf(s -> s.equals("Z"));
    CVerify.Collection.equals(strings, CList.of("A", "B", "C"), "CListTest ::> Negative  removeIf");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetainAll() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.retainAll(CList.of("B", "A"));
    CVerify.Collection.equals(strings, CList.of("A", "B"), "CListTest ::> retainAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetainAll_N() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.retainAll(CList.of("Z", "A"));
    CVerify.Collection.equals(strings, CList.of("A"), "CListTest ::> Negative  retainAll");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSet2() {
    CList<String> strings = CList.of("A", "B", "C");
    CList<String> strings2 = CList.of(strings.stream());
    CVerify.Collection.equals(strings, strings2, "CListTest ::> set");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSet3() {
    CList<String> strings = CList.of("A", "B", "C");
    strings.set(0, "Z");
    CVerify.Collection.equals(strings, CList.of("Z", "B", "C"), "CListTest ::> set");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSize() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.Int.equals(strings.size(), 3, "CListTest ::> size");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSpliterator() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.Long.equals(strings.spliterator().estimateSize(), 3L, "CListTest ::> spliterator");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSpliterator_N() {
    CList<String> strings = CList.of();
    CVerify.Long.equals(
        strings.spliterator().estimateSize(), 0L, "CListTest ::> Negative  spliterator");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStream() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.stream().findFirst().get(), "A", "CListTest ::> stream");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubList() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.Collection.equals(strings.subList(1, 2), CList.of("B"), "CListTest ::> subList");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToArray() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.toArray()[0].toString(), "A", "CListTest ::> toArray");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToArray1() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.toArray(new String[3])[0], "A", "CListTest ::> toArray");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToString() {
    CList<String> strings = CList.of("A", "B", "C");
    CVerify.String.equals(strings.toString(), "A, B, C", "CListTest ::> toString");
  }
}
