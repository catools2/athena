package org.catools.pipeline.tests;

import org.catools.common.annotations.*;
import org.catools.common.tests.CTest;
import org.testng.annotations.Test;

public class CPipelineTest extends CTest {
  @Test
  @CIgnored
  @CRegression(depth = 1)
  public void testPipeline1() {

  }

  @Test
  @CRegression(depth = 1)
  @CSeverity(level = 1)
  public void testPipeline2() {

  }

  @Test
  @CTestIds(ids = {"T-12345", "T-45678"})
  @COpenDefects(ids = {"OD-12345"})
  @CDefects(ids = {"D-12345"})
  @CAwaiting(cause = "Awaiting Something")
  @CDeferred(ids = {"DIF-123"})
  public void testPipeline3() {

  }

  @Test
  @CTestIds(ids = {"T-12345", "T-45678"})
  @COpenDefects(ids = {"OD-12345"})
  public void testPipeline4() {

  }

  @Test
  @CTestIds(ids = {"T-32345", "T-45678"})
  @COpenDefects(ids = {"OD-32345"})
  public void testPipeline5() {

  }

  @Test
  @CTestIds(ids = {"T-32345", "T-35678"})
  @COpenDefects(ids = {"OD-42345"})
  public void testPipeline6() {

  }

  @Test
  public void testPipeline7() {

  }

  @Test
  public void testPipeline8() {

  }

}
