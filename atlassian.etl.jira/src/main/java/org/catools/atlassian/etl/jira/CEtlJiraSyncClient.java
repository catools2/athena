package org.catools.atlassian.etl.jira;

import com.atlassian.jira.rest.client.api.domain.BasicProject;
import lombok.experimental.UtilityClass;
import org.catools.atlassian.etl.jira.translators.CEtlJiraTranslator;
import org.catools.atlassian.jira.client.CJiraClient;
import org.catools.common.collections.CSet;
import org.catools.common.date.CDate;
import org.catools.tms.etl.dao.CEtlItemDao;
import org.catools.tms.etl.dao.CEtlLastSyncDao;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class CEtlJiraSyncClient {
  private static final String JIRA = "JIRA";

  public static void syncJira(
      CSet<String> projectNamesToSync,
      List<String> issueTypes,
      int parallelInputCount,
      int parallelOutputCount) {
    Objects.requireNonNull(projectNamesToSync);
    Objects.requireNonNull(issueTypes);

    CSet<BasicProject> projects = CJiraClient.getProjects();
    for (BasicProject project : projects.getAll(p -> projectNamesToSync.contains(p.getName()))) {
      Date syncStartTime = CDate.now();
      for (String issueType : issueTypes) {
        addItems(project, issueType, parallelInputCount, parallelOutputCount);
      }
      CEtlLastSyncDao.updateProjectLastSync(JIRA, project.getName(), syncStartTime);
    }
  }

  public static void addItems(
      BasicProject project,
      String issueType,
      int parallelInputCount,
      int parallelOutputCount) {
    Objects.requireNonNull(project);
    Objects.requireNonNull(issueType);

    Date lastSync = CEtlLastSyncDao.getItemsLastSync(JIRA, project.getName(), issueType);

    Date syncStartTime = CDate.now();
    CJiraClient.search(
        project.getKey(),
        issueType,
        lastSync,
        parallelInputCount,
        parallelOutputCount,
        issues -> {
          if (issues != null && issues.isNotEmpty())
            CEtlItemDao.mergeItems(CEtlJiraTranslator.translateIssues(issues));
        });

    CEtlLastSyncDao.updateItemsLastSync(JIRA, project.getName(), issueType, syncStartTime);
  }
}
