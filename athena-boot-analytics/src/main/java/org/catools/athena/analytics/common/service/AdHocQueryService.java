package org.catools.athena.analytics.common.service;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.analytics.QueryResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.SqlValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Runs a caller-built SQL statement, read-only.
 *
 * <p>This replaces the closed registry that used to be the only way to reach this data: the
 * console and the agent now write their own SQL against the analytics views, and this service's
 * job is to make sure that whatever they send can only ever read. Two independent guards, because
 * either one alone has a gap the other closes:
 *
 * <ol>
 *   <li>{@link #validateReadOnly(String)} rejects anything that is not one {@code SELECT}/{@code
 *       WITH} statement before it reaches the driver - the fast, legible failure for the common
 *       mistake.
 *   <li>{@code @Transactional(readOnly = true)} puts the connection in a PostgreSQL read-only
 *       transaction, which the engine itself enforces on every statement it plans - including a
 *       data-modifying CTE buried inside an otherwise innocent {@code WITH ... SELECT}, which a
 *       text-level check alone would miss.
 * </ol>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdHocQueryService {

  private final NamedParameterJdbcTemplate jdbc;

  @Value("${athena.analytics.max-rows:50000}")
  private int maxRows;

  @Value("${athena.analytics.adhoc-statement-timeout-ms:15000}")
  private int statementTimeoutMs;

  /** Raised for anything the caller got wrong, so the controller can answer 400 rather than 500. */
  public static class InvalidRequest extends IllegalArgumentException {
    public InvalidRequest(String message) {
      super(message);
    }
  }

  /** Comments and string literals, blanked out before the shape of the statement is checked. */
  private static final Pattern LITERAL_OR_COMMENT =
      Pattern.compile("'(?:[^']|'')*'|--[^\\n]*|/\\*.*?\\*/", Pattern.DOTALL);

  /**
   * Keywords that can modify data or state, checked anywhere in the statement rather than only at
   * its head - a data-modifying CTE (e.g. {@code WITH t AS (DELETE FROM x RETURNING *) SELECT *
   * FROM t}) has {@code WITH} as its leading word and the write hidden inside.
   */
  private static final Pattern WRITE_KEYWORD = Pattern.compile(
      "\\b(insert|update|delete|merge|drop|alter|truncate|create|grant|revoke|call|copy|vacuum"
          + "|reindex|cluster|refresh|listen|notify|unlisten|prepare|deallocate|discard"
          + "|checkpoint|do|lock)\\b",
      Pattern.CASE_INSENSITIVE);

  @Transactional(readOnly = true)
  public QueryResultDto execute(String sql, Map<String, Object> params) {
    validateReadOnly(sql);
    jdbc.getJdbcTemplate().execute("SET LOCAL statement_timeout = " + statementTimeoutMs);

    MapSqlParameterSource binds = new MapSqlParameterSource();
    for (Map.Entry<String, Object> entry : (params == null ? Map.<String, Object>of() : params).entrySet()) {
      bind(binds, entry.getKey(), entry.getValue());
    }

    List<QueryResultDto.Column> columns = new ArrayList<>();
    List<List<Object>> rows = new ArrayList<>();
    boolean[] truncated = {false};

    jdbc.query(sql, binds, rs -> {
      ResultSetMetaData meta = rs.getMetaData();
      for (int i = 1; i <= meta.getColumnCount(); i++) {
        columns.add(new QueryResultDto.Column(meta.getColumnLabel(i), meta.getColumnTypeName(i)));
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
      log.warn("Ad-hoc query hit the {} row cap; the result is partial.", maxRows);
    }
    return new QueryResultDto("adhoc", columns, rows, truncated[0], List.of(), List.of());
  }

  /**
   * The statement must be exactly one {@code SELECT}/{@code WITH}. Rejected here rather than left
   * to the driver: a stacked statement ({@code SELECT 1; DROP TABLE x}) is a syntax question the
   * database would answer inconsistently across drivers, and the point is a caller never finds
   * that boundary by trial and error.
   */
  private void validateReadOnly(String sql) {
    if (sql == null || sql.isBlank()) {
      throw new InvalidRequest("sql is required");
    }
    String stripped = stripLiterals(sql).trim();
    String withoutTrailingSemicolon =
        stripped.endsWith(";") ? stripped.substring(0, stripped.length() - 1) : stripped;
    if (withoutTrailingSemicolon.indexOf(';') >= 0) {
      throw new InvalidRequest("Only a single statement is allowed");
    }

    String firstWord = withoutTrailingSemicolon.trim().split("\\s+", 2)[0].toLowerCase(Locale.ROOT);
    if (!firstWord.equals("select") && !firstWord.equals("with")) {
      throw new InvalidRequest("Only SELECT/WITH statements are allowed, but the statement started with '"
          + firstWord + "'");
    }

    Matcher writeMatch = WRITE_KEYWORD.matcher(withoutTrailingSemicolon);
    if (writeMatch.find()) {
      throw new InvalidRequest("Statement contains a disallowed keyword: " + writeMatch.group());
    }
  }

  private static String stripLiterals(String sql) {
    Matcher m = LITERAL_OR_COMMENT.matcher(sql);
    StringBuilder out = new StringBuilder(sql);
    while (m.find()) {
      for (int i = m.start(); i < m.end(); i++) {
        out.setCharAt(i, ' ');
      }
    }
    return out.toString();
  }

  /**
   * Binds a value by its JSON shape: a list becomes a {@code text[]}, a parseable instant becomes
   * a real timestamp bind (so {@code col BETWEEN :timeFrom AND :timeTo} works against a timestamp
   * column), everything else binds as-is.
   */
  private static void bind(MapSqlParameterSource binds, String name, Object raw) {
    if (raw == null) {
      // Console forms send null for an omitted scalar filter. An untyped JDBC NULL makes
      // PostgreSQL unable to resolve expressions such as `(:version IS NULL OR col = :version)`.
      binds.addValue(name, null, Types.VARCHAR);
      return;
    }
    if (raw instanceof Collection<?> collection) {
      binds.addValue(name, textArray(collection.stream()
          .filter(Objects::nonNull).map(String::valueOf).toList()));
      return;
    }
    if (raw instanceof String text) {
      Timestamp instant = tryParseInstant(text);
      if (instant != null) {
        binds.addValue(name, instant);
        return;
      }
      binds.addValue(name, text, Types.VARCHAR);
      return;
    }
    binds.addValue(name, raw);
  }

  private static Timestamp tryParseInstant(String text) {
    try {
      return Timestamp.from(Instant.parse(text));
    } catch (DateTimeParseException e) {
      return null;
    }
  }

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
