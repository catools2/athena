package org.catools.common.extensions.base;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.extensions.states.interfaces.CCollectionState;

import java.util.Collection;
import java.util.Map;

/**
 * CBaseCollectionExtension is a base interface for collection related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * @see Map
 * @see CCollection
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CBaseCollectionExtension<E, C extends Collection<E>> extends CBaseIterableExtension<E, C> {

  default CCollectionState<E, C> _toState(C e) {
    return () -> e;
  }
}
