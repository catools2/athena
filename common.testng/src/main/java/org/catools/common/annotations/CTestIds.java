package org.catools.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation {@link CTestIds} uses to mark test with the id which is using for future
 * traceability. (i.e. Jira Number)
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>traceability is a most have for any testing system so we should be careful to have a unique
 *       {@link CTestIds} for each test.
 *   <li>we use Ids from this set when we get TestNG test suite for some scenarios.
 *   <li>we plan to use this id for command line execution in future so using a command line you can
 *       specify list of one or more tests to be executed.
 *   <li>we use this ids to refer to the test during reporting .
 * </ul>
 *
 * @see CDeferred
 * @see CAwaiting
 * @see COpenDefects
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface CTestIds {
  String[] ids();
}
