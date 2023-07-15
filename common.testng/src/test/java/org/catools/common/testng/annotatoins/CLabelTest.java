package org.catools.common.testng.annotatoins;

import org.catools.common.annotations.CRegression;
import org.catools.common.annotations.CSeverity;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.tests.CTest;
import org.testng.annotations.Test;

public class CLabelTest extends CTest {

  @Test
  @IANY1
  @RANY1
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void ignoreByOneLabel() {
    throw new CRuntimeException("Should skip by one label");
  }

  @Test
  @IANY1
  @IANY2
  @RANY1
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void ignoreByTwoLabel() {
    throw new CRuntimeException("Should skip by two label");
  }

  @Test
  @IANY1
  @IANY2
  @IANY3
  @RANY1
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void ignoreByAllAnyLabel() {
    throw new CRuntimeException("Should skip by all ANY label");
  }

  @Test
  @RALL1
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void ignoreByOneAllLabel() {
    throw new CRuntimeException("Should skip by only one ALL label");
  }

  @Test
  @IALL1
  @IALL2
  @IALL3
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void ignoreByAllLabel() {
    throw new CRuntimeException("Should skip by ALL label");
  }

  @Test
  @RALL1
  @RALL2
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void ignoreByTwoAllLabel() {
    throw new CRuntimeException("Should skip by two ALL label");
  }

  @Test
  @RALL1
  @RALL2
  @RALL3
  @CRegression(depth = 2)
  @CSeverity(level = 1)
  public void ignoreByRegressionDepth() {
    throw new CRuntimeException("Should skip by Regression Depth");
  }

  @Test
  @RALL1
  @RALL2
  @RALL3
  @CRegression(depth = 1)
  @CSeverity(level = 2)
  public void ignoreBySeverityLevel() {
    throw new CRuntimeException("Should skip by Severity Level");
  }

  @Test
  @RALL1
  @RALL2
  @RALL3
  @CRegression(depth = 2)
  @CSeverity(level = 2)
  public void ignoreByRegressionDepthAndSeverityLevel() {
    throw new CRuntimeException("Should skip by Severity Level and Regression Depth");
  }

  @Test
  @RALL1
  @RALL2
  @RALL3
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void shouldRunByAllLabel() {
  }

  @Test
  @RALL1
  @RANY1
  @RALL3
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void shouldRunByAnyLabel() {
  }

  @Test(dependsOnMethods = {"shouldRunByAllLabel", "shouldRunByAnyLabel"})
  @RANY1
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void testLabels() {
  }
}
