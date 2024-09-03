package org.catools.gatling;

class AthenaSpecPerformanceIT extends AthenaPerformanceIT {

  @Override
  protected String getSimulator() {
    return AthenaSpecSimulator.class.getName();
  }
}