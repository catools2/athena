package org.catools.gatling;

class GitRepositoryPerformanceIT extends AthenaPerformanceIT {

  @Override
  protected String getSimulator() {
    return GitRepositorySimulator.class.getName();
  }

}