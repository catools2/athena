package org.catools.media.extensions.types.interfaces;

import org.catools.media.extensions.states.interfaces.CImageComparisionState;
import org.catools.media.extensions.wait.interfaces.CImageComparisionWaiter;
import org.catools.media.extensions.waitVerify.interfaces.CImageComparisonWaitVerify;

/**
 * CImageComparisionExtension is an central interface where we extend all boolean related interfaces
 * so adding new functionality will be much easier. <strong>
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
public interface CImageComparisonExtension
    extends CImageComparisionWaiter, CImageComparisonWaitVerify, CImageComparisionState {
}
