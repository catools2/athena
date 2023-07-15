package org.catools.common.extensions.types.interfaces;

import org.catools.common.extensions.verify.interfaces.waitVerifier.CCollectionWaitVerifier;
import org.catools.common.extensions.verify.interfaces.waitVerify.CCollectionWaitVerify;
import org.catools.common.extensions.wait.interfaces.CCollectionWaiter;

import java.util.Collection;

/**
 * CDynamicCollectionExtension is an central interface where we extend all Collection related
 * interfaces so adding new functionality will be much easier. <strong>
 *
 * <p>Java does not allow to override Object methods in interface level so this is something we
 * should care about it manually.
 *
 * <p>Make sure to override equals, hashCode (and if needed toString methods) in your
 * implementations. <code>
 *
 * @Override public int hashCode() {
 * return getValue().hashCode();
 * }
 * @Override public boolean equals(Object obj) {
 * return isEqual(obj);
 * }
 * </code> </strong>
 */
public interface CDynamicCollectionExtension<E> extends
    CStaticCollectionExtension<E, Collection<E>>,
    CCollectionWaiter<E, Collection<E>>,
    CCollectionWaitVerifier<E, Collection<E>>,
    CCollectionWaitVerify<E, Collection<E>> {
  @Override
  default boolean withWaiter() {
    return true;
  }
}
