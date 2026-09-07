package org.catools.athena.core.common.service;

import java.util.Optional;
import java.util.Set;

public interface QueryService {
  /**
   * Execute a query that returns a single result
   *
   * @param query the SQL query to execute
   * @return an Optional containing the result or empty if no result found
   */
  Optional<Object> querySingleResult(String query);

  /**
   * Execute a query that returns multiple results
   *
   * @param query the SQL query to execute
   * @return a Set containing the results or empty set if no results found
   */
  Optional<Set<Object>> queryCollection(String query);
}
