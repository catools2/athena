package org.catools.web.table;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.functions.CMemoize;
import org.catools.web.collections.CWebIterable;
import org.catools.web.config.CDriverConfigs;
import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;
import org.catools.web.factory.CWebElementFactory;
import org.catools.web.pages.CWebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Quotes;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Getter
@Setter
@Accessors(chain = true)
public abstract class CWebTable<DR extends CDriver, R extends CWebTableRow<DR, ? extends CWebTable<DR, R>>>
    extends CWebElement<DR> implements CWebComponent<DR>, CWebIterable<R> {

  protected String searchCriteriaXpathFormat = "[%d][contains(.,%s)]/ancestor::tr[1]";
  protected String tHeadXpath = "/thead";
  protected String headerRowXpath = "/tr";
  protected String headerCellXpath = "/th";
  protected String tBodyXpath = "/tbody";
  protected String rowXpath = "/tr";
  protected String cellXpath = "/td";
  private String baseXpath;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private CMemoize<CWebTableHeaderInfo<DR>> memoizeHeadersMap = new CMemoize<>(this::readHeaders);

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private final ThreadLocal<CMap<String, String>> searchCriteria = ThreadLocal.withInitial(CHashMap::new);

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private final ThreadLocal<Boolean> readRecordOnIteration = ThreadLocal.withInitial(() -> true);

  public CWebTable(String name, DR driver, String baseXpath) {
    this(name, driver, baseXpath, CDriver.DEFAULT_TIMEOUT);
  }

  public CWebTable(String name, DR driver, String baseXpath, int waitSec) {
    super(name, driver, By.xpath(baseXpath), waitSec);
    this.baseXpath = baseXpath;
    CWebElementFactory.initElements(this);

    Runtime.getRuntime().addShutdownHook(new Thread(searchCriteria::remove));
    Runtime.getRuntime().addShutdownHook(new Thread(readRecordOnIteration::remove));
  }

  @Override
  public abstract R getRecord(int idx);

  @Override
  public boolean hasRecord(int idx) {
    return isDataAvailable() && driver.$(getRowXpath(idx)).isVisible(0);
  }

  public CList<R> getAll(String header, String value) {
    return getAll(ImmutableMap.of(header, value));
  }

  public CList<R> getAll(Map<String, String> searchCriteria) {
    return performActionOnTable(searchCriteria, this::getAll);
  }

  public CList<R> getAll(String header, String value, Predicate<R> predicate) {
    return getAll(ImmutableMap.of(header, value), predicate);
  }

  public CList<R> getAll(Map<String, String> searchCriteria, Predicate<R> predicate) {
    return performActionOnTable(searchCriteria, () -> getAll(predicate));
  }

  public R getAny(String header, String value) {
    return getAny(ImmutableMap.of(header, value));
  }

  public R getAny(Map<String, String> searchCriteria) {
    return performActionOnTable(searchCriteria, this::getRandom);
  }

  public R getFirst(String header, String value) {
    return getFirst(ImmutableMap.of(header, value));
  }

  public R getFirst(Map<String, String> searchCriteria) {
    return performActionOnTable(searchCriteria, this::getFirst);
  }

  public R getFirst(String header, String value, Predicate<R> predicate) {
    return getFirst(ImmutableMap.of(header, value), predicate);
  }

  public R getFirst(Map<String, String> searchCriteria, Predicate<R> predicate) {
    return performActionOnTable(searchCriteria, () -> getFirst(predicate));
  }

  public R getFirstOrElse(String header, String value, R other) {
    return getFirstOrElse(ImmutableMap.of(header, value), other);
  }

  public R getFirstOrElse(Map<String, String> searchCriteria, R other) {
    return performActionOnTable(searchCriteria, () -> getFirstOrElse(other));
  }

  public R getFirstOrElse(String header, String value, Predicate<R> predicate, R other) {
    return getFirstOrElse(ImmutableMap.of(header, value), predicate, other);
  }

  public R getFirstOrElse(Map<String, String> searchCriteria, Predicate<R> predicate, R other) {
    return performActionOnTable(searchCriteria, () -> getFirstOrElse(predicate, other));
  }

  public R getFirstOrElseGet(String header, String value, Supplier<R> other) {
    return getFirstOrElseGet(ImmutableMap.of(header, value), other);
  }

  public R getFirstOrElseGet(Map<String, String> searchCriteria, Supplier<R> other) {
    return performActionOnTable(searchCriteria, () -> getFirstOrElseGet(other));
  }

  public R getFirstOrElseGet(
      String header, String value, Predicate<R> predicate, Supplier<R> other) {
    return getFirstOrElseGet(ImmutableMap.of(header, value), predicate, other);
  }

  public R getFirstOrElseGet(
      Map<String, String> searchCriteria, Predicate<R> predicate, Supplier<R> other) {
    return performActionOnTable(searchCriteria, () -> getFirstOrElseGet(predicate, other));
  }

  public R getFirstOrNull(String header, String value) {
    return getFirstOrNull(ImmutableMap.of(header, value));
  }

  public R getFirstOrNull(Map<String, String> searchCriteria) {
    return performActionOnTable(searchCriteria, this::getFirstOrNull);
  }

  public R getFirstOrNull(String header, String value, Predicate<R> predicate) {
    return getFirstOrNull(ImmutableMap.of(header, value), predicate);
  }

  public R getFirstOrNull(Map<String, String> searchCriteria, Predicate<R> predicate) {
    return performActionOnTable(searchCriteria, () -> getFirstOrNull(predicate));
  }

  public R getFirstOrAny(String header, String value, Predicate<R> predicate) {
    return getFirstOrAny(ImmutableMap.of(header, value), predicate);
  }

  public R getFirstOrAny(Map<String, String> searchCriteria, Predicate<R> predicate) {
    return performActionOnTable(searchCriteria, () -> getFirstOrAny(predicate));
  }

  public R getFirstOrThrow(String header, String value, RuntimeException e) {
    return getFirstOrThrow(ImmutableMap.of(header, value), e);
  }

  public R getFirstOrThrow(Map<String, String> searchCriteria, RuntimeException e) {
    return performActionOnTable(searchCriteria, () -> getFirstOrThrow(e));
  }

  public <X extends RuntimeException> R getFirstOrThrow(
      String header,
      String value,
      Predicate<R> predicate,
      Supplier<? extends X> exceptionSupplier) {
    return getFirstOrThrow(ImmutableMap.of(header, value), predicate, exceptionSupplier);
  }

  public <X extends RuntimeException> R getFirstOrThrow(
      Map<String, String> searchCriteria,
      Predicate<R> predicate,
      Supplier<? extends X> exceptionSupplier) {
    return performActionOnTable(searchCriteria, () -> getFirstOrThrow(predicate, exceptionSupplier));
  }

  public R getFirstOrElse(String header, String value, Predicate<R> predicate, Supplier<R> other) {
    return getFirstOrElse(ImmutableMap.of(header, value), predicate, other);
  }

  public R getFirstOrElse(
      Map<String, String> searchCriteria, Predicate<R> predicate, Supplier<R> other) {
    return performActionOnTable(searchCriteria, () -> getFirstOrElse(predicate, other));
  }

  public CWebElement<DR> getHeader(String headerName) {
    return getHeader(getHeaderIndex(headerName));
  }

  public CWebElement<DR> getHeader(int idx) {
    return new CWebElement<>(
        "Header " + idx,
        driver,
        By.xpath(
            String.format(
                "(%s)[%d]", baseXpath + tHeadXpath + headerRowXpath + headerCellXpath, idx)));
  }

  public Integer getHeaderIndex(String header) {
    return memoizeHeadersMap.get().getHeaderIndex(header);
  }

  public CMap<Integer, String> getHeadersMap() {
    return getHeadersMap(false);
  }

  public CMap<Integer, String> getHeadersMap(boolean reset) {
    if (reset) {
      memoizeHeadersMap.reset();
    }
    return memoizeHeadersMap.get().getHeadersMap();
  }

  public boolean isReadRecordOnIteration() {
    return readRecordOnIteration.get();
  }

  public CMap<Integer, String> getVisibleHeadersMap(boolean reset) {
    if (reset) {
      memoizeHeadersMap.reset();
    }
    return memoizeHeadersMap.get().getVisibleHeadersMap();
  }

  public boolean isDataAvailable() {
    if (CDriverConfigs.waitCompleteReadyStateBeforeEachAction()) {
      driver.waitCompleteReadyState();
    }
    return driver.getElement(
        By.xpath(String.format("(%s)[1]", baseXpath + tBodyXpath + rowXpath)), waitSec)
        != null;
  }

  public String getRowXpath(int idx) {
    StringBuilder searchXpath = new StringBuilder(StringUtils.EMPTY);
    String rowCellLocatorByIndexAndText = cellXpath + searchCriteriaXpathFormat;
    if (searchCriteria.get() != null && searchCriteria.get().isNotEmpty()) {
      for (Map.Entry<String, String> entry : searchCriteria.get().entrySet()) {
        searchXpath.append(String.format(
            rowCellLocatorByIndexAndText,
            getHeadersMap().getFirstKeyByValue(entry.getKey()),
            Quotes.escape(entry.getValue())));
      }
    }
    return String.format("(%s)[%s]", baseXpath + tBodyXpath + rowXpath + searchXpath, idx + 1);
  }

  protected CWebTableHeaderInfo<DR> readHeaders() {
    return new CWebTableHeaderInfo<>(driver, baseXpath + tHeadXpath + headerRowXpath + headerCellXpath);
  }

  protected <O> O performActionOnTable(Map<String, String> criteria, Supplier<O> supplier) {
    return performActionOnTable(criteria, supplier, true);
  }

  protected synchronized <O> O performActionOnTable(Map<String, String> criteria, Supplier<O> supplier, boolean readRecordOnIteration) {
    setReadRecordOnIteration(readRecordOnIteration);
    setSearchCriteria(criteria);
    O o = supplier.get();
    clearSearchCriteria();
    setReadRecordOnIteration(true);
    return o;
  }

  protected void setReadRecordOnIteration(boolean value) {
    readRecordOnIteration.set(value);
  }

  protected void setSearchCriteria(Map<String, String> searchCriteria) {
    clearSearchCriteria();
    logger.debug("Set Search Criteria to " + searchCriteria);
    this.searchCriteria.get().putAll(searchCriteria);
  }

  protected void clearSearchCriteria() {
    logger.debug("Clear Search Criteria");
    this.searchCriteria.get().clear();
  }
}
