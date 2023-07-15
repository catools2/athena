package org.catools.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * It happens when you want to remove test from your execution but in large projects it happens that
 * you disable test and then forgot about it. To avoid such situation we have {@link CIgnored}.
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>We have a flag CORE_SKIP_CLASS_WITH_IGNORED_TEST to avoid running tests with {@link
 *       CIgnored} annotation.
 *   <li>if test fails due to knows defect then we should use {@link COpenDefects} annotation
 *       instead.
 *   <li>if deferred defect fixed then we should move it {@link CDefects} annotation and remove this
 *       from test.
 * </ul>
 *
 * @see CDeferred
 * @see CDefects
 * @see CAwaiting
 * @see COpenDefects
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface CIgnored {
}
