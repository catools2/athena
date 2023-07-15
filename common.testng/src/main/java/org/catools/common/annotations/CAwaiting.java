package org.catools.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation {@link CAwaiting} uses to mark test which fails and awaiting action from someone
 * or if needs investigation.
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>We have a flag CORE_SKIP_CLASS_WITH_AWAITING_TEST to avoid running tests with {@link
 *       CAwaiting} annotation.
 *   <li>if test fails due to knows defect then we should use {@link COpenDefects} annotation
 *       instead.
 *   <li>after when defect has been fixed we replace {@link COpenDefects} with {@link CDefects}. We
 *       do this to ensure that we have visibility on all related defects so in case if open return
 *       developers can identify the root cause faster.
 * </ul>
 *
 * @see CDeferred
 * @see CIgnored
 * @see CDefects
 * @see COpenDefects
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface CAwaiting {
  String cause();
}
