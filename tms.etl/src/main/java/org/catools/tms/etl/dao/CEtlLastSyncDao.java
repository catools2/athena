package org.catools.tms.etl.dao;

import org.catools.tms.etl.model.CEtlLastSync;

import java.util.Date;

public class CEtlLastSyncDao extends CEtlBaseDao {
  public static void updateProjectLastSync(String component, String projectName, Date syncDate) {
    updateLastSync(String.format("A:%s|P:%s|FullSync", component, projectName), syncDate);
  }

  public static Date getProjectLastSync(String component, String projectName) {
    return getLastSync(String.format("A:%s|P:%s|FullSync", component, projectName));
  }

  public static void updateExecutionLastSync(
      String component, String projectName, String versionName, Date syncDate) {
    updateLastSync(
        String.format("A:%s|P:%s|V:%s|Execution", component, projectName, versionName),
        syncDate);
  }

  public static Date getExecutionLastSync(
      String component, String projectName, String versionName) {
    return getLastSync(
        String.format("A:%s|P:%s|V:%s|Execution", component, projectName, versionName));
  }

  public static void updateItemsLastSync(
      String component, String projectName, String itemType, Date syncDate) {
    updateLastSync(String.format("A:%s|P:%s|%s", component, projectName, itemType), syncDate);
  }

  public static Date getItemsLastSync(String component, String projectName, String itemType) {
    return getLastSync(String.format("A:%s|P:%s|%s", component, projectName, itemType));
  }

  private static synchronized void updateLastSync(String key, Date syncDate) {
    merge(new CEtlLastSync(key, syncDate));
  }

  private static Date getLastSync(String key) {
    CEtlLastSync lastSync = find(CEtlLastSync.class, key);
    return lastSync == null ? null : lastSync.getSyncDate();
  }
}
