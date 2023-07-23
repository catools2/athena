package org.catools.common.extensions.base;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CLinkedMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.extensions.states.interfaces.CMapState;
import org.catools.common.extensions.verify.hard.CMapVerification;

import java.util.Map;

/**
 * CBaseIterableExtension is a base interface for Map related extensions.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link
 * CMapVerification} for each new added verification here
 *
 * @see Map
 * @see CCollection
 * @see CHashMap
 * @see CLinkedMap
 * @see CSet
 * @see CList
 */
public interface CBaseMapExtension<K, V> extends CBaseObjectExtension<Map<K, V>, CMapState<K, V>> {

  default CMapState<K, V> _toState(Map<K, V> e) {
    return () -> e;
  }
}
