package org.catools.common.tests.types;

import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.extensions.verify.CVerifier;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.stream.Collectors;

public class CCollectionTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAdd1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CList<>("A", "B", "C");
    strings.add("C");
    verifier.String.equals(strings.join(), "ABCC", "CCollectionTest ::> add");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAdd2() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.add("C");
    verifier.String.equals(strings.join(), "ABC", "CCollectionTest ::> add");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddAll() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.addAll(new CSet<>("D", "E", "F"));
    verifier.String.equals(strings.join(), "ABCDEF", "CCollectionTest ::> addAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddIf1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B");
    strings.addIf(s -> s.equals("C"), "C");
    verifier.String.equals(strings.join(), "ABC", "CCollectionTest ::> addIf");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAddIf2() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B");
    strings.addIf(s -> !s.equals("C"), "C");
    verifier.String.equals(strings.join(), "AB", "CCollectionTest ::> addIf");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testClear() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.clear();
    verifier.Bool.isTrue(strings.isEmpty(), "CCollectionTest ::> clear");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CVerifier verifier = new CVerifier();
    verifier.Bool.isTrue(new CSet<>("A", "B", "C").contains("B"), "CCollectionTest ::> contains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.Bool.isTrue(
        strings.containsAll(new CSet<>("A", "C")), "CCollectionTest ::> containsAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.Bool.isTrue(
        strings.containsAll(new CSet<>("A", "X")), "CCollectionTest ::> containsAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    CVerifier verifier = new CVerifier();
    verifier.Bool.isTrue(new CSet<>("A", "B", "C").contains("D"), "CCollectionTest ::> contains");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testForEach() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> output = new CList<>();
    new CSet<>("A", "B", "C").forEach(c -> output.add(c));
    verifier.String.equals(output.join(), "ABC", "CCollectionTest ::> forEach");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetAll() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getAll(s -> !s.equals("B")).join(), "AC", "CCollectionTest ::> getAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetAny() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> list1 = new CSet<>("A", "B", "C");
    verifier.Collection.contains(list1, list1.getRandom(), "CCollectionTest ::> testGetAny");
    verifier.Object.isNull(new CSet<>().getRandom(), "CCollectionTest ::> testGetAny");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetAnyAndRemove() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> list1 = new CSet<>("A", "C");
    String val;
    verifier.Collection.notContains(
        list1, (val = list1.getAnyAndRemove()), "CCollectionTest ::> testGetAnyAndRemove");
    verifier.Object.isNotNull(
        val, "CCollectionTest ::> testGetAnyAndRemove returns null if no value present");
    verifier.Int.equals(list1.size(), 1, "CCollectionTest ::> testGetAnyAndRemove");

    verifier.Collection.notContains(
        list1, (val = list1.getAnyAndRemove()), "CCollectionTest ::> testGetAnyAndRemove");
    verifier.Object.isNotNull(
        val, "CCollectionTest ::> testGetAnyAndRemove returns null if no value present");
    verifier.Int.equals(list1.size(), 0, "CCollectionTest ::> testGetAnyAndRemove");

    verifier.Object.isNull(
        list1.getAnyAndRemove(),
        "CCollectionTest ::> testGetAnyAndRemove returns null if no value present");
    verifier.verify();
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGetAny_N1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> list1 = new CSet<>("A", "B", "C");
    verifier.Collection.notContains(list1, list1.getRandom(), "CCollectionTest ::> testGetAny");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testGetAny_N2() {
    CVerifier verifier = new CVerifier();
    verifier.Object.isNotNull(new CSet<>().getRandom(), "CCollectionTest ::> testGetAny");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirst() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(strings.getFirst(), "A", "CCollectionTest ::> getFirst");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirst1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getFirst(s -> s.equals("B")), "B", "CCollectionTest ::> getFirst");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrElse() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(strings.getFirstOrElse("B"), "A", "CCollectionTest ::> getFirstOrElse");

    strings.clear();
    verifier.String.equals(strings.getFirstOrElse("D"), "D", "CCollectionTest ::> getFirstOrElse");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrElse1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getFirstOrElse(s -> s.equals("A"), "B"), "A", "CCollectionTest ::> getFirstOrElse");

    strings.clear();
    verifier.String.equals(
        strings.getFirstOrElse(s -> s.equals("C"), "D"), "D", "CCollectionTest ::> getFirstOrElse");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrElseGet() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getFirstOrElseGet(() -> "B"), "A", "CCollectionTest ::> getFirstOrElse");

    strings.clear();
    verifier.String.equals(
        strings.getFirstOrElseGet(() -> "D"), "D", "CCollectionTest ::> getFirstOrElse");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrElseGet1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getFirstOrElseGet(s -> s.equals("A"), () -> "B"),
        "A",
        "CCollectionTest ::> getFirstOrElse");

    strings.clear();
    verifier.String.equals(
        strings.getFirstOrElseGet(s -> s.equals("C"), () -> "D"),
        "D",
        "CCollectionTest ::> getFirstOrElse");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrNull() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(strings.getFirstOrNull(), "A", "CCollectionTest ::> testGetFirstOrNull");

    strings.clear();
    verifier.Object.isNull(strings.getFirstOrNull(), "CCollectionTest ::> testGetFirstOrNull");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrNull1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getFirstOrNull(s -> s.equals("A")), "A", "CCollectionTest ::> testGetFirstOrNull");

    strings.clear();
    verifier.Object.isNull(
        strings.getFirstOrNull(s -> s.equals("C")), "CCollectionTest ::> testGetFirstOrNull");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrThrow1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getFirstOrThrow(new RuntimeException("B")),
        "A",
        "CCollectionTest ::> getFirstOrThrow");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testGetFirstOrThrow2() {
    new CSet<>().getFirstOrThrow(new RuntimeException(":)"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetFirstOrThrow3() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.String.equals(
        strings.getFirstOrThrow(s -> s.equals("A"), () -> new RuntimeException("B")),
        "A",
        "CCollectionTest ::> getFirstOrThrow");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testGetFirstOrThrow4() {
    new CSet<>("B", "C").getFirstOrThrow(s -> s.equals("A"), () -> new RuntimeException(":)"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testHas() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B");
    verifier.Bool.isTrue(strings.has(s -> s.equals("A")), "CCollectionTest ::> has");
    verifier.Bool.isFalse(strings.has(s -> s.equals("C")), "CCollectionTest ::> has");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testHasNot() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B");
    verifier.Bool.isTrue(strings.hasNot(s -> s.equals("C")), "CCollectionTest ::> hasNot");
    verifier.Bool.isFalse(strings.hasNot(s -> s.equals("A")), "CCollectionTest ::> hasNot");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerifier verifier = new CVerifier();
    verifier.Bool.isTrue(new CSet<>().isEmpty(), "CCollectionTest ::> isEmpty");
    verifier.Bool.isFalse(new CSet<>(1, 2, 3).isEmpty(), "CCollectionTest ::> isEmpty");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    CVerifier verifier = new CVerifier();
    verifier.Bool.isTrue(new CSet<>(1).isNotEmpty(), "CCollectionTest ::> isNotEmpty");
    verifier.Bool.isFalse(new CSet<Integer>().isNotEmpty(), "CCollectionTest ::> isNotEmpty");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIterator() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        new CSet<>("A", "B", "C").iterator().next(), "A", "CCollectionTest ::> iterator");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIterator_N() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        new CSet<>("A", "B", "C").iterator().next(), "B", "CCollectionTest ::> iterator");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testJoin() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        new CList<>("A", "B", "B", "C").join(), "ABBC", "CCollectionTest ::> join");
    verifier.String.equals(new CSet<>("A", "B", "C").join(), "ABC", "CCollectionTest ::> join");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testJoin1() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        new CList<>("A", "B", "B", "C").join("*"), "A*B*B*C", "CCollectionTest ::> join");
    verifier.String.equals(
        new CSet<>("A", "B", "C").join("."), "A.B.C", "CCollectionTest ::> join");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testJoin2() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        new CList<>("A1", "B2", "B3", "C4").join(s -> s.substring(0, 1), "*"),
        "A*B*B*C",
        "CCollectionTest ::> join");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMap() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        ((CCollection<String, Collection<String>>) new CList<>("A", "B"))
            .map(s -> s.toLowerCase())
            .collect(Collectors.joining()),
        "ab",
        "CCollectionTest ::> map");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMapToList() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        ((CCollection<String, Collection<String>>) new CList<>("A", "B")).mapToList(s -> s.toLowerCase()).join(),
        "ab",
        "CCollectionTest ::> mapToList");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMapToSet() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        ((CCollection<String, Collection<String>>) new CList<>("A", "B", "B", "C"))
            .mapToSet(s -> s.toLowerCase())
            .join(),
        "abc",
        "CCollectionTest ::> mapToSet");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testParallelStream() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.Bool.isTrue(strings.parallelStream() != null, "CCollectionTest ::> parallelStream");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testPartition() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> list1 = new CSet<>("A", "B", "C");
    verifier.Int.equals(list1.partition(1).size(), 3, "CCollectionTest ::> testPartition");
    verifier.Int.equals(list1.partition(1).get(0).size(), 1, "CCollectionTest ::> testPartition");
    verifier.Int.equals(list1.partition(3).size(), 1, "CCollectionTest ::> testPartition");
    verifier.Int.equals(list1.partition(3).get(0).size(), 3, "CCollectionTest ::> testPartition");
    verifier.Int.equals(list1.partition(4).get(0).size(), 3, "CCollectionTest ::> testPartition");
    verifier.Int.equals(new CSet<>().partition(4).size(), 0, "CCollectionTest ::> testPartition");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.remove("C");
    verifier.String.equals(strings.join(), "AB", "CCollectionTest ::> remove");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemove2() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.remove("X");
    verifier.String.equals(strings.join(), "ABC", "CCollectionTest ::> remove");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveAll1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.removeAll(new CSet<>("A", "C"));
    verifier.String.equals(strings.join(), "B", "CCollectionTest ::> removeAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveAll2() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.removeAll(new CSet<>("A", "C", "X"));
    verifier.String.equals(strings.join(), "B", "CCollectionTest ::> removeAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIf1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.removeIf(s -> s.equals("B"));
    verifier.String.equals(strings.join(), "AC", "CCollectionTest ::> removeIf");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIf2() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.removeIf(s -> s.equals("X"));
    verifier.String.equals(strings.join(), "ABC", "CCollectionTest ::> removeIf");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetainAll1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.retainAll(new CSet<>("A", "C", "X"));
    verifier.String.equals(strings.join(), "AC", "CCollectionTest ::> retainAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetainAll2() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    strings.retainAll(new CSet<>("A", "C"));
    verifier.String.equals(strings.join(), "AC", "CCollectionTest ::> retainAll");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSize() {
    CVerifier verifier = new CVerifier();
    verifier.Int.equals(new CSet<>("A", "B", "C").size(), 3, "CCollectionTest ::> size");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSpliterator() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.Bool.isTrue(strings.spliterator() != null, "CCollectionTest ::> spliterator");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStream() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> strings = new CSet<>("A", "B", "C");
    verifier.Bool.isTrue(strings.stream() != null, "CCollectionTest ::> stream");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToArray() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> output = new CList<>();
    Object[] objects = new CSet<>("A", "B", "C").toArray();
    for (Object object : objects) {
      output.add((String) object);
    }
    verifier.String.equals(output.join(), "ABC", "CCollectionTest ::> toArray");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToArray1() {
    CVerifier verifier = new CVerifier();
    CCollection<String, Collection<String>> output = new CList<>();
    String[] objects = new CSet<>("A", "B", "C").toArray(new String[3]);
    for (String string : objects) {
      output.add(string);
    }
    verifier.String.equals(output.join(), "ABC", "CCollectionTest ::> toArray");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToList() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        new CSet<>("A", "B", "B").toList().join(), "AB", "CCollectionTest ::> toList");
    verifier.String.equals(
        new CList<>("A", "B", "B").toList().join(), "ABB", "CCollectionTest ::> toList");
    verifier.String.equals(
        new CList<>("A", "B").toList().join(), "AB", "CCollectionTest ::> toList");
    verifier.verify();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToSet() {
    CVerifier verifier = new CVerifier();
    verifier.String.equals(
        new CSet<>("A", "B", "B").toSet().join(), "AB", "CCollectionTest ::> toSet");
    verifier.String.equals(
        new CList<>("A", "B", "B").toSet().join(), "AB", "CCollectionTest ::> toSet");
    verifier.String.equals(new CList<>("A", "B").toSet().join(), "AB", "CCollectionTest ::> toSet");
    verifier.verify();
  }
}
