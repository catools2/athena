package org.catools.metrics.dao;

import org.catools.metrics.model.CMetric;
import org.hibernate.annotations.QueryHints;

public class CMetricMetaDataDao extends CMetricsDao {
  public static CMetric getMetaDataByNameAndValue(String name, String value, Number amount) {
    return doTransaction(entityManager -> entityManager
        .createNamedQuery("getMetricsMetaData", CMetric.class)
        .setParameter("name", name)
        .setParameter("value", value)
        .setParameter("amount", amount)
        .setHint(QueryHints.CACHEABLE, true)
        .getResultStream()
        .findFirst()
        .orElse(null));
  }
}
