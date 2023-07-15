package org.catools.common.annotations;

import org.catools.common.testng.listeners.CIMethodInterceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * This annotation {@link CSeverity} uses to mark test with the level of severity which has been
 * assigned to the test. This information will be used to define if the test should be executed
 * based on value of TESTNG_RUN_SEVERITY_LEVEL
 *
 * <p>Please note:
 *
 * <ul>
 *   <li>You must include {@link CIMethodInterceptor} in your listeners.
 *   <li>Any tests without {@link CSeverity} will be ignored if value of TESTNG_RUN_SEVERITY_LEVEL
 *       is greater than Zero.
 *   <li>Any test with {@link CSeverity} value less than OR equal to TESTNG_RUN_SEVERITY_LEVEL will
 *       be included in execution suite.
 *   <li>Any tests with both {@link CRegression} and {@link CSeverity} should have both condition
 *       true if value of TESTNG_RUN_SEVERITY_LEVEL and TESTNG_RUN_REGRESSION_DEPTH is greater than
 *       Zero.
 * </ul>
 *
 * @see CDeferred
 * @see CAwaiting
 * @see COpenDefects
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface CSeverity {
  int level();
}
