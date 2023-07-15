package org.catools.common.annotations;

import org.catools.common.testng.listeners.CIMethodInterceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation {@link CRegression} uses to mark test with the depth of regression which has been
 * assigned to the test. This information will be used to define if the test should be executed
 * based on value of TESTNG_RUN_REGRESSION_DEPTH
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>You must include {@link CIMethodInterceptor} in your listeners.
 *   <li>Any tests without {@link CRegression} will be ignored if value of
 *       TESTNG_RUN_REGRESSION_DEPTH is greater than Zero.
 *   <li>Any test with {@link CRegression} value less than OR equal to TESTNG_RUN_SEVERITY_LEVEL
 *       will be included in execution suite.
 * </ul>
 *
 * @see CDeferred
 * @see CAwaiting
 * @see COpenDefects
 * @see CSeverity
 * @see CIgnored
 * @see COpenDefects
 * @see CDefects
 * @see CTestIds
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface CRegression {
  int depth();
}
