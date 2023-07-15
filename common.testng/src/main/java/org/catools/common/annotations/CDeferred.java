package org.catools.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * It is not a common case when we want to execute failing test cases but in large project it
 * happens that team loss the trace of defect and it is not clear if the defect is fixed or it is
 * reproduces and what is the impact. In poor managed process you might have to execute failing
 * tests to see if defect gone or not.
 *
 * <p>This annotation {@link CDeferred} uses to link test with related defects which has not been
 * fixed and deferred for a version or more. In case if the test scenario passes we mark it at the
 * end as passed but if it fails then we mark it as deferred and put the related id in report
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>if test fails due to knows defect then we should use {@link COpenDefects} annotation
 *       instead.
 *   <li>if deferred defect fixed then we should move it {@link CDefects} annotation and remove this
 *       from test.
 * </ul>
 *
 * @see CDeferred
 * @see CIgnored
 * @see CAwaiting
 * @see COpenDefects
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface CDeferred {
  String[] ids();
}
