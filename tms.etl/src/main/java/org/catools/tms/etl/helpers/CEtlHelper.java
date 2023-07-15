package org.catools.tms.etl.helpers;

import org.catools.common.utils.CRetry;
import org.catools.tms.etl.cache.CEtlCacheManager;
import org.catools.tms.etl.dao.CEtlItemStatusTransitionDao;
import org.catools.tms.etl.model.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CEtlHelper {

  public static void normalizeItem(CEtlItem item) {
    item.setProject(CEtlCacheManager.readProject(item.getProject()));
    item.setPriority(CEtlCacheManager.readPriority(item.getPriority()));
    item.setStatus(CEtlCacheManager.readStatus(item.getStatus()));
    item.setType(CEtlCacheManager.readType(item.getType()));
    item.setMetadata(CEtlCacheManager.readMetaData(item.getMetadata()));
    item.setVersions(normalizeVersions(item.getVersions()));
    normalizeStatusTransitions(item);
  }

  public static void normalizeExecution(CEtlExecution execution) {
    // Related Project, Version and Item should already been merged to normalize execution
    CRetry.retry(integer -> {
      normalizeItem(execution.getItem());
      execution.setCycle(execution.getCycle() == null ? null : normalizeCycle(execution.getCycle()));
      execution.setExecutor(execution.getExecutor() == null ? null : CEtlCacheManager.readUser(execution.getExecutor()));
      execution.setStatus(execution.getStatus() == null ? null : CEtlCacheManager.readExecutionStatus(execution.getStatus()));
      return true;
    }, 5, 2000);
  }

  private static Set<CEtlVersion> normalizeVersions(Collection<CEtlVersion> versions) {
    Set<CEtlVersion> output = new HashSet<>();
    for (CEtlVersion version : versions) {
      output.add(normalizeVersion(version));
    }
    return output;
  }

  private static CEtlVersion normalizeVersion(CEtlVersion version) {
    if (version == null) return null;
    version.setProject(CEtlCacheManager.readProject(version.getProject()));
    return CEtlCacheManager.readVersion(version);
  }

  private static CEtlCycle normalizeCycle(CEtlCycle cycle) {
    if (cycle == null) return null;
    cycle.setVersion(normalizeVersion(cycle.getVersion()));
    return CEtlCacheManager.readCycle(cycle);
  }

  private static void normalizeStatusTransitions(CEtlItem item) {
    Set<CEtlItemStatusTransition> transitions = new HashSet<>();
    for (CEtlItemStatusTransition statusTransition : item.getStatusTransitions()) {
      statusTransition.setFrom(CEtlCacheManager.readStatus(statusTransition.getFrom()));
      statusTransition.setTo(CEtlCacheManager.readStatus(statusTransition.getTo()));
      transitions.add(Optional.ofNullable(CEtlItemStatusTransitionDao.getItemStatusTransition(item.getId(), statusTransition)).orElse(statusTransition));
    }
    item.setStatusTransitions(transitions);
  }
}
