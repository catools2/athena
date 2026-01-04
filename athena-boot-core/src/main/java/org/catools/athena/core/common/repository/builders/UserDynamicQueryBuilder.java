package org.catools.athena.core.common.repository.builders;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.entity.UserFilterDto;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Query Builder for User entity.
 * Generates JPQL queries based on UserFilterDto parameters.
 * Handles query building, sorting, and count query generation.
 */
@RequiredArgsConstructor
public class UserDynamicQueryBuilder {

  private final UserFilterDto filterDto;

  /**
   * Build JPQL query based on active filters in UserFilterDto
   *
   * @return JPQL query string
   */
  public String buildQuery() {
    String baseQuery = "SELECT u FROM User u";

    if (!filterDto.hasFilters()) {
      return baseQuery;
    }

    baseQuery = baseQuery.concat(" WHERE ");

    List<String> filters = new ArrayList<>();

    if (filterDto.getUsername() != null && !filterDto.getUsername().trim().isEmpty()) {
      filters.add(" lower(u.username) like lower(concat('%%', '%s', '%%')) ".formatted(escapeJpql(filterDto.getUsername())));
    }

    if (filterDto.getAlias() != null && !filterDto.getAlias().trim().isEmpty()) {
      filters.add("EXISTS (SELECT 1 FROM UserAlias ua WHERE ua.user = u AND lower(ua.alias) like lower(concat('%%', '%s', '%%'))) ".formatted(escapeJpql(filterDto.getAlias())));
    }

    return baseQuery.concat(String.join(" AND ", filters));
  }

  /**
   * Build query with sorting applied
   *
   * @param pageable pagination and sort information
   * @return JPQL query string with ORDER BY clause
   */
  public String buildQueryWithSort(Pageable pageable) {
    String query = buildQuery();
    return applySort(query, pageable);
  }

  /**
   * Apply ORDER BY clause from Pageable's Sort to JPQL query
   *
   * @param jpqlQuery the base JPQL query
   * @param pageable pagination information containing sort details
   * @return JPQL query with ORDER BY clause
   */
  private String applySort(String jpqlQuery, Pageable pageable) {
    if (pageable == null || pageable.getSort().isUnsorted()) {
      return jpqlQuery;
    }

    StringBuilder orderByClause = new StringBuilder(" ORDER BY ");
    pageable.getSort().forEach(order -> {
      orderByClause.append("lower(TRIM(u.")
          .append(order.getProperty())
          .append(")) ")
          .append(order.getDirection().name())
          .append(", ");
    });
    // Add stable tiebreaker by id
    orderByClause.append("u.id ASC");
    return jpqlQuery + orderByClause;
  }

  /**
   * Build count query to get total number of records matching filters
   *
   * @return COUNT query string
   */
  public String buildCountQuery() {
    String baseQuery = buildQuery();
    return baseQuery.replaceFirst("(?i)^\\s*SELECT\\s+([a-zA-Z][a-zA-Z0-9_]*)\\s+FROM\\s+([a-zA-Z][a-zA-Z0-9_.]*)\\s+\\1", "SELECT COUNT($1) FROM $2 $1");
  }

  /**
   * Escape JPQL special characters to prevent injection
   *
   * @param value the value to escape
   * @return escaped value
   */
  private static String escapeJpql(String value) {
    if (value == null || value.trim().isEmpty()) {
      return "";
    }
    return value.replace("'", "''");
  }
}

