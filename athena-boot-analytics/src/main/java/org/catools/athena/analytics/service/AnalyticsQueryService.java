package org.catools.athena.analytics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.analytics.registry.ParamKind;
import org.catools.athena.analytics.registry.QueryParam;
import org.catools.athena.analytics.registry.QueryRegistry;
import org.catools.athena.analytics.registry.RegisteredQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.SqlValue;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Runs a registered query with caller-supplied parameters. */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsQueryService {

  private final QueryRegistry registry;
  private final NamedParameterJdbcTemplate jdbc;
  private final ViewFreshnessService freshness;

  @Value("${athena.analytics.max-rows:50000}")
  private int maxRows;

  /** Raised for anything the caller got wrong, so the controller can answer 400 rather than 500. */
  public static class InvalidRequest extends RuntimeException {
    public InvalidRequest(String message) {
      super(message);
    }
  }

  public QueryResult run(String queryId, Map<String, Object> params) {
    RegisteredQuery query = registry.find(queryId)
        .orElseThrow(() -> new InvalidRequest("Unknown query '" + queryId + "'"));

    String sql = query.sql();
    MapSqlParameterSource binds = new MapSqlParameterSource();

    for (QueryParam param : query.params()) {
      Object raw = params.get(param.name());
      switch (param.kind()) {
        case OPERATOR -> sql = substituteOperator(sql, param, raw);
        case INSTANT -> binds.addValue(param.name(), toTimestamp(param, raw));
        case LIST -> binds.addValue(param.name(), textArray(toList(raw)));
        // Typed explicitly, because an optional filter spelled `(:x IS NULL OR col = :x)` sends
        // a NULL with no type attached and PostgreSQL answers "could not determine data type of
        // parameter". Declaring VARCHAR costs nothing and makes every optional filter work.
        case SCALAR -> binds.addValue(
            param.name(), raw == null ? null : String.valueOf(raw), Types.VARCHAR);
      }
    }

    List<QueryResult.Column> columns = new ArrayList<>();
    List<List<Object>> rows = new ArrayList<>();
    boolean[] truncated = {false};

    // ResultSetExtractor, not RowCallbackHandler: the column list has to be read from the
    // metadata whether or not any rows came back. A chart still needs to know its shape when
    // the filters match nothing, and an empty `columns` would make an empty result
    // indistinguishable from a broken one.
    jdbc.query(sql, binds, rs -> {
      ResultSetMetaData meta = rs.getMetaData();
      for (int i = 1; i <= meta.getColumnCount(); i++) {
        columns.add(new QueryResult.Column(meta.getColumnLabel(i), meta.getColumnTypeName(i)));
      }
      while (rs.next()) {
        if (rows.size() >= maxRows) {
          truncated[0] = true;
          break;
        }
        List<Object> row = new ArrayList<>(columns.size());
        for (int i = 1; i <= columns.size(); i++) {
          row.add(normalise(rs.getObject(i)));
        }
        rows.add(row);
      }
      return null;
    });

    if (truncated[0]) {
      log.warn("Query {} hit the {} row cap; the result is partial.", queryId, maxRows);
    }
    return new QueryResult(queryId, columns, rows, truncated[0], query.views(),
        freshness.of(query.views()));
  }

  /**
   * The only place text reaches the SQL. The value must be one the manifest enumerated - anything
   * else is rejected outright rather than escaped, because there is no escaping that makes an
   * arbitrary operator safe.
   */
  private String substituteOperator(String sql, QueryParam param, Object raw) {
    String value = raw == null ? param.allowed().getFirst() : String.valueOf(raw);
    if (!param.allowed().contains(value)) {
      throw new InvalidRequest("Parameter '" + param.name() + "' must be one of "
          + param.allowed() + " but was '" + value + "'");
    }
    return sql.replace("{{" + param.name() + "}}", value);
  }

  private Timestamp toTimestamp(QueryParam param, Object raw) {
    if (raw == null) {
      throw new InvalidRequest("Parameter '" + param.name() + "' is required");
    }
    if (raw instanceof Number epochMillis) {
      return Timestamp.from(Instant.ofEpochMilli(epochMillis.longValue()));
    }
    try {
      return Timestamp.from(Instant.parse(String.valueOf(raw)));
    } catch (DateTimeParseException e) {
      throw new InvalidRequest("Parameter '" + param.name()
          + "' must be an ISO-8601 instant or epoch millis, but was '" + raw + "'");
    }
  }

  private static List<String> toList(Object raw) {
    if (raw == null) {
      // Empty, not null: the "All" sentinel is cardinality(:x) = 0, so an absent filter must
      // arrive as an empty array rather than a null that would make the comparison unknown.
      return List.of();
    }
    if (raw instanceof Collection<?> collection) {
      return collection.stream().filter(Objects::nonNull).map(String::valueOf).toList();
    }
    return List.of(String.valueOf(raw));
  }

  /**
   * Binds a real {@code text[]}. The list parameters feed {@code = ANY(:x)} and
   * {@code jsonb_exists_any(col, :x)}, neither of which accepts an expanded IN-list, so the array
   * has to be created on the connection.
   */
  private static SqlValue textArray(List<String> values) {
    return new SqlValue() {
      private Array array;

      @Override
      public void setValue(PreparedStatement ps, int paramIndex) throws SQLException {
        array = ps.getConnection().createArrayOf("text", values.toArray(new String[0]));
        ps.setArray(paramIndex, array);
      }

      @Override
      public void cleanup() {
        if (array != null) {
          try {
            array.free();
          } catch (SQLException e) {
            log.debug("Could not free array parameter", e);
          }
        }
      }
    };
  }

  /** Keep JDBC-specific types out of the JSON payload. */
  private static Object normalise(Object value) {
    if (value instanceof Timestamp timestamp) {
      return timestamp.toInstant().toString();
    }
    if (value instanceof Array array) {
      try {
        return array.getArray();
      } catch (SQLException e) {
        return String.valueOf(value);
      }
    }
    return value;
  }
}
