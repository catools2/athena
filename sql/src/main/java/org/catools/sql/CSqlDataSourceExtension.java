package org.catools.sql;

import org.catools.common.extensions.types.CDynamicNumberExtension;
import org.catools.common.extensions.types.CDynamicObjectExtension;
import org.catools.common.extensions.types.CDynamicStringExtension;
import org.catools.common.extensions.types.interfaces.CDynamicCollectionExtension;
import org.catools.common.extensions.types.interfaces.CDynamicDateExtension;
import org.catools.common.extensions.types.interfaces.CDynamicMapExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collection;

public class CSqlDataSourceExtension {

  public static class String {
    public static CDynamicStringExtension query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicStringExtension query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicStringExtension() {
        @Override
        public java.lang.String _get() {
          return CSqlDataSource.QueryString.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class Date {
    public static CDynamicDateExtension query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicDateExtension query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicDateExtension() {
        @Override
        public java.util.Date _get() {
          return CSqlDataSource.QueryDate.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class Int {
    public static CDynamicNumberExtension<Integer> query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicNumberExtension<Integer> query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicNumberExtension<>() {
        @Override
        public Integer _get() {
          return CSqlDataSource.QueryInt.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class Long {
    public static CDynamicNumberExtension<java.lang.Long> query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicNumberExtension<java.lang.Long> query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicNumberExtension<>() {
        @Override
        public java.lang.Long _get() {
          return CSqlDataSource.QueryLong.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class Double {
    public static CDynamicNumberExtension<java.lang.Double> query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicNumberExtension<java.lang.Double> query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicNumberExtension<>() {
        @Override
        public java.lang.Double _get() {
          return CSqlDataSource.QueryDouble.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class BigDecimal {
    public static CDynamicNumberExtension<java.math.BigDecimal> query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicNumberExtension<java.math.BigDecimal> query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicNumberExtension<>() {
        @Override
        public java.math.BigDecimal _get() {
          return CSqlDataSource.QueryBigDecimal.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class List {
    public static <T> CDynamicCollectionExtension<T> query(
        java.lang.String sql,
        RowMapper<T> rowMapper,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return query(
          sql, new MapSqlParameterSource(), rowMapper, waitSec, interval, dbSource);
    }

    public static <T> CDynamicCollectionExtension<T> query(
        java.lang.String sql,
        SqlParameterSource paramSource,
        RowMapper<T> rowMapper,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicCollectionExtension<>() {
        @Override
        public Collection<T> _get() {
          return CSqlDataSource.QueryList.query(sql, paramSource, rowMapper, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }

    public static <T> CDynamicCollectionExtension<T> query(
        java.lang.String sql,
        SqlParameterSource paramSource,
        Class<T> elementType,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicCollectionExtension<>() {
        @Override
        public Collection<T> _get() {
          return CSqlDataSource.QueryList.query(sql, paramSource, elementType, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class Map {
    public static CDynamicMapExtension<java.lang.String, java.lang.Object> query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicMapExtension<java.lang.String, java.lang.Object> query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicMapExtension<>() {
        @Override
        public java.util.Map<java.lang.String, java.lang.Object> _get() {
          return CSqlDataSource.QueryMap.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class Object {
    public static java.lang.Object query(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return query(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static java.lang.Object query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicObjectExtension<>() {
        @Override
        public java.lang.Object _get() {
          return CSqlDataSource.QueryObject.query(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }

    public static <T> CDynamicObjectExtension<T> query(
        java.lang.String sql,
        RowMapper<T> rowMapper,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return query(
          sql, new MapSqlParameterSource(), rowMapper, waitSec, interval, dbSource);
    }

    public static <T> CDynamicObjectExtension<T> query(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        RowMapper<T> rowMapper,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicObjectExtension<T>() {
        @Override
        public T _get() {
          return CSqlDataSource.QueryObject.query(sql, paramSource, rowMapper, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }

  public static class Blob {
    public static CDynamicStringExtension queryAsString(
        java.lang.String sql, int waitSec, int interval, java.lang.String dbSource) {
      return queryAsString(sql, new MapSqlParameterSource(), waitSec, interval, dbSource);
    }

    public static CDynamicStringExtension queryAsString(
        java.lang.String sql,
        MapSqlParameterSource paramSource,
        int waitSec,
        int interval,
        java.lang.String dbSource) {
      return new CDynamicStringExtension() {
        @Override
        public java.lang.String _get() {
          return CSqlDataSource.QueryBlob.queryAsString(sql, paramSource, dbSource);
        }

        @Override
        public int getDefaultWaitIntervalInMilliSeconds() {
          return interval;
        }

        @Override
        public int getDefaultWaitInSeconds() {
          return waitSec;
        }
      };
    }
  }
}
