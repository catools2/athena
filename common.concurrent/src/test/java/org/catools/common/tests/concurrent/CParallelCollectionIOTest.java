package org.catools.common.tests.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.concurrent.CParallelCollectionIO;
import org.catools.common.concurrent.exceptions.CThreadTimeoutException;
import org.catools.common.utils.CSleeper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CParallelCollectionIOTest {

  @Test
  public void testRun() throws Throwable {
    CParallelCollectionIO<Integer> pt = new CParallelCollectionIO<>("P1", 2, 2);
    List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
    pt.setInputExecutor(eof -> {
      eof.set(true);
      return List.of(integers.remove(0));
    });
    pt.setOutputExecutor((eof, idx) -> log.trace(idx + ""));
    pt.run();
    Assert.assertEquals(integers.size(), 2, "Only 2 records has been read from main list");
  }

  @Test(expectedExceptions = CThreadTimeoutException.class)
  public void testRunTimeout() throws Throwable {
    CParallelCollectionIO<Integer> pt = new CParallelCollectionIO<>("P1", 2, 2);
    List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
    pt.setInputExecutor(eof -> {
      eof.set(true);
      return List.of(integers.remove(0));
    });
    pt.setOutputExecutor((eof, idx) -> CSleeper.sleepTightInSeconds(idx));
    pt.run(1L, TimeUnit.SECONDS);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testRunInputExceptionAfterEof() throws Throwable {
    CParallelCollectionIO<Integer> pt = new CParallelCollectionIO<>("P1", 2, 1);
    List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
    pt.setInputExecutor(eof -> {
      eof.set(true);
      Objects.requireNonNull(null);
      return List.of(integers.remove(0));
    });
    pt.setOutputExecutor((eof, idx) -> log.trace(idx + ""));
    pt.run();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testRunInputExceptionWithoutEof() throws Throwable {
    CParallelCollectionIO<Integer> pt = new CParallelCollectionIO<>("P1", 2, 1);
    List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
    pt.setInputExecutor(eof -> {
      Objects.requireNonNull(null);
      return List.of(integers.remove(0));
    });
    pt.setOutputExecutor((eof, idx) -> log.trace(idx + ""));
    pt.run();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testRunOutputException() throws Throwable {
    CParallelCollectionIO<Integer> pt = new CParallelCollectionIO<>("P1", 4, 1);
    List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
    pt.setInputExecutor(eof -> {
      eof.set(true);
      return List.of(integers.remove(0));
    });
    pt.setOutputExecutor((eof, idx) -> Objects.requireNonNull(null));
    pt.run();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testTestRun2Threads() throws Throwable {
    CParallelCollectionIO<Integer> pt = new CParallelCollectionIO<>("P1", 2, 2);
    List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
    pt.setInputExecutor(eof -> {
      eof.set(integers.isEmpty());
      return integers.isEmpty() ? Collections.emptyList() : List.of(integers.remove(0));
    });
    pt.setOutputExecutor((eof, idx) -> log.trace(idx + ""));
    pt.run();
    Assert.assertEquals(integers.size(), 0, "All records has been read from main list");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testTestRun4Threads() throws Throwable {
    CParallelCollectionIO<Integer> pt = new CParallelCollectionIO<>("P1", 2, 1);
    List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
    pt.setInputExecutor(eof -> {
      eof.set(integers.isEmpty());
      return integers.isEmpty() ? Collections.emptyList() : List.of(integers.remove(0));
    });
    pt.setOutputExecutor((eof, idx) -> log.trace(idx + ""));
    pt.run();
    Assert.assertEquals(integers.size(), 0, "All records has been read from main list");
  }
}