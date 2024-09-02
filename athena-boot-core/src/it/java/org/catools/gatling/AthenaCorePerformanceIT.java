package org.catools.gatling;

class AthenaCorePerformanceIT extends AthenaPerformanceIT {

  @Override
  protected String getSimulator() {
    return AthenaCoreSimulator.class.getName();
  }

}