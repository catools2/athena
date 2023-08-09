package org.catools.metrics.cache;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.metrics.dao.CMetricMetaDataDao;
import org.catools.metrics.model.CMetric;

import java.util.function.Supplier;

public class CMetricCacheManager {
  private static final CMap<String, CMetric> METADATA = new CHashMap<>();

  public static synchronized CMetric readMetaData(String name, Number value) {
    return read(METADATA, name + value, () -> {
      CMetric result = CMetricMetaDataDao.getMetaDataByNameAndValue(name, value);
      if (result != null) {
        return result;
      }
      return CMetricMetaDataDao.merge(new CMetric(name, value));
    });
  }

  private static <T> T read(CMap<String, T> storage, String key, Supplier<T> getValue) {
    if (!storage.containsKey(key)) {
      storage.put(key, getValue.get());
    }
    return storage.get(key);
  }
}
