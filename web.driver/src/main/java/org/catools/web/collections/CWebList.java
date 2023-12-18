package org.catools.web.collections;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CList;
import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.catools.web.drivers.CDriver.DEFAULT_TIMEOUT;

@Getter
@Slf4j
public class CWebList<E extends CWebElement<? extends CDriver>> implements CWebIterable<E> {
  protected final String name;
  protected final String locator;
  protected final BiFunction<Integer, String, E> controlBuilder;
  protected final int waitForFirstElementInSecond;
  protected final int waitForOtherElementInSecond;

  public CWebList(String name, String xpathLocator, BiFunction<Integer, String, E> controlBuilder) {
    this(name, xpathLocator, DEFAULT_TIMEOUT, controlBuilder);
  }

  public CWebList(
      String name,
      String xpathLocator,
      int waitSecs,
      BiFunction<Integer, String, E> controlBuilder) {
    this(name, xpathLocator, waitSecs, 0, controlBuilder);
  }

  public CWebList(
      String name,
      String xpathLocator,
      int waitForFirstElementInSecond,
      int waitForOtherElementInSecond,
      BiFunction<Integer, String, E> controlBuilder) {
    super();
    this.name = name;
    this.locator = xpathLocator;
    this.controlBuilder = controlBuilder;
    this.waitForFirstElementInSecond = Math.max(1, waitForFirstElementInSecond);
    this.waitForOtherElementInSecond = Math.max(1, waitForOtherElementInSecond);
  }

  public void forEach(Consumer<E> function, int firstWaitSecs) {
    forEach(function, firstWaitSecs, this.waitForOtherElementInSecond);
  }

  public void forEach(Consumer<E> function, int firstWaitSecs, int waitSecs) {
    int idx = 0;
    while (true) {
      E control = getRecord(idx++);
      if (control == null || !control.Present.waitIsTrue(idx == 0 ? firstWaitSecs : waitSecs)) {
        break;
      }
      function.accept(control);
    }
  }

  public void onFirstMatch(Predicate<E> condition, Consumer<E> action) {
    onMatch(condition, action, true);
  }

  public void onFirstMatch(Predicate<E> condition, Consumer<E> action, int firstWaitSecs) {
    onMatch(condition, action, true, firstWaitSecs);
  }

  public void onFirstMatch(
      Predicate<E> condition, Consumer<E> action, int firstWaitSecs, int waitSecs) {
    onMatch(condition, action, true, firstWaitSecs, waitSecs);
  }

  public void onMatch(Predicate<E> condition, Consumer<E> action, boolean stopAfterFirstMatch) {
    onMatch(condition, action, stopAfterFirstMatch, DEFAULT_TIMEOUT);
  }

  public void onMatch(
      Predicate<E> condition, Consumer<E> action, boolean stopAfterFirstMatch, int firstWaitSecs) {
    onMatch(condition, action, stopAfterFirstMatch, firstWaitSecs, 1);
  }

  public void onMatch(
      Predicate<E> condition,
      Consumer<E> action,
      boolean stopAfterFirstMatch,
      int firstWaitSecs,
      int waitSecs) {
    int idx = 0;
    while (true) {
      E control = getRecord(idx++);
      if (control == null
          || (!control.Enabled.waitIsTrue(idx == 0 ? firstWaitSecs : waitSecs)
          && !control.Present.waitIsTrue(1))) {
        break;
      }
      if (condition.test(control)) {
        action.accept(control);
        if (stopAfterFirstMatch) {
          break;
        }
      }
    }
  }

  public CList<E> getElements() {
    return getElements(DEFAULT_TIMEOUT);
  }

  public CList<E> getElements(int firstWaitSecs) {
    return getElements(firstWaitSecs, 1);
  }

  public CList<E> getElements(int firstWaitSecs, int waitSecs) {
    CList<E> output = new CList<>();
    forEach(output::add, firstWaitSecs, waitSecs);
    return output;
  }

  public CList<String> getTexts() {
    return getTexts(DEFAULT_TIMEOUT);
  }

  public CList<String> getTexts(int firstWaitSecs) {
    return getTexts(firstWaitSecs, 1);
  }

  public CList<String> getTexts(int firstWaitSecs, int waitSecs) {
    CList<String> output = new CList<>();
    forEach(t -> output.add(t.Text._get()), firstWaitSecs, waitSecs);
    return output;
  }

  public boolean testAll(Predicate<E> predicate) {
    return testAll(predicate, DEFAULT_TIMEOUT);
  }

  public boolean testAll(Predicate<E> predicate, int firstWaitSecs) {
    return testAll(predicate, firstWaitSecs, 1);
  }

  public boolean testAll(Predicate<E> predicate, int firstWaitSecs, int waitSecs) {
    Map<String, Boolean> output = new HashMap<>();
    forEach(
        t -> {
          output.put(t.getName(), predicate.test(t));
        },
        firstWaitSecs,
        waitSecs);
    return output.keySet().stream().allMatch(output::get);
  }

  public boolean testAny(Predicate<E> predicate) {
    return testAny(predicate, DEFAULT_TIMEOUT);
  }

  public boolean testAny(Predicate<E> predicate, int firstWaitSecs) {
    return testAny(predicate, firstWaitSecs, 1);
  }

  public boolean testAny(Predicate<E> predicate, int firstWaitSecs, int waitSecs) {
    Map<String, Boolean> output = new HashMap<>();
    onFirstMatch(predicate, t -> output.put(t.getName(), true), firstWaitSecs, waitSecs);
    return output.keySet().stream().filter(output::get).count() == 1;
  }

  public int count() {
    return getElements(DEFAULT_TIMEOUT).size();
  }

  public int count(int firstWaitSecs) {
    return getElements(firstWaitSecs).size();
  }

  public int count(int firstWaitSecs, int waitSecs) {
    return getElements(firstWaitSecs, waitSecs).size();
  }

  @Override
  public boolean hasRecord(int idx) {
    if (idx == 0) {
      return getRecord(idx).Present.waitIsTrue(waitForFirstElementInSecond, 100);
    }
    return getRecord(idx).Present.waitIsTrue(waitForOtherElementInSecond, 100);
  }

  @Override
  public String getVerifyMessagePrefix() {
    return name;
  }

  @Override
  public boolean isEqual(Iterable<E> expected) {
    return equals(expected);
  }

  @Override
  public E getRecord(int idx) {
    return controlBuilder.apply(idx, String.format("(%s)[%s]", locator, idx + 1));
  }
}
