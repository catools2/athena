package org.catools.pipeline.cache;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.pipeline.configs.CPipelineConfigs;
import org.catools.pipeline.dao.CPipelineExecutionMetaDataDao;
import org.catools.pipeline.dao.CPipelineMetaDataDao;
import org.catools.pipeline.dao.CPipelineUserDao;
import org.catools.pipeline.model.CPipelineExecutionMetaData;
import org.catools.pipeline.model.CPipelineMetaData;
import org.catools.pipeline.model.CPipelineUser;

import java.util.function.Supplier;

public class CPipelineCacheManager {
  private static CPipelineUser EXECUTOR;
  private static final CMap<String, CPipelineMetaData> METADATA = new CHashMap<>();
  private static final CMap<String, CPipelineExecutionMetaData> EXECUTION_METADATA = new CHashMap<>();

  public static synchronized CPipelineUser getExecutor() {
    if (EXECUTOR == null) {
      CPipelineUser user = CPipelineConfigs.getExecutor();
      CPipelineUser result = CPipelineUserDao.getUserByName(user.getName());
      if (result == null) {
        result = CPipelineUserDao.merge(user);
      }
      EXECUTOR = result;
    }
    return EXECUTOR;
  }

  public static synchronized CPipelineMetaData readMetaData(String name, String value) {
    return read(METADATA, name + value, () -> {
      CPipelineMetaData result = CPipelineMetaDataDao.getMetaDataByNameAndValue(name, value);
      if (result != null) {
        return result;
      }
      return CPipelineMetaDataDao.merge(new CPipelineMetaData(name, value));
    });
  }

  public static synchronized CPipelineExecutionMetaData readExecutionMetaData(String name, String value) {
    return read(EXECUTION_METADATA, name + value, () -> {
      CPipelineExecutionMetaData result = CPipelineExecutionMetaDataDao.getMetaDataByNameAndValue(name, value);
      if (result != null) {
        return result;
      }
      return CPipelineExecutionMetaDataDao.merge(new CPipelineExecutionMetaData(name, value));
    });
  }

  private static <T> T read(CMap<String, T> storage, String key, Supplier<T> getValue) {
    if (!storage.containsKey(key)) {
      storage.put(key, getValue.get());
    }
    return storage.get(key);
  }
}
