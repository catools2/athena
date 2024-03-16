package org.catools.athena.core.common.service;

import java.util.Optional;
import java.util.Set;

// This class allow us to execute custom query and return result without continues changes in application code
public interface QueryService {

  Optional<Object> querySingleResult(String query);

  Set<Object> queryCollection(String query);

}
