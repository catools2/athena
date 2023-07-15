package org.catools.common.extensions.wait;

import org.catools.common.extensions.wait.interfaces.CCollectionWaiter;

import java.util.Collection;

/**
 * Collection wait class contains all wait method which is related to Collection
 */
public class CCollectionWait extends CIterableWait {

  private static <C> CCollectionWaiter toWaiter(Collection<C> actual) {
    return () -> actual;
  }
}
