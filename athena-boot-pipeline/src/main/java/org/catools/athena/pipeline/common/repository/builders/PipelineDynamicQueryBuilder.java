package org.catools.athena.pipeline.common.repository.builders;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PipelineDynamicQueryBuilder implements PipelineRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Optional<Pipeline> findLastPipeline(String pipelineName, String pipelineNumber, Long versionId, Long environmentId) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Pipeline> cq = cb.createQuery(Pipeline.class);
    Root<Pipeline> root = cq.from(Pipeline.class);
    // Eagerly fetch metadata to avoid LazyInitializationException
    root.fetch("metadata", JoinType.LEFT);
    List<Predicate> predicates = new ArrayList<>();

    if (pipelineName != null && !pipelineName.isBlank()) {
      predicates.add(cb.like(root.get("name"), pipelineName));
    }
    if (pipelineNumber != null && !pipelineNumber.isBlank()) {
      predicates.add(cb.like(root.get("number"), pipelineNumber));
    }
    if (versionId != null) {
      predicates.add(cb.equal(root.get("versionId"), versionId));
    }
    if (environmentId != null) {
      predicates.add(cb.equal(root.get("environmentId"), environmentId));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    // Order by number desc, id desc if number is present, else just id desc
    if (pipelineNumber != null && !pipelineNumber.isBlank()) {
      cq.orderBy(cb.desc(root.get("number")), cb.desc(root.get("id")));
    } else {
      cq.orderBy(cb.desc(root.get("id")));
    }

    TypedQuery<Pipeline> query = em.createQuery(cq);
    query.setMaxResults(1);
    List<Pipeline> result = query.getResultList();
    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }
}
