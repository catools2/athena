package org.catools.tms.etl.cache;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.tms.etl.dao.*;
import org.catools.tms.etl.model.*;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.function.Supplier;

public class CEtlCacheManager {
  private static final CMap<String, CEtlUser> USERS = new CHashMap<>();
  private static final CMap<String, CEtlItemMetaData> METADATA = new CHashMap<>();
  private static final CMap<String, CEtlProject> PROJECTS = new CHashMap<>();
  private static final CMap<String, CEtlVersion> VERSIONS = new CHashMap<>();
  private static final CMap<String, CEtlCycle> CYCLES = new CHashMap<>();
  private static final CMap<String, CEtlStatus> STATUSES = new CHashMap<>();
  private static final CMap<String, CEtlExecutionStatus> EXECUTION_STATUSES = new CHashMap<>();
  private static final CMap<String, CEtlPriority> PRIORITIES = new CHashMap<>();
  private static final CMap<String, CEtlItemType> ITEM_TYPES = new CHashMap<>();
  private static final CMap<String, CEtlItem> ITEMS = new CHashMap<>();

  public static synchronized CEtlItem readItem(String issueOd) {
    return read(ITEMS, issueOd, () -> {
      CEtlItem result = CEtlItemDao.getItemById(issueOd);
      if (result == null) {
        throw new InvalidParameterException("Item not found. Item Id: " + issueOd);
      }
      return result;
    });
  }

  public static synchronized CEtlUser readUser(CEtlUser user) {
    String userName = user.getName();
    return read(USERS, userName, () -> {
      CEtlUser result = CEtlUserDao.getUserByName(userName);
      if (result != null) {
        return result;
      }
      return CEtlUserDao.merge(user);
    });
  }

  public static CEtlItemMetaDatas readMetaData(Collection<CEtlItemMetaData> data) {
    CEtlItemMetaDatas metaDatas = new CEtlItemMetaDatas();
    for (CEtlItemMetaData metadata : data) {
      metaDatas.add(readMetaData(metadata));
    }
    return metaDatas;
  }

  public static synchronized CEtlItemMetaData readMetaData(CEtlItemMetaData metaData) {
    return read(METADATA, metaData.getName() + metaData.getValue(), () -> {
      CEtlItemMetaData result = CEtlMetaDataDao.getMetaDataByNameAndValue(metaData);
      if (result != null) {
        return result;
      }
      return CEtlMetaDataDao.merge(metaData);
    });
  }

  public static synchronized CEtlProject readProject(CEtlProject project) {
    return read(PROJECTS, project.getName(), () -> {
      CEtlProject result = CEtlProjectDao.getProjectByName(project.getName());
      if (result != null) {
        return result;
      }
      return CEtlProjectDao.merge(project);
    });
  }

  public static synchronized CEtlVersion readVersion(CEtlVersion version) {
    return read(VERSIONS, version.getProject().getName() + version.getName(), () -> {
      CEtlVersion result = CEtlVersionDao.getVersion(version.getProject(), version.getName());
      if (result != null) {
        return result;
      }
      return CEtlVersionDao.merge(version);
    });
  }

  public static synchronized CEtlCycle readCycle(CEtlCycle cycle) {
    return read(CYCLES, cycle.getId(), () -> {
      CEtlCycle result = CEtlCycleDao.getCycleById(cycle.getId());

      if (result != null) {
        return result;
      }
      return CEtlCycleDao.merge(cycle);
    });
  }

  public static synchronized CEtlStatus readStatus(CEtlStatus status) {
    return read(STATUSES, status.getName(), () -> {
      CEtlStatus result = CEtlStatusDao.getStatusByName(status.getName());
      if (result != null) {
        return result;
      }
      return CEtlStatusDao.merge(status);
    });
  }

  public static synchronized CEtlExecutionStatus readExecutionStatus(CEtlExecutionStatus status) {
    return read(EXECUTION_STATUSES, status.getName(), () -> {
      CEtlExecutionStatus result = CEtlExecutionStatusDao.getStatusByName(status.getName());
      if (result != null) {
        return result;
      }
      return CEtlExecutionStatusDao.merge(status);
    });
  }

  public static synchronized CEtlPriority readPriority(CEtlPriority priority) {
    return read(PRIORITIES, priority.getName(), () -> {
      CEtlPriority result = CEtlPriorityDao.getPriorityByName(priority.getName());
      if (result != null) {
        return result;
      }
      return CEtlPriorityDao.merge(priority);
    });
  }

  public static synchronized CEtlItemType readType(CEtlItemType type) {
    return read(ITEM_TYPES, type.getName(), () -> {
      CEtlItemType result = CEtlItemTypeDao.getItemTypeByName(type.getName());
      if (result != null) {
        return result;
      }
      return CEtlItemTypeDao.merge(type);
    });
  }

  private static <T> T read(CMap<String, T> storage, String key, Supplier<T> getValue) {
    if (!storage.containsKey(key)) {
      storage.put(key, getValue.get());
    }
    return storage.get(key);
  }
}
