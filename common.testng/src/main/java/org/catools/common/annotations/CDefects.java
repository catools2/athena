package org.catools.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation {@link CDefects} uses to link test to related defects which has been fixed or not
 * reproduce anymore.
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>if test fails due to knows defect then we should use {@link COpenDefects} annotation
 *       instead.
 * </ul>
 *
 * @see CDeferred
 * @see CIgnored
 * @see CAwaiting
 * @see COpenDefects
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface CDefects {
  String[] ids();
}
