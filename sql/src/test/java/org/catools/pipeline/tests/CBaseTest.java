package org.catools.pipeline.tests;

import org.catools.common.date.CDate;
import org.catools.etl.git.configs.CGitConfigRepo;
import org.catools.etl.git.configs.CGitConfigs;
import org.catools.etl.git.model.CGitCommit;
import org.catools.etl.git.utils.CGitLoader;
import org.catools.sql.CSqlDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.testng.annotations.BeforeSuite;

public class CBaseTest {
  protected static final String PRIMARY = "PRIMARY";
  protected static final String SELECT_COUNT = "Select count(*) from git.\"commit\"";
  protected static final String FROM_ALL = "from git.\"commit\"";
  protected static final String VALID_FROM_COMMIT = "from git.\"commit\" where hash='d78457d822a84787cd23bcbd04a7b01ec4a6de6a'";
  protected static final String INVALID_FROM_COMMIT = "from git.\"commit\" where hash='INVALID'";
  protected static final String DATE_FORMAT = "YYYY-MM-dd HH:mm:ss.SSS";

  protected static final CGitCommit commit = new CGitCommit() {{
    setHash("d78457d822a84787cd23bcbd04a7b01ec4a6de6a");
    setShortMessage("Merge branch 'fix_code_smells' into 'master'");
    setCommitTime(CDate.of("2022-08-10 19:20:00.000", DATE_FORMAT));
  }};

  @BeforeSuite
  public void beforeSuite() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(org.testcontainers.jdbc.ContainerDatabaseDriver.class);
    dataSource.setUrl("jdbc:tc:postgresql:13:///test");
    dataSource.setUsername("test");
    dataSource.setPassword("test");
    CSqlDataSource.addDataSource(PRIMARY, dataSource);

    CGitConfigRepo repository = CGitConfigs.getRepositories().get(0);
    CGitLoader.loadRepository("repo1", repository.getUrl(), 10);
  }
}
