package org.catools.tms.etl.tests;

import org.catools.common.date.CDate;
import org.catools.tms.etl.dao.CEtlExecutionDao;
import org.catools.tms.etl.model.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CEtlTest {
  private static PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres:latest");

  @BeforeClass
  public void beforeClass() {
    psql.start();
  }

  @AfterClass
  public void afterClass() {
    psql.stop();
  }

  @Test
  public void testPipeline1() {
    CEtlProject project = new CEtlProject("TestProject");

    CEtlVersion version1 = new CEtlVersion("V1", project);
    CEtlVersion version2 = new CEtlVersion("V2", project);

    CEtlCycle cycle = new CEtlCycle("1", "Cycle1", version1, CDate.now(), CDate.now());

    int max = 5;
    while (max-- > 0) {
      CEtlItem item = new CEtlItem(
          "T-1" + max,
          "Item " + max,
          CDate.now().addMonths(-2),
          CDate.now().addMonths(-1),
          project,
          new CEtlItemType("Test"),
          new CEtlVersions(version1, version2),
          new CEtlStatus("Start"),
          new CEtlPriority("Highest"));

      item.addItemMetaData(new CEtlItemMetaData("K1", "V1"));
      item.addStatusTransition(new CEtlItemStatusTransition(CDate.now().addMonths(-1), new CEtlStatus("Open"), new CEtlStatus("New"), item));

      CEtlUser user = new CEtlUser("akeshmiri" + max);
      int max2 = 2;
      while (max2-- > 0) {
        CEtlExecutionDao.mergeExecution(new CEtlExecution(
            max + "" + max2,
            item,
            cycle,
            CDate.now().addMonths(-2),
            CDate.now().addMonths(-1),
            user,
            new CEtlExecutionStatus("Passed")
        ));
      }

      CEtlExecutionDao.getExecutionsByCycleId(cycle.getId()).verifyIsNotEmpty();
    }
  }
}
