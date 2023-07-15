package org.catools.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation {@link COpenDefects} uses to link test to related open defects.
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>if defect fixed then we should remove {@link COpenDefects} and add fixed defect id
 *       to @CDefects annotation.
 *   <li>if defect deferred for short term then we can change {@link COpenDefects} to {@link
 *       CDeferred}.
 * </ul>
 *
 * @see CDeferred
 * @see CAwaiting
 * @see COpenDefects
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface COpenDefects {
  String[] ids();
}
