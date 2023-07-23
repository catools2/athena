package org.catools.common.extensions.base;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CIterable;
import org.catools.common.extensions.states.interfaces.CIterableState;

import java.util.Map;

/**
 * CBaseIterableExtension is a base interface for Iterable related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * @see Map
 * @see CIterable
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CBaseIterableExtension<E, C extends Iterable<E>> extends CBaseObjectExtension<C, CIterableState<E, C>> {

  @Override
  default CIterableState<E, C> _toState(C e) {
    return () -> e;
  }
}
