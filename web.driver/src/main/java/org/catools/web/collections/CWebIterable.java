package org.catools.web.collections;

import org.catools.common.collections.interfaces.CIterable;
import org.catools.common.extensions.types.interfaces.CDynamicIterableExtension;
import org.catools.web.controls.CWebElement;

import javax.ws.rs.NotSupportedException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface CWebIterable<E extends CWebElement<?>> extends CIterable<E, Iterable<E>>, CDynamicIterableExtension<E> {

  /**
   * The Zero based index which means the first record has index 0. In case if you want to use it in
   * your java code, you should decrement it for 0.
   *
   * @param idx
   * @return
   */
  E getRecord(int idx);

  /**
   * The Zero based index which means the first record has index 0. In case if you want to use it in
   * your java code, you should decrement it for 0. You really should not need to change this
   * method.
   *
   * @param idx
   * @return
   */
  boolean hasRecord(int idx);

  @SuppressWarnings("unchecked")
  @Override
  default Iterable<E> _get() {
    return this;
  }

  @Override
  default boolean withWaiter() {
    return true;
  }

  @Override
  default Iterator<E> iterator() {
    return new Iterator<>() {
      int cursor = 0;
      E record = null;

      @Override
      public boolean hasNext() {
        return hasRecord(cursor) && (record = getRecord(cursor)) != null && record.Present.isTrue();
      }

      @Override
      public E next() {
        if (record == null)
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
