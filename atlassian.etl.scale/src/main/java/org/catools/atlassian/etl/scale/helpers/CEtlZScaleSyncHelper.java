package org.catools.atlassian.etl.scale.helpers;

import com.atlassian.jira.rest.client.api.domain.BasicProject;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.etl.jira.translators.CEtlJiraTranslator;
import org.catools.atlassian.etl.scale.translators.CEtlZScaleTestCaseTranslator;
import org.catools.atlassian.jira.client.CJiraClient;
import org.catools.atlassian.scale.model.CZScaleTestCase;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.CSet;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.utils.CStringUtil;
import org.catools.etl.tms.cache.CEtlCacheManager;
import org.catools.etl.tms.dao.CEtlItemDao;
import org.catools.etl.tms.model.CEtlItem;
import org.catools.etl.tms.model.CEtlProject;
import org.catools.etl.tms.model.CEtlVersion;
import org.catools.etl.tms.model.CEtlVersions;

import java.util.Objects;

@UtilityClass
@Slf4j
public class CEtlZScaleSyncHelper {
  private static final CSet<BasicProject> PROJECTS = new CSet<>();
  private static final CMap<String, CList<CEtlVersion>> PROJECT_VERSION = new CHashMap<>();

  public static CEtlItem addItem(CZScaleTestCase testcase) {
    CEtlProject etlProject = getProject(testcase);
    CEtlVersions versions = new CEtlVersions(getProjectVersions(testcase.getProjectKey(), etlProject));
    CEtlItemDao.mergeItem(CEtlZScaleTestCaseTranslator.translateTestCase(etlProject, versions, testcase));
    return CEtlCacheManager.readItem(testcase.getKey());
  }

  private static CEtlProject getProject(CZScaleTestCase testcase) {
    BasicProject project = getProjectByKey(testcase.getProjectKey());
    return project == null || CStringUtil.isBlank(project.getName()) ?
        CEtlProject.UNSET :
        new CEtlProject(project.getName());
  }

  public static BasicProject getProjectByName(String projectName) {
    return getProjects().getFirst(p -> Objects.equals(p.getName(), projectName));
  }

  public static BasicProject getProjectByKey(String projectKey) {
    return getProjects().getFirst(p -> p.getKey().equals(projectKey));
  }

  public static CList<CEtlVersion> getProjectVersions(String projectKey, CEtlProject project) {
    if (!PROJECT_VERSION.keySet().contains(projectKey)) {
      CList<CEtlVersion> cEtlVersions = CJiraClient.getProjectVersions(projectKey).mapToList(v -> CEtlJiraTranslator.translateVersion(project, v));
      PROJECT_VERSION.put(projectKey, cEtlVersions);
    }
    return PROJECT_VERSION.get(projectKey);
  }

  private static CSet<BasicProject> getProjects() {
    if (PROJECTS.isEmpty()) {
      PROJECTS.addAll(CJiraClient.getProjects());
    }
    return PROJECTS;
  }
}
