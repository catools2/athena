package org.catools.common.tests.utils;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CRetry;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class CRetryTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNot1() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfNot(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        i -> i >= 10,
        20,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfNot1_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfNot(
        integer -> {
          if (flag.get() == 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        i -> i >= 10,
        20,
        -1,
        null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNot2() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfNot(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        i -> i >= 10,
        20,
        1,
        null);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfNot2_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfNot(
        integer -> {
          if (flag.get() == 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        i -> i >= 10,
        20,
        1,
        null,
        true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIf1() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIf(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        i -> i < 10,
        20,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIf1_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIf(
        integer -> {
          if (flag.get() == 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        i -> i < 10,
        20,
        -1,
        null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIf2() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIf(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        i -> i < 10,
        20,
        1,
        null);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIf2_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIf(
        integer -> {
          if (flag.get() == 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        i -> i < 10,
        20,
        1,
        null,
        true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIf3() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIf(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        i -> i < 20,
        10,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 11, "If condition works");
    CVerify.Int.equals(counter.get(), 11, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIf4() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIf(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        i -> i < 20,
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 11, "If condition works");
    CVerify.Int.equals(counter.get(), 11, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIf5() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIf(
        integer -> {
          counter.set(integer);
          if (flag.incrementAndGet() == 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.get();
        },
        i -> i < 20,
        10,
        1,
        null,
        false);
    CVerify.Int.equals(flag.get(), 11, "If condition works");
    CVerify.Int.equals(counter.get(), 11, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfEmpty1() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.add("A");
          }
          return list;
        },
        20,
        1,
        null,
        true);
    CVerify.Int.equals(list.size(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfEmpty1_N() {
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          if (integer < 5) {
            CVerify.Int.equals(list.size(), 10, "throwing exception");
          }
          return list;
        },
        5,
        1,
        null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfEmpty2() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.add("A");
          }
          return list;
        },
        20,
        1,
        null);
    CVerify.Int.equals(list.size(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfEmpty2_N() {
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          if (integer < 5) {
            CVerify.Int.equals(list.size(), 10, "throwing exception");
          }
          return list;
        },
        5,
        1,
        null,
        true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfEmpty3() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.add("A");
          }
          return list;
        },
        3,
        1,
        null,
        true);
    CVerify.Int.equals(list.size(), 0, "If condition works");
    CVerify.Int.equals(counter.get(), 4, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfEmpty4() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.add("A");
          }
          return list;
        },
        3,
        1,
        null);
    CVerify.Int.equals(list.size(), 0, "If condition works");
    CVerify.Int.equals(counter.get(), 4, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfEmpty5() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.add("A");
          }
          return list.isEmpty() ? null : list;
        },
        3,
        1,
        null,
        true);
    CVerify.Int.equals(list.size(), 0, "If condition works");
    CVerify.Int.equals(counter.get(), 4, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfEmpty6() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>();
    CRetry.retryIfEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.add("A");
          }
          return list.isEmpty() ? null : list;
        },
        3,
        1,
        null);
    CVerify.Int.equals(list.size(), 0, "If condition works");
    CVerify.Int.equals(counter.get(), 4, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse1() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 9;
        },
        20,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfFalse1_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          if (flag.get() < 5) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet() < 9;
        },
        5,
        1,
        null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse2() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 9;
        },
        20,
        1,
        null);
    CVerify.Int.equals(flag.get(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfFalse2_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          if (flag.get() < 5) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet() < 9;
        },
        5,
        1,
        null,
        true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse3() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 9;
        },
        20,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse4() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 9;
        },
        20,
        1,
        null);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse5() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 20;
        },
        10,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 11, "If condition works");
    CVerify.Int.equals(counter.get(), 11, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse6() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 20;
        },
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 11, "If condition works");
    CVerify.Int.equals(counter.get(), 11, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse7() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          if (flag.incrementAndGet() < 5) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.get() < 9;
        },
        20,
        1,
        null,
        false);
    CVerify.Int.equals(flag.get(), 5, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse8() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 5 ? null : flag.get();
        },
        10,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 5, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfFalse9() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfFalse(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 5 ? null : flag.get();
        },
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 5, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNotEmpty1() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>("A");
    CRetry.retryIfNotEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.remove("A");
          }
          return list;
        },
        20,
        1,
        null,
        true);
    CVerify.Int.equals(list.size(), 0, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfNotEmpty1_N() {
    CList<String> list = new CList<>("A");
    CRetry.retryIfNotEmpty(
        integer -> {
          if (integer < 5) {
            CVerify.Int.equals(list.size(), 10, "throwing exception");
          }
          return list;
        },
        5,
        1,
        null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNotEmpty2() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>("A");
    CRetry.retryIfNotEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.remove("A");
          }
          return list;
        },
        20,
        1,
        null);
    CVerify.Int.equals(list.size(), 0, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfNotEmpty2_N() {
    CList<String> list = new CList<>("A");
    CRetry.retryIfNotEmpty(
        integer -> {
          if (integer < 5) {
            CVerify.Int.equals(list.size(), 10, "throwing exception");
          }
          return list;
        },
        5,
        1,
        null,
        true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNotEmpty3() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>("A");
    CRetry.retryIfNotEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.remove("A");
          }
          return list;
        },
        3,
        1,
        null,
        true);
    CVerify.Int.equals(list.size(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 4, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNotEmpty4() {
    AtomicInteger counter = new AtomicInteger();
    CList<String> list = new CList<>("A");
    CRetry.retryIfNotEmpty(
        integer -> {
          counter.set(integer);
          if (integer == 5) {
            list.remove("A");
          }
          return list;
        },
        3,
        1,
        null);
    CVerify.Int.equals(list.size(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 4, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNotEmpty5() {
    AtomicInteger counter = new AtomicInteger();
    CRetry.retryIfNotEmpty(
        integer -> {
          counter.set(integer);
          return null;
        },
        3,
        1,
        null,
        true);
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfNotEmpty6() {
    AtomicInteger counter = new AtomicInteger();
    CRetry.retryIfNotEmpty(
        integer -> {
          counter.set(integer);
          return null;
        },
        3,
        1,
        null,
        true);
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue1() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 9;
        },
        20,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfTrue1_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          if (flag.get() < 5) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet() < 9;
        },
        5,
        1,
        null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue2() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 9;
        },
        20,
        1,
        null);
    CVerify.Int.equals(flag.get(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryIfTrue2_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          if (flag.get() < 5) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet() < 9;
        },
        5,
        1,
        null,
        true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue3() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 10;
        },
        20,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue4() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 10;
        },
        20,
        1,
        null);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 10, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue5() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 20;
        },
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 11, "If condition works");
    CVerify.Int.equals(counter.get(), 11, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue6() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() < 20;
        },
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 11, "If condition works");
    CVerify.Int.equals(counter.get(), 11, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue7() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          if (flag.incrementAndGet() < 5) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.get() > 10;
        },
        20,
        1,
        null,
        false);
    CVerify.Int.equals(flag.get(), 5, "If condition works");
    CVerify.Int.equals(counter.get(), 5, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue8() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 5 ? null : flag.get() < 7;
        },
        10,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 6, "If condition works");
    CVerify.Int.equals(counter.get(), 6, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryIfTrue9() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retryIfTrue(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet() > 5 ? null : flag.get() < 7;
        },
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 6, "If condition works");
    CVerify.Int.equals(counter.get(), 6, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryOnError1() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retry(
        integer -> {
          counter.set(integer);
          if (flag.incrementAndGet() < 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        20,
        1,
        null,
        true);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 9, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryOnError1_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retry(
        integer -> {
          if (flag.incrementAndGet() < 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        5,
        1,
        null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryOnError2() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retry(
        integer -> {
          counter.set(integer);
          if (flag.incrementAndGet() < 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        20,
        1,
        null);
    CVerify.Int.equals(flag.get(), 10, "If condition works");
    CVerify.Int.equals(counter.get(), 9, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testRetryOnError2_N() {
    AtomicInteger flag = new AtomicInteger();
    CRetry.retry(
        integer -> {
          if (flag.incrementAndGet() < 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.incrementAndGet();
        },
        5,
        1,
        null,
        true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryOnError3() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retry(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryOnError4() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retry(
        integer -> {
          counter.set(integer);
          return flag.incrementAndGet();
        },
        10,
        1,
        null);
    CVerify.Int.equals(flag.get(), 1, "If condition works");
    CVerify.Int.equals(counter.get(), 1, "If condition works");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRetryOnError5() {
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger flag = new AtomicInteger();
    CRetry.retry(
        integer -> {
          counter.set(integer);
          if (flag.incrementAndGet() < 9) {
            CVerify.Int.equals(flag.get(), 10, "throwing exception");
          }
          return flag.get();
        },
        20,
        1,
        null,
        false);
    CVerify.Int.equals(flag.get(), 9, "If condition works");
    CVerify.Int.equals(counter.get(), 9, "If condition works");
  }
}
