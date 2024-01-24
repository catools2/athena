package org.catools.sql;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.date.CDate;
import org.catools.common.utils.CRegExUtil;
import org.catools.common.utils.CRetry;
import org.catools.common.utils.CStringUtil;
import org.catools.metrics.configs.CMetricsConfigs;
import org.catools.metrics.model.CMetric;
import org.catools.metrics.utils.CMetricsUtils;
import org.catools.sql.configs.CSqlConfigs;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
@UtilityClass
public class CSqlDataSource {
  private static final CHashMap<String, DataSource> dataSourcesMap = new CHashMap<>();
  private static final String RESULT = "Result: ";

  public static void addDataSource(String sourceName, DataSource dbSource) {
    dataSourcesMap.put(sourceName, dbSource);
  }

  public static class QueryString {
    public static String query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static String query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForString", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          String result = jdbcTemplate.queryForObject(sql, paramSource, String.class);
          log.trace(RESULT + result);
          return result;
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static String query(String sql, String dbSource, Predicate<String> retryCondition, int retryCount, int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static String query(String sql, String dbSource, Predicate<String> retryCondition, int retryCount, int interval, String orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static String query(String sql,
                               MapSqlParameterSource paramSource,
                               String dbSource,
                               Predicate<String> retryCondition,
                               int retryCount,
                               int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static String query(String sql,
                               MapSqlParameterSource paramSource,
                               String dbSource,
                               Predicate<String> retryCondition,
                               int retryCount,
                               int interval,
                               String orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryDate {
    public static Date query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static Date query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForDate", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          Date result = jdbcTemplate.queryForObject(sql, paramSource, Date.class);
          log.trace(RESULT + result);
          return result;
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static Date query(String sql, String dbSource, Predicate<Date> retryCondition, int retryCount, int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Date query(String sql, String dbSource, Predicate<Date> retryCondition, int retryCount, int interval, Date orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static Date query(String sql,
                             MapSqlParameterSource paramSource,
                             String dbSource,
                             Predicate<Date> retryCondition,
                             int retryCount,
                             int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Date query(String sql,
                             MapSqlParameterSource paramSource,
                             String dbSource,
                             Predicate<Date> retryCondition,
                             int retryCount,
                             int interval,
                             Date orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryInt {
    public static Integer query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static Integer query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForInt", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          int result = jdbcTemplate.queryForObject(sql, paramSource, Integer.class);
          log.trace(RESULT + result);
          return result;
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static Integer query(String sql, String dbSource, Predicate<Integer> retryCondition, int retryCount, int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Integer query(String sql, String dbSource, Predicate<Integer> retryCondition, int retryCount, int interval, Integer orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static Integer query(String sql,
                                MapSqlParameterSource paramSource,
                                String dbSource,
                                Predicate<Integer> retryCondition,
                                int retryCount,
                                int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Integer query(String sql,
                                MapSqlParameterSource paramSource,
                                String dbSource,
                                Predicate<Integer> retryCondition,
                                int retryCount,
                                int interval,
                                Integer orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryLong {
    public static Long query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static Long query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForLong", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          Long result = jdbcTemplate.queryForObject(sql, paramSource, Long.class);
          log.trace(RESULT + result);
          return result;
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static Long query(String sql, String dbSource, Predicate<Long> retryCondition, int retryCount, int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Long query(String sql, String dbSource, Predicate<Long> retryCondition, int retryCount, int interval, Long orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static Long query(String sql,
                             MapSqlParameterSource paramSource,
                             String dbSource,
                             Predicate<Long> retryCondition,
                             int retryCount,
                             int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Long query(String sql,
                             MapSqlParameterSource paramSource,
                             String dbSource,
                             Predicate<Long> retryCondition,
                             int retryCount,
                             int interval,
                             Long orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryDouble {
    public static Double query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static Double query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForDouble", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          Double result = jdbcTemplate.queryForObject(sql, paramSource, Double.class);
          log.trace(RESULT + result);
          return result;
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static Double query(String sql, String dbSource, Predicate<Double> retryCondition, int retryCount, int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Double query(String sql, String dbSource, Predicate<Double> retryCondition, int retryCount, int interval, Double orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static Double query(String sql,
                               MapSqlParameterSource paramSource,
                               String dbSource,
                               Predicate<Double> retryCondition,
                               int retryCount,
                               int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static Double query(String sql,
                               MapSqlParameterSource paramSource,
                               String dbSource,
                               Predicate<Double> retryCondition,
                               int retryCount,
                               int interval,
                               Double orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryBigDecimal {
    public static BigDecimal query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static BigDecimal query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForBigDecimal", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          BigDecimal result = jdbcTemplate.queryForObject(sql, paramSource, BigDecimal.class);
          log.trace(RESULT + result);
          return result;
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static BigDecimal query(String sql, String dbSource, Predicate<BigDecimal> retryCondition, int retryCount, int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static BigDecimal query(String sql,
                                   String dbSource,
                                   Predicate<BigDecimal> retryCondition,
                                   int retryCount,
                                   int interval,
                                   BigDecimal orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static BigDecimal query(String sql,
                                   MapSqlParameterSource paramSource,
                                   String dbSource,
                                   Predicate<BigDecimal> retryCondition,
                                   int retryCount,
                                   int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static BigDecimal query(String sql,
                                   MapSqlParameterSource paramSource,
                                   String dbSource,
                                   Predicate<BigDecimal> retryCondition,
                                   int retryCount,
                                   int interval,
                                   BigDecimal orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryList {

    private static final String QUERY_FOR_LIST = "queryForList";

    public static <T> CList<T> query(String sql, RowMapper<T> rowMapper, String dbSource) {
      return query(sql, new MapSqlParameterSource(), rowMapper, dbSource);
    }

    public static <T> CList<T> query(String sql, MapSqlParameterSource paramSource, RowMapper<T> rowMapper, String dbSource) {
      return doAction(QUERY_FOR_LIST, dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          List<T> result = jdbcTemplate.query(sql, paramSource, rowMapper);
          log.trace("Row found: " + result.size());
          return new CList<>(result);
        }
        catch (EmptyResultDataAccessException e) {
          return new CList<>();
        }
      });
    }

    public static <T> CList<T> query(String sql, Class<T> elementType, String dbSource) {
      return query(sql, new MapSqlParameterSource(), elementType, dbSource);
    }

    public static <T> CList<T> query(String sql, MapSqlParameterSource paramSource, Class<T> elementType, String dbSource) {
      return doAction(QUERY_FOR_LIST, dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          List<T> result = jdbcTemplate.queryForList(sql, paramSource, elementType);
          log.trace("Row found: " + result.size());
          return new CList<>(result);
        }
        catch (EmptyResultDataAccessException e) {
          return new CList<>();
        }
      });
    }

    public static CList<Map<String, Object>> query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static CList<Map<String, Object>> query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction(QUERY_FOR_LIST, dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, paramSource);
          log.trace("Row found: " + result.size());
          return new CList<>(result);
        }
        catch (EmptyResultDataAccessException e) {
          return new CList<>();
        }
      });
    }

    public static <T> CList<T> query(String sql,
                                     RowMapper<T> rowMapper,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval) {
      return query(sql, rowMapper, dbSource, retryCondition, retryCount, interval, new CList<>());
    }

    public static <T> CList<T> query(String sql,
                                     RowMapper<T> rowMapper,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval,
                                     CList<T> orElse) {
      return doWithRetry(integer -> query(sql, rowMapper, dbSource), retryCondition, retryCount, interval, orElse);
    }

    public static <T> CList<T> query(String sql,
                                     Class<T> elementType,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval) {
      return query(sql, elementType, dbSource, retryCondition, retryCount, interval, new CList<>());
    }

    public static <T> CList<T> query(String sql,
                                     Class<T> elementType,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval,
                                     CList<T> orElse) {
      return doWithRetry(integer -> query(sql, new MapSqlParameterSource(), elementType, dbSource), retryCondition, retryCount, interval, orElse);
    }

    public static <T> CList<T> query(String sql,
                                     MapSqlParameterSource paramSource,
                                     RowMapper<T> rowMapper,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval) {
      return query(sql, paramSource, rowMapper, dbSource, retryCondition, retryCount, interval, new CList<>());
    }

    public static <T> CList<T> query(String sql,
                                     MapSqlParameterSource paramSource,
                                     RowMapper<T> rowMapper,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval,
                                     CList<T> orElse) {
      return doWithRetry(integer -> query(sql, paramSource, rowMapper, dbSource), retryCondition, retryCount, interval, orElse);
    }

    public static <T> CList<T> query(String sql,
                                     MapSqlParameterSource paramSource,
                                     Class<T> elementType,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval) {
      return query(sql, paramSource, elementType, dbSource, retryCondition, retryCount, interval, new CList<>());
    }

    public static <T> CList<T> query(String sql,
                                     MapSqlParameterSource paramSource,
                                     Class<T> elementType,
                                     String dbSource,
                                     Predicate<CList<T>> retryCondition,
                                     int retryCount,
                                     int interval,
                                     CList<T> orElse) {
      return doWithRetry(integer -> query(sql, paramSource, elementType, dbSource), retryCondition, retryCount, interval, orElse);
    }

    public static CList<Map<String, Object>> query(String sql,
                                                   String dbSource,
                                                   Predicate<CList<Map<String, Object>>> retryCondition,
                                                   int retryCount,
                                                   int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, new CList<>());
    }

    public static CList<Map<String, Object>> query(String sql,
                                                   String dbSource,
                                                   Predicate<CList<Map<String, Object>>> retryCondition,
                                                   int retryCount,
                                                   int interval,
                                                   CList<Map<String, Object>> orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static CList<Map<String, Object>> query(String sql,
                                                   MapSqlParameterSource paramSource,
                                                   String dbSource,
                                                   Predicate<CList<Map<String, Object>>> retryCondition,
                                                   int retryCount,
                                                   int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, new CList<>());
    }

    public static CList<Map<String, Object>> query(String sql,
                                                   MapSqlParameterSource paramSource,
                                                   String dbSource,
                                                   Predicate<CList<Map<String, Object>>> retryCondition,
                                                   int retryCount,
                                                   int interval,
                                                   CList<Map<String, Object>> orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryMap {
    public static CHashMap<String, Object> query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static CHashMap<String, Object> query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForMap", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          Map<String, Object> result = jdbcTemplate.queryForMap(sql, paramSource);
          log.trace("Row found: " + result.size());
          return new CHashMap<>(result);
        }
        catch (EmptyResultDataAccessException e) {
          return new CHashMap<>();
        }
      });
    }

    public static CHashMap<String, Object> query(String sql,
                                                 String dbSource,
                                                 Predicate<CHashMap<String, Object>> retryCondition,
                                                 int retryCount,
                                                 int interval) {
      return query(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static CHashMap<String, Object> query(String sql,
                                                 String dbSource,
                                                 Predicate<CHashMap<String, Object>> retryCondition,
                                                 int retryCount,
                                                 int interval,
                                                 CHashMap<String, Object> orElse) {
      return query(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static CHashMap<String, Object> query(String sql,
                                                 MapSqlParameterSource paramSource,
                                                 String dbSource,
                                                 Predicate<CHashMap<String, Object>> retryCondition,
                                                 int retryCount,
                                                 int interval) {
      return query(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static CHashMap<String, Object> query(String sql,
                                                 MapSqlParameterSource paramSource,
                                                 String dbSource,
                                                 Predicate<CHashMap<String, Object>> retryCondition,
                                                 int retryCount,
                                                 int interval,
                                                 CHashMap<String, Object> orElse) {
      return doWithRetry(integer -> query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryObject {
    public static Object query(String sql, String dbSource) {
      return query(sql, new MapSqlParameterSource(), dbSource);
    }

    public static Object query(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForObject", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          return jdbcTemplate.queryForObject(sql, paramSource, Object.class);
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static <T> T query(String sql, RowMapper<T> rowMapper, String dbSource) {
      return query(sql, new MapSqlParameterSource(), rowMapper, dbSource);
    }

    public static <T> T query(String sql, MapSqlParameterSource paramSource, RowMapper<T> rowMapper, String dbSource) {
      return doAction("queryForMap", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          T result = jdbcTemplate.queryForObject(sql, paramSource, rowMapper);
          log.trace(RESULT + result);
          return result;
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static <T> T query(String sql, RowMapper<T> rowMapper, String dbSource, Predicate<T> retryCondition, int retryCount, int interval) {
      return query(sql, rowMapper, dbSource, retryCondition, retryCount, interval, null);
    }

    public static <T> T query(String sql,
                              RowMapper<T> rowMapper,
                              String dbSource,
                              Predicate<T> retryCondition,
                              int retryCount,
                              int interval,
                              T orElse) {
      return query(sql, new MapSqlParameterSource(), rowMapper, dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static <T> T query(String sql,
                              MapSqlParameterSource paramSource,
                              RowMapper<T> rowMapper,
                              String dbSource,
                              Predicate<T> retryCondition,
                              int retryCount,
                              int interval) {
      return query(sql, paramSource, rowMapper, dbSource, retryCondition, retryCount, interval, null);
    }

    public static <T> T query(String sql,
                              MapSqlParameterSource paramSource,
                              RowMapper<T> rowMapper,
                              String dbSource,
                              Predicate<T> retryCondition,
                              int retryCount,
                              int interval,
                              T orElse) {
      return doWithRetry(integer -> query(sql, paramSource, rowMapper, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class QueryBlob {
    public static String queryAsString(String sql, String dbSource) {
      return doAction("", dbSource, sql, "", jdbcTemplate -> {
        try {
          Object result = QueryObject.query(sql, dbSource);
          return result == null ? null : new String((byte[]) result);
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static String queryAsString(String sql, MapSqlParameterSource paramSource, String dbSource) {
      return doAction("queryForBlobAsString", dbSource, sql, paramSource, jdbcTemplate -> {
        try {
          Object result = QueryObject.query(sql, paramSource, dbSource);
          return result == null ? null : new String((byte[]) result);
        }
        catch (EmptyResultDataAccessException e) {
          return null;
        }
      });
    }

    public static String queryAsString(String sql, String dbSource, Predicate<String> retryCondition, int retryCount, int interval) {
      return queryAsString(sql, dbSource, retryCondition, retryCount, interval, null);
    }

    public static String queryAsString(String sql, String dbSource, Predicate<String> retryCondition, int retryCount, int interval, String orElse) {
      return queryAsString(sql, new MapSqlParameterSource(), dbSource, retryCondition, retryCount, interval, orElse);
    }

    public static String queryAsString(String sql,
                                       MapSqlParameterSource paramSource,
                                       String dbSource,
                                       Predicate<String> retryCondition,
                                       int retryCount,
                                       int interval) {
      return queryAsString(sql, paramSource, dbSource, retryCondition, retryCount, interval, null);
    }

    public static String queryAsString(String sql,
                                       MapSqlParameterSource paramSource,
                                       String dbSource,
                                       Predicate<String> retryCondition,
                                       int retryCount,
                                       int interval,
                                       String orElse) {
      return doWithRetry(integer -> QueryString.query(sql, paramSource, dbSource), retryCondition, retryCount, interval, orElse);
    }
  }

  public static class Batch {
    public static CList<Integer> update(List<String> batches, String dbSource) {
      return update(batches, 500, dbSource);
    }

    public static CList<Integer> update(List<String> batches, int partitionSize, String dbSource) {
      return doAction("batchUpdate", dbSource, CStringUtil.join(batches, "\n"), "", jdbcTemplate -> {
        CList<Integer> output = new CList<>();
        for (CList<String> partition : new CList<>(batches).partition(partitionSize)) {
          output.addAll(List.of(ArrayUtils.toObject(jdbcTemplate.getJdbcOperations().batchUpdate(partition.toArray(new String[0])))));
        }
        return output;
      });
    }

    public static CList<Integer> update(String sql, List<MapSqlParameterSource> parameters, String dbSource) {
      return update(sql, parameters, 500, dbSource);
    }

    public static CList<Integer> update(String sql, List<MapSqlParameterSource> parameters, int partitionSize, String dbSource) {
      List<Map<String, Object>> batchValues = new CList<>(parameters).mapToList(MapSqlParameterSource::getValues);
      return doAction("batchUpdate", dbSource, sql, CStringUtil.join(batchValues, "\n"), jdbcTemplate -> {
        CList<Integer> output = new CList<>();
        for (CList<MapSqlParameterSource> partition : new CList<>(parameters).partition(partitionSize)) {
          output.addAll(List.of(ArrayUtils.toObject(jdbcTemplate.batchUpdate(sql, partition.toArray(new MapSqlParameterSource[0])))));
        }
        return output;
      });
    }
  }

  public static void delete(String sql, String dbSource) {
    delete(sql, new MapSqlParameterSource(), dbSource);
  }

  public static void delete(String sql, MapSqlParameterSource paramSource, String dbSource) {
    doAction("delete", dbSource, sql, paramSource, jdbcTemplate -> jdbcTemplate.update(sql, paramSource));
  }

  public static int update(String sql, String dbSource) {
    return update(sql, new MapSqlParameterSource(), dbSource);
  }

  public static int update(String sql, MapSqlParameterSource paramSource, String dbSource) {
    return doAction("update", dbSource, sql, paramSource, jdbcTemplate -> jdbcTemplate.update(sql, paramSource));
  }

  public static int insert(String sql, String dbSource) {
    return insert(sql, new MapSqlParameterSource(), dbSource);
  }

  public static int insert(String sql, MapSqlParameterSource paramSource, String dbSource) {
    return doAction("insert", dbSource, sql, paramSource, jdbcTemplate -> jdbcTemplate.update(sql, paramSource));
  }

  public static CHashMap<String, Object> call(CallableStatementCreator statement, List<SqlParameter> params, String dbSource) {
    return doAction("get", dbSource, statement.toString(), params.toString(), jdbcTemplate -> {
      try {
        return new CHashMap<>(jdbcTemplate.getJdbcOperations().call(statement, params));
      }
      catch (EmptyResultDataAccessException e) {
        return new CHashMap<>();
      }
    });
  }

  public static CHashMap<String, Object> call(CallableStatementCreator statement, String dbSource) {
    return call(statement, new ArrayList<>(), dbSource);
  }

  private static <R> R doAction(String actionName,
                                String dbSource,
                                String sql,
                                MapSqlParameterSource parameters,
                                Function<NamedParameterJdbcTemplate, R> action) {
    return doAction(actionName, dbSource, sql, parameters == null ? "" : parameters.getValues().toString(), action);
  }

  private static <R> R doAction(String actionName, String dbSource, String sql, String parameters, Function<NamedParameterJdbcTemplate, R> action) {
    if (dataSourcesMap.isEmpty()) {
      throw new IndexOutOfBoundsException("No connection available.\nUse CSqlDataSource.addDataSource to add new datasource.");
    }

    if (CStringUtil.isNotBlank(parameters)) {
      log.trace(actionName + " on " + dbSource + " => " + sql + " with parameters " + parameters);
    }
    else {
      log.trace(actionName + " on " + dbSource + " => " + sql);
    }
    CDate startTime = CDate.now();
    try {
      int retryCount = CSqlConfigs.getNumberOfRetryOnConnectionException();
      int interval = CSqlConfigs.getInternalTimeOfRetryOnConnectionException() * 1000;
      return CRetry.retryOnThrowable(idx -> action.apply(new NamedParameterJdbcTemplate(dataSourcesMap.get(dbSource))), (idx, t) -> {
        log.warn("Failed to obtain DB connection on {} try with exception {}.", idx, t);
        return t instanceof CannotGetJdbcConnectionException || t instanceof SQLRecoverableException;
      }, retryCount, interval, null, true);
    }
    catch (Exception t) {
      log.error("Failed to Perform " + actionName + " against " + dbSource, t);
      throw t;
    } finally {
      recordPerformanceMetrics(actionName, dbSource, sql, parameters, startTime);
    }
  }

  private synchronized static void recordPerformanceMetrics(String actionName, String dbSource, String sql, String parameters, CDate startTime) {
    if (!CMetricsConfigs.isSqlRecorderEnabled()) return;
    try {
      CMetricsUtils.addMetric(
          actionName,
          startTime,
          startTime.getDurationToNow().toNanos(),
          CList.of(
              new CMetric("DB_SOURCE", dbSource, null),
              new CMetric("QUERY", CRegExUtil.replaceAll(sql, "[\r\n]+", " "), null),
              new CMetric("PARAMS", parameters, null)
          )
      );
    } catch (Exception e) {
      log.warn("Failed to record performance metric.", e);
    }
  }

  private static <R> R doWithRetry(Function<Integer, R> m, Predicate<R> retryCondition, int retryCount, int interval, R orElse) {
    return CRetry.retryIf(m, retryCondition, retryCount, interval, () -> orElse, true);
  }
}
