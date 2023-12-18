package org.catools.metrics.cache;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.metrics.dao.CMetricMetaDataDao;
import org.catools.metrics.model.CMetric;

import java.util.function.Supplier;

public class CMetricCacheManager {
  private static final CMap<String, CMetric> METADATA = new CHashMap<>();

  public static synchronized CMetric readMetaData(String name, String value, Number amount) {
    return read(METADATA, name + value + amount, () -> {
      CMetric result = CMetricMetaDataDao.getMetaDataByNameAndValue(name, value, amount);
      if (result != null) {
        return result;
      }
      return CMetricMetaDataDao.merge(new CMetric(name, value, amount));
    });
  }

  private static synchronized <T> T read(CMap<String, T> storage, String key, Supplier<T> getValue) {
    return storage.computeIfAbsent(key, k -> getValue.get());
  }
}
