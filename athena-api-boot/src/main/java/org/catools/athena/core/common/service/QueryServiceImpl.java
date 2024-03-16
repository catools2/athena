package org.catools.athena.core.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
  @PersistenceContext
  private EntityManager entityManager;

  public Optional<Object> querySingleResult(String query) {
    Object singleResult = entityManager.createNativeQuery(query).getSingleResult();
    return Optional.ofNullable(singleResult);
  }

  public Set<Object> queryCollection(String query) {
    List resultList = entityManager.createNativeQuery(query).getResultList();
    Set<Object> output = new HashSet<>();
    if (resultList != null && !resultList.isEmpty())
      output.addAll(resultList);
    return output;
  }

}
