package org.catools.web.table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.utils.CSleeper;
import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriver;
import org.openqa.selenium.By;

import javax.ws.rs.NotSupportedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

@Getter
@Setter
@Accessors(chain = true)
public abstract class CWebMultiPageTable<DR extends CDriver, R extends CWebTableRow<DR, ? extends CWebTable<DR, R>>>
    extends CWebTable<DR, R> {
  private final CWebElement<DR> firstLink;
  private final CWebElement<DR> previousLink;
  private final CWebElement<DR> nextLink;
  private final CWebElement<DR> lastLink;
  private final int maxNumberOfPageToIterate;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private final ThreadLocal<Boolean> singlePageMode = ThreadLocal.withInitial(() -> false);

  public CWebMultiPageTable(
      String name,
      DR driver,
      String baseXpath,
      By firstLocator,
      By previousLocator,
      By nextLocator,
      By lastLocator) {
    this(
        name,
        driver,
        baseXpath,
        firstLocator,
        previousLocator,
        nextLocator,
        lastLocator,
        DEFAULT_TIMEOUT);
  }

  public CWebMultiPageTable(
      String name,
      DR driver,
      String baseXpath,
      By firstLocator,
      By previousLocator,
      By nextLocator,
      By lastLocator,
      int waitSec) {
    this(
        name,
        driver,
        baseXpath,
        firstLocator,
        previousLocator,
        nextLocator,
        lastLocator,
        waitSec,
        100);
  }

  public CWebMultiPageTable(
      String name,
      DR driver,
      String baseXpath,
      By firstLocator,
      By previousLocator,
      By nextLocator,
      By lastLocator,
      int waitSec,
      int maxNumberOfPageToIterate) {
    super(name, driver, baseXpath, waitSec);
    this.firstLink = new CWebElement<>("First", driver, firstLocator);
    this.previousLink = new CWebElement<>("Previous", driver, previousLocator);
    this.nextLink = new CWebElement<>("Next", driver, nextLocator);
    this.lastLink = new CWebElement<>("Last", driver, lastLocator);
    this.maxNumberOfPageToIterate = maxNumberOfPageToIterate;

    Runtime.getRuntime().addShutdownHook(new Thread(singlePageMode::remove));
  }

  public abstract String getCurrentPageNumber();

  public int getTotalRecordCount() {
    return performActionOnTable(new HashMap<>(), () -> (int) getAll().stream().count(), false);
  }

  public int getCurrentPageRecordCount() {
    return performActionOnCurrentPage(new HashMap<>(), () -> (int) getAll().stream().count(), false);
  }

  public boolean gotoFirstPage() {
    logger.trace("Go to first page.");
    if (firstLink.isClickable(0)) {
      firstLink.click();
      return true;
    } else {
      int counter = maxNumberOfPageToIterate;
      while (gotoPreviousPage() && counter-- > 0) ;
      return counter < maxNumberOfPageToIterate;
    }
  }

  public boolean gotoPreviousPage() {
    if (previousLink.Present.isFalse() || previousLink.Enabled.isFalse()) {
      return false;
    }
    String currentPageNumber = getCurrentPageNumber();
    logger.trace("Go to previous page from page {}.", currentPageNumber);
    previousLink.click();
    if (StringUtils.isNotBlank(currentPageNumber)) {
      isDataAvailable();
      if (currentPageNumber.equals(getCurrentPageNumber())) {
        return false;
      }
    }
    CSleeper.sleepTightInSeconds(1);
    return true;
  }

  public boolean gotoNextPage() {
    if (nextLink.Present.isFalse() || nextLink.Enabled.isFalse()) {
      return false;
    }
    String currentPageNumber = getCurrentPageNumber();
    logger.trace("Go to next page from page {}.", currentPageNumber);
    nextLink.click();
    return StringUtils.isBlank(currentPageNumber)
        || !currentPageNumber.equals(getCurrentPageNumber());
  }

  public boolean gotoLastPage() {
    logger.trace("Go to last page.");
    if (lastLink.isClickable(0)) {
      lastLink.click();
      return true;
    } else {
      int counter = maxNumberOfPageToIterate;
      while (gotoNextPage() && counter-- > 0) ;
      return counter < maxNumberOfPageToIterate;
    }
  }

  @Override
  public Iterator<R> iterator() {
    if (singlePageMode.get())
      return super.iterator();
    return iterateWithPagination();
  }

  protected synchronized <O> O performActionOnCurrentPage(Map<String, String> criteria, Supplier<O> supplier, boolean readRecordOnIteration) {
    singlePageMode.set(true);
    O o = super.performActionOnTable(criteria, supplier, readRecordOnIteration);
    singlePageMode.set(false);
    return o;
  }

  private Iterator<R> iterateWithPagination() {
    gotoFirstPage();
    return new Iterator<>() {
      int counter = maxNumberOfPageToIterate;
      int cursor = 0;
      R record = null;

      @Override
      public boolean hasNext() {
        record = null;
        while (counter > 0) {
          // Read the record
          boolean recordPresent = hasRecord(cursor) && (record = getRecord(cursor)) != null && record.Present.isTrue();

          //if record available then we are good
          if (recordPresent) break;

          // if not record available, and we cannot move to the next page then set the record to null to end the iteration
          if (!gotoNextPage()) {
            record = null;
            break;
          }
          // go to next page if no record found;
          counter--;
          cursor = 0;
        }
        return record != null;
      }

      @SuppressWarnings("unchecked")
      @Override
      public R next() {
        if (record == null || record.Present.isFalse())
          throw new NoSuchElementException();

        cursor++;
        return record;
      }

      @Override
      public void remove() {
        throw new NotSupportedException();
      }
    };
  }
}
